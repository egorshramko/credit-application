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

import com.example.credit.data.BinaryContent;
import com.example.credit.data.ClientProfile;
import com.example.credit.data.Contact;
import com.example.credit.data.Credit;
import com.example.credit.data.enums.ContactType;
import com.example.credit.data.enums.Sex;
import com.example.credit.service.CreditService;
import com.example.credit.service.TempProfileService;
import com.example.credit.storage.service.TempStorageService;
import com.example.credit.web.api.dto.BinaryContentDto;
import com.example.credit.web.api.dto.profile.ContactDto;

import jakarta.json.Json;
import jakarta.json.JsonObject;
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
	
	@GetMapping
	public String getProfilePage(@PathVariable("id") String creditId, Model model) {
		
		Credit credit = creditService.getCreditById(creditId);
		model.addAttribute("profile", Optional.ofNullable(credit.getProfile()).orElse(new ClientProfile()));
		
		return "profile";

	}
	
	@PostMapping("/contact")
	@ResponseBody
	public ResponseEntity<String> addContactToProfile(HttpSession session) {
		
		Object profileObj = session.getAttribute("profile");
		
		if (profileObj instanceof ClientProfile) {
			ClientProfile profile = (ClientProfile) profileObj;
			
			UUID newContactUUID = UUID.randomUUID();
			
			profile.addContact(Contact.builder()
								.uuid(newContactUUID)
								.build());
			
			session.setAttribute("profile", profile);
			
			return ResponseEntity.ok()
					.body(Json.createObjectBuilder()
							.add("id", newContactUUID.toString())
							.build()
							.toString());
		}
		
		return ResponseEntity.internalServerError()
				.build();
	}
	
	@DeleteMapping("/contact/{contactId}")
	@ResponseBody
	public ResponseEntity<String> removeContactFromProfile(HttpSession session,
			@PathVariable("contactId") String contactUUID) {
		
		Object profileObj = session.getAttribute("profile");
		
		if (profileObj instanceof ClientProfile) {
			ClientProfile profile = (ClientProfile) profileObj;
			
			log.info("profile id: " + profile.getId().toString());
			log.info("contacts.size: " + Integer.toString(profile.getContacts().size()));
			
			UUID removedContactUUID = UUID.fromString(contactUUID);
			if (profile.removeContact(removedContactUUID)) {
				
				log.info("contact removed");
				log.info("contacts.size: " + Integer.toString(profile.getContacts().size()));
				
				return ResponseEntity.ok()
						.build();
			}
			
			log.warn("incorrect contact UUID!");
			log.info("contacts.size: " + Integer.toString(profile.getContacts().size()));
			return ResponseEntity.notFound()
					.build();
		}
		
		return ResponseEntity.internalServerError()
				.build();
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
	
	@DeleteMapping("/photo")
	@ResponseBody
	public ResponseEntity<?> removePhotoFromProfile(HttpSession session, 
													@RequestBody BinaryContentDto dto) {
		
		Object profileObj = session.getAttribute("profile");
		
		if (profileObj instanceof ClientProfile) {
			
			ClientProfile profile = (ClientProfile) profileObj;
			log.info("profile id: " + profile.getId());
			
			BinaryContent photo = Optional.ofNullable(profile.getPhoto()).orElse(null);
			if (photo.getUuid().equals(UUID.fromString(dto.getUuid()))) {
				log.info("remove photo from profile");
				
				
				try {
					tempStorageService.remove(photo.getUuid());
				}
				catch (IOException exception) {
					log.error("This photo is not exists!");
				}
				
			}
		}
		
		return ResponseEntity.internalServerError().build();
		
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
