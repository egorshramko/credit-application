$("#add-contact-button").on('click', addContact);
$(".phone-input").click().mask("+7 (999) 999-99-99");

//функция добавления способа связи
function addContact() {
	console.log("Добавляем способ связи");
	
	//получаем контейнер повторителя
	let contactsRepeater = document.getElementById('contacts-repeater');
	
								/*	<div class="row my-2">
			                            <div class="col-11">
			                            	<div class="row">
			                            		<div class="col-4">
					                                <label for="contact-type-input-1" class="form-label">Статус способа связи</label>
													<select class="form-select" id="contact-type-input-1">
					                                    <option th:each="ct : ${contactTypes}" th:text="${ct.displayValue}" th:value="${ct}"></option>
					                                </select>
					                            </div>
					                            <div class="col-4">
					                                <label for="contact-value-1" class="form-label">Значение</label>
													<input class="form-control phone-input" type="text"
					                                           id="contact-value-1" placeholder="+7 (___) ___-__-__" autocomplete="off"
					                                           name="passportNumber"
					                                    />
					                                </div>
					                                <div class="col-4">
					                                    <label for="comment-connect-1" class="form-label">Комментарий</label>
					                                    <input class="form-control" type="text"
					                                           id="comment-connect-1" autocomplete="off"
					                                           name="comment"
					                                    />
					                                </div>
			                            		</div>
			                            		
			                            	</div>
			                            	<div class="col-1 d-flex align-items-end justify-content-center py-1">
			                            		<button type="button" class="btn btn-sm btn-outline-danger">
			                                        <span class="bi bi-trash3"></span>
			                                    </button>
			                            	</div>
			                                
			                            </div>
		
			                            <!--Кнопка добавления способа связи-->
			                            <div class="col-12">
			                                <div id="add-contact-btn-container">
			                                    <button type="button" id="add-contact-button" class="btn btn-link px-0 link-button">Добавить способ связи</button>
			                                </div>
			                            </div>
			                        </div>*/
								
	console.log(contactTypes);
	
	//контейнер контакта
	let contactContainer = document.createElement('div');
	contactContainer.classList.add('row');
	contactContainer.classList.add('my-2');
	
	//подбор подходящего айдишника для элемента контакта
	let contactContainerNumber = contactsRepeater.childNodes.length;
	let contactContainerId = 'contact-' + String(contactContainerNumber);
	do {
		if (!!document.getElementById(contactContainerId)) {
			contactContainerNumber++;
			contactContainertId = 'contact-' + String(contactContainerNumber);
		}
		else {
			contactContainer.setAttribute('id', contactContainerId);
		}
	}
	while (contactContainerId !== 'contact-' + String(contactContainerNumber));
	
	//контейнер заполнения информации
	let contactInformationContainer = document.createElement('div');
	contactInformationContainer.classList.add('col-11');
	
	//строка контейнера заполнения информации
	let contactInformationContainerRow = document.createElement('div');
	contactInformationContainerRow.classList.add('row');
	
	//контейнер для выпадающего списка с типом контакта
	let contactTypeSelectContainer = document.createElement('div');
	contactTypeSelectContainer.classList.add('col-4');
	
	//надпись "Статус способа связи"
	let contactTypeSelectLabel = document.createElement('label');
	contactTypeSelectLabel.classList.add('form-label');
	contactTypeSelectLabel.innerText = 'Статус способа связи';
	
	//выпадающий список с типами контакта
	let contactTypeSelect = document.createElement('select');
	contactTypeSelect.classList.add('form-select');
	
	//подбор подходящего айдишника для выпадающего списка
	let contactTypeSelectId = 'contact-type-input-' + String(contactContainerNumber);
	
	//задаем привязку надписи к выпадающему списку
	contactTypeSelectLabel.setAttribute('for', contactTypeSelectId);
	
	//Заполняем выпадающий список значениями
	for (let i = 0; i < contactTypes.length; i++) {
		
		let contactTypeSelectOption = document.createElement('option');
		contactTypeSelectOption.setAttribute('value', contactTypes[i]);
		contactTypeSelectOption.innerText = contactTypesDisplay[i];
		
		contactTypeSelect.appendChild(contactTypeSelectOption);
		
	}
	
	//заполняем контейнер выпадающего списка элементами
	contactTypeSelectContainer.appendChild(contactTypeSelectLabel);
	contactTypeSelectContainer.appendChild(contactTypeSelect);
	
	//контейнер для поля ввода телефона
	let valueContainer = document.createElement('div');
	valueContainer.classList.add('col-4');
	
	//надпись для поля ввода телефона
	let contactValueLabel = document.createElement('label');
	contactValueLabel.classList.add('form-label');
	contactValueLabel.innerText = 'Значение';
	
	//поле ввода телефона
	let contactValueInput = document.createElement('input');
	contactValueInput.classList.add('form-control');
	contactValueInput.classList.add('phone-input');
	contactValueInput.setAttribute('type', 'text');
	contactValueInput.setAttribute('placeholder', '+7 (___) ___-__-__');
	contactValueInput.setAttribute('autocomplete', 'off');
	contactValueInput.setAttribute('id', 'contact-value-' + String(contactContainerNumber));
	contactValueLabel.setAttribute('for', 'contact-value-' + String(contactContainerNumber));
	
	//добавляем маску к полю ввода
	$(contactValueInput).click().mask("+7 (999) 999-99-99");
	
	valueContainer.appendChild(contactValueLabel);
	valueContainer.appendChild(contactValueInput);
	
	//контейнер для поля ввода комментария
	let commentContainer = document.createElement('div');
	commentContainer.classList.add('col-4');
	
	//надпись для поля ввода комментария
	let commentLabel = document.createElement('label');
	commentLabel.classList.add('form-label');
	commentLabel.innerText = 'Комментарий';
	
	//поле ввода комментария
	let commentInput = document.createElement('input');
	commentInput.classList.add('form-control');
	commentInput.setAttribute('type', 'text');
	commentInput.setAttribute('autocomplete', 'off');
	commentInput.setAttribute('id', 'contact-comment-' + String(contactContainerNumber));
	
	commentLabel.setAttribute('for', 'contact-comment-' + String(contactContainerNumber));
	
	commentContainer.appendChild(commentLabel);
	commentContainer.appendChild(commentInput);
	
	contactInformationContainerRow.appendChild(contactTypeSelectContainer);
	contactInformationContainerRow.appendChild(valueContainer);
	contactInformationContainerRow.appendChild(commentContainer);
	
	//контейнер для кнопки удаления контакта
	let contactRemoveButtonContainer = document.createElement('div');
	contactRemoveButtonContainer.classList.add('col-1');
	contactRemoveButtonContainer.classList.add('d-flex');
	contactRemoveButtonContainer.classList.add('align-items-end');
	contactRemoveButtonContainer.classList.add('justify-content-center');
	contactRemoveButtonContainer.classList.add('py-1');
	
	//кнопка удаления контакта
	let contactRemoveButton = document.createElement('button');
	contactRemoveButton.setAttribute('type', 'button');
	contactRemoveButton.classList.add('btn');
	contactRemoveButton.classList.add('btn-sm');
	contactRemoveButton.classList.add('btn-outline-danger');
	contactRemoveButton.classList.add('link-button');
	contactRemoveButton.setAttribute('id', 'contact-remove-' + String(contactContainerNumber));
	
	contactRemoveButton.addEventListener('click', removeContactButtonHandler);
	
	//значок мусорки в кнопке удаления
	let contactRemoveButtonSvg = document.createElement('span');
	contactRemoveButtonSvg.classList.add('bi');
	contactRemoveButtonSvg.classList.add('bi-trash3');
	
	
	contactRemoveButton.appendChild(contactRemoveButtonSvg);
	contactRemoveButtonContainer.appendChild(contactRemoveButton);
	
	contactInformationContainer.appendChild(contactInformationContainerRow);
	
	contactContainer.appendChild(contactInformationContainer);
	contactContainer.appendChild(contactRemoveButtonContainer);
	
	contactsRepeater.appendChild(contactContainer);
								
	
	
}

function removeContactButtonHandler(event) {
	console.log("Попробовали удалить контакт!");
	console.log(event.target.tagName);
	
	let removeButton = (event.target.tagName == 'SPAN') ? event.target.parentNode : event.target;
	
	console.log(removeButton);
	
	
}