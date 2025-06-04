package com.example.credit.web.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.example.credit.check.data.CheckResult;
import com.example.credit.data.BinaryContent;
import com.example.credit.data.ClientProfile;
import com.example.credit.data.Contact;
import com.example.credit.data.Credit;
import com.example.credit.data.enums.ContactType;
import com.example.credit.data.enums.Sex;
import com.example.credit.service.ClientProfileService;
import com.example.credit.service.CreditService;
import com.example.credit.service.TempProfileService;
import com.example.credit.storage.service.TempStorageService;
import com.example.credit.web.api.dto.BinaryContentDto;
import com.example.credit.web.api.dto.profile.ClientProfileDto;
import com.example.credit.web.api.dto.profile.ContactDto;
import com.example.credit.web.api.mapper.ClientProfileMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/credit/{id}/profile")
@SessionAttributes("profile")
public class ProfileController {

	@Autowired
	private CreditService creditService;
	
	@Autowired
	private TempStorageService tempStorageService;
	
	@Autowired
	private TempProfileService tempProfileService;
	
	@Autowired
	private ClientProfileService clientProfileService;
	
	@Autowired
	private ClientProfileMapper clientProfileMapper;
	
	@ModelAttribute("sex")
	public Sex[] addSexEnumToModel(Model model) {

		return Sex.values();

	}
	
	@ModelAttribute("contactTypes")
	public ContactType[] addContactTypesToModel(Model model) {
		
		return ContactType.values();
		
	}
	
	@ModelAttribute("displayValues")
	public Iterable<String> addContactTypesDisplayValuesToModel(Model model) {
		
		List<String> displayValues = new ArrayList<>();
		for (ContactType ct : ContactType.values()) {
			displayValues.add(ct.getDisplayValue());
		}
		
		return displayValues;
		
	}
	
	@ModelAttribute
	public void addProfileToModel(@PathVariable("id") String creditId, 
			HttpSession session, 
			Model model) {
		
		//загрузка анкеты из кредита
		Credit credit = creditService.getCreditById(creditId);
		ClientProfile profileFromCredit = 
				Optional.ofNullable(credit.getProfile())
				.orElse(new ClientProfile());
		
		//проверка наличия запрошенной анкеты в сессии
		ClientProfile profileFromSession = this.getProfileFromSession(session);
		if (profileFromSession == null 
				|| !profileFromCredit.getId()
					.equals(profileFromSession.getId())) {
			
			model.addAttribute("profile", profileFromCredit);
			
		}
	}
	
	@GetMapping
	public String getProfilePage(@PathVariable("id") String creditId, Model model) {
		
		return "profile";

	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<?> fillProfile(@PathVariable("id") String creditId, 
			@RequestBody ClientProfileDto profileDto,
			Model model,
			HttpSession session) {
		
		log.info("Getting profile from session");
		ClientProfile profile = this.getProfileFromSession(session);
		
		profile = clientProfileMapper.updateProfileFromDto(profile, profileDto);
		
		Iterable<CheckResult> checkResults = clientProfileService.checkAndUpdateProfile(profile);
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)				
				.body(this.mapCheckResultsToJson(checkResults));
	}
	
	private String mapCheckResultsToJson(Iterable<CheckResult> results) {
		
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		
		for (CheckResult result : results) {
			arrayBuilder.add(result.toJson());
		}
		
		return Json.createObjectBuilder()
				.add("checkResults", arrayBuilder)
				.build()
				.toString();
		
	}
	
	/**
	 * Метод добавляет в коллекцию контактов анкеты новый объект
	 * контакта с идентификатором, по которому можно получить доступ
	 * до него из клиентского приложения.
	 * 
	 * @param session - объект текущей пользовательской сессии
	 * @return
	 * Объект HTTP-ответа:
	 * Код 200 в случае успешного создания и добавления контакта.
	 * Код 500 в ином случае.
	 */
	@PostMapping("/contact")
	@ResponseBody
	public ResponseEntity<String> addContactToProfile(HttpSession session) {
		
		log.info("Request for adding contact to profile");
		
		ClientProfile profile = this.getProfileFromSession(session);
		if (profile == null) {
			log.error("Profile is not exist in this session");
			log.error("session id: " + session.getId());
			
			return ResponseEntity.internalServerError().build();
		}
		
		UUID newContactUUID = UUID.randomUUID();
		profile.addContact(Contact.builder()
								.contactType(ContactType.HOME) //статус способа связи по умолчанию
								.uuid(newContactUUID)
								.build());
		
		return ResponseEntity.ok()
				.body(Json.createObjectBuilder()
						.add("id", newContactUUID.toString())
						.build()
						.toString());
		
	}
	
	/**
	 * Метод удаления контакта из анкеты по его идентификатору
	 * 
	 * @param session - объект текущей пользовательской сессии
	 * @param contactUUIDString - идентификатор удаляемого контакта
	 * @return
	 * Объект HTTP-ответа:
	 * Код 200 без тела ответа в случае успешного удаления,
	 * Код 404 с сообщением об ошибке в теле в случае передачи 
	 * некорректного идентификатора,
	 * Код 500 в случае отсутствия анкеты в сессии и прочих ошибок сервера
	 */
	@DeleteMapping("/contact/{contactId}")
	@ResponseBody
	public ResponseEntity<String> removeContactFromProfile(HttpSession session,
			@PathVariable("contactId") String contactUUIDString) {
		
		log.info("Request to remove contact from profile");
		log.info("Removing contact UUID: " + contactUUIDString);
		
		//получение объекта анкеты из пользовательской сессии
		ClientProfile profile = this.getProfileFromSession(session);
		UUID contactUUID = UUID.fromString(contactUUIDString);
		if (profile == null) {
			log.error("Profile is not exist in this session");
			log.error("session id: " + session.getId());
			
			return ResponseEntity.internalServerError().build();
		}
		
		if (profile.removeContact(contactUUID)) {
			log.info("Contact removed successfully");
			
			return ResponseEntity.ok().build();
		}
		
		log.warn("Invalid contact UUID");
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Json.createObjectBuilder()
						.add("message", "Invalid contact UUID")
						.build()
						.toString());
		
	}
	
	/**
	 * Метод загружает фотографию анкеты в файловое хранилище и привязывает
	 * ее к объекту анкеты, хранящемуся в пользовательской сессии.
	 * 
	 * @param session - объект текущей пользовательской сессии
	 * @param file - загружаемый клиентом файл
	 * 
	 * @return
	 * Объект HTTP-ответа:
	 * Код 200 в случае успешной загрузки файла. 
	 * В теле успешного ответа содержится json 
	 * 	с UUID фотографии во временном хранилище, по которому можно получить 
	 *  доступ к фотографии.
	 * Код 500 без тела в случае неуспешной загрузки файла.
	 */
	@PostMapping("/photo")
	@ResponseBody
	public ResponseEntity<?> addPhotoToProfile(HttpSession session, 
			@RequestParam(value = "file", required = false) @Valid final MultipartFile file) {
		
		log.info("Request for adding photo to profile");
		
		//получение объекта анкеты из сессии
		ClientProfile profile = this.getProfileFromSession(session);
		if (profile == null) {
			
			log.error("Profile is not exist in this session");
			log.error("session id: " + session.getId());
			
			return ResponseEntity.internalServerError().build();
		}
		
		//загрузка файла и привязка к анкете
		try {
			UUID photoUUID = tempStorageService.upload(file);
			profile.setPhoto(BinaryContent.builder()
									.content(tempStorageService.download(photoUUID).toFile())
									.uuid(photoUUID)
									.build());
			
			log.info("Photo uploaded successfully");
			log.info("Photo id in temp file storage: " + photoUUID.toString());
			
			return ResponseEntity.ok()
					.body(Json.createObjectBuilder()
							.add("id", photoUUID.toString())
							.build()
							.toString());
			
			
		}
		catch (IOException ioException) {
			log.error(ioException.getMessage());
			
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	/**
	 * Метод, возвращающий фотографию профиля клиенту
	 * 
	 * @param session - объект текущей сессии
	 * @return
	 * HTTP-ответ с фотографией
	 */
	@GetMapping("/photo")
	public ResponseEntity<?> getProfilePhoto(HttpSession session) {
		
		log.info("Request to get profile photo");
		
		ClientProfile profile = this.getProfileFromSession(session);
		if (profile == null) {
			log.error("Profile is not exist in this session");
			log.error("session id: " + session.getId());
					
			return ResponseEntity.internalServerError().build();
		}
		
		BinaryContent photo = profile.getPhoto();
		if (photo != null) {
			
			try {
				Path photoPath = tempStorageService.download(photo.getUuid());
				Resource fileResource = new FileSystemResource(photoPath);
				
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" +
								tempStorageService.getFileNameById(photo.getUuid()));
				
				return ResponseEntity.ok()
						.headers(responseHeaders)
						.contentLength(photoPath.toFile().length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM)
						.body(fileResource);
			}
			catch (IOException ioException) {
				log.error(ioException.toString());
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Json.createObjectBuilder()
								.add("message", ioException.getMessage())
								.build()
								.toString());
			}
			
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	/**
	 * Метод удаляет фотографию из анкеты, хранящейся в пользовательской сессии, 
	 * и из файловое хранилища по переданному в пути запроса идентификатору.
	 * 
	 * @param session - объект текущей пользовательской сессии
	 * @param photoId - переданный идентификатор фотографии
	 * 
	 * @return
	 * Объект HTTP-объекта:
	 * Код 200 в случае успешного удаления фотографии.
	 * Код 404 в случае передачи некорректного идентификатора.
	 * Код 500 в иных случаях.
	 */
	@DeleteMapping("/photo/{photoUUID}")
	@ResponseBody
	public ResponseEntity<?> removePhotoFromProfile(HttpSession session, 
													@PathVariable("photoUUID") String photoId) {
		
		log.info("Request for removing profile photo");
		log.info("Requested to remove photo UUID: " + photoId);
		
		//получение объекта анкеты из сессии
		ClientProfile profile = this.getProfileFromSession(session);
		UUID photoUUID = UUID.fromString(photoId);
		if (profile == null) {
					
			log.error("Profile is not exist in this session");
			log.error("session id: " + session.getId());
					
			return ResponseEntity.internalServerError().build();
		}
		
		//проверка корректности переданного идентификатора
		BinaryContent profilePhoto = profile.getPhoto();
		if (profilePhoto == null || !profilePhoto.getUuid().equals(photoUUID)) {
			log.error("Invalid photo UUID");
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Json.createObjectBuilder()
							.add("message", "Invalid photo UUID")
							.build()
							.toString());
		}
		
		//удаление файла из хранилища и анкеты
		try {
			
			boolean photoRemoved = tempStorageService.remove(photoUUID);
			if (photoRemoved) {
				profile.setPhoto(null);
				
				log.info("Photo removed successfully");
			}
			
			return ResponseEntity.ok()
					.body(Json.createObjectBuilder()
							.add("removed", photoRemoved)
							.build()
							.toString());
			
		}
		catch (IOException ioException) {
			log.error(ioException.getMessage());
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Json.createObjectBuilder()
							.add("message", ioException.getMessage())
							.build()
							.toString());
		}
		
		
	}
	
	/**
	 * Метод добавления скана паспорта в анкету.
	 * 
	 * Сохраняет во временном файловом хранилище файл скана паспорта 
	 * клиента, после чего полученный идентификатор привязывает
	 * к анкете.
	 * 
	 * Для выполнения метода необходимо вызвать endpoint 
	 * /credit/{id}/profile/scan по методу POST
	 * 
	 * @param session - объект пользовательской 
	 *     сессии
	 * @param file - файл скана паспорта, полученный от клиента
	 *     в теле запроса
	 * 
	 * @return
	 * Код 200 и тело ответа с id скана во временном хранилище
	 *     в случае успешного сохранения скана
	 * 
	 */
	@PostMapping("/scan")
	@ResponseBody
	public ResponseEntity<?> addPassportScanToProfile(HttpSession session, 
			@RequestParam(value = "file", required = false) 
			@Valid 
			final MultipartFile file) {
		
		try {
			UUID tempStorageFileUUID = tempStorageService.upload(file);
			
			ClientProfile profile = this.getProfileFromSession(session);
			tempProfileService.addPassportScan(profile, tempStorageFileUUID);
			
			return ResponseEntity.ok(Json.createObjectBuilder()
					.add("id", tempStorageFileUUID.toString())
					.build()
					.toString());
			
		}
		catch (IOException exception) {
			log.error(exception.toString());
			
			return ResponseEntity.badRequest()
					.body(Json.createObjectBuilder()
							.add("message", exception.getMessage()));
		}
		
	}
	
	//TODO: написать нормальный JavaDoc
	//метод скачивания скана паспорта
	@GetMapping(path = "/scan/{scanId}")
	public ResponseEntity<?> downloadPassportScan(HttpSession session,
			@PathVariable("scanId") String scanUUID) {
		
		log.info("Request to download passport scan with id " + scanUUID);
		
		//получаем анкету из сессии и преобразуем ид скана
		ClientProfile profile = this.getProfileFromSession(session);
		UUID scanUUIDObject = UUID.fromString(scanUUID);
		if (profile == null) {
			
			log.error("Profile is not exist in this session");
			log.error("session id: " + session.getId());
			
			return ResponseEntity.internalServerError()
					.build();
		}
		
		//проверка корректности ид скана
		if (!tempProfileService
				.isPassportScanIdValid(profile, scanUUIDObject)) {
			
			log.info("Invalid passport scan uuid");
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Json.createObjectBuilder()
							.add("message", "Invalid passport scan uuid")
							.build()
							.toString());
		}
		
		//подготовка файла для передачи клиенту
		try {
			Path scanFilePath = tempStorageService.download(UUID.fromString(scanUUID));
			Resource fileResource = new FileSystemResource(
					scanFilePath);
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, 
					"attachment; filename=\"" + 
							tempStorageService.getFileNameById(
									UUID.fromString(scanUUID)));
			
			return ResponseEntity.ok()
					.headers(responseHeaders)
					.contentLength(scanFilePath.toFile().length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(fileResource);
					
		}
		catch (IOException exception) {
			log.error(exception.toString());
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Json.createObjectBuilder()
							.add("message", exception.getMessage())
							.build()
							.toString());
		}
		
	}
	
	
	@DeleteMapping("/scan/{scanId}")
	@ResponseBody
	public ResponseEntity<?> deletePassportScanFromProfile(HttpSession session, 
			@PathVariable("scanId") String scanUUID) {
		
		ClientProfile profile = this.getProfileFromSession(session);
		UUID scanUUIDObject = UUID.fromString(scanUUID);
		
		//проверяем валидность переданного идентификатора
		if (tempProfileService.isPassportScanIdValid(profile, scanUUIDObject)) {
			
			try {
				tempStorageService.remove(scanUUIDObject);
				tempProfileService.deletePassportScan(profile, scanUUIDObject);
				
				session.setAttribute("profile", profile);
				return ResponseEntity.ok()
						.body(Json.createObjectBuilder()
								.add("removed", true)
								.build()
								.toString());
				
			}
			catch (IOException ioException) {
				log.error(ioException.toString());
				
				return ResponseEntity.internalServerError()
						.build();
			}
			
			
		}
		else {
			return ResponseEntity.notFound()
					.build();
		}
		
	}
	
	private ClientProfile getProfileFromSession(HttpSession session) {
		
		Object profile = session.getAttribute("profile");
		if (profile instanceof ClientProfile) {
			return (ClientProfile) profile;
		}
		
		return null;
		
	}
	
	
}
