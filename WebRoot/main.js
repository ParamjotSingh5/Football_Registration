var INVALID_FEEDBACK_STYLE_CLASS = "is-invalid";
var VALID_FEEDBACK_STYLE_CLASS = "is-valid";
var VALUE_CHANGED_CLASS = "value-changed";
var SUBMIT_METHOD_TYPE_PATCH = "PATCH";
var SUBMIT_METHOD_TYPE_POST = "POST";
var PHONE_NUMBER_LENGTH = 10;
var PINCODE_LENGTH = 6;

const FETCH_COUNTRY_DIAL_CODES_URL = "https://countriesnow.space/api/v0.1/countries/codes";
const FETCH_COUNTRY_STATES_URL= "https://countriesnow.space/api/v0.1/countries/states";
const FETCH_STATE_CITIES_URL = "https://countriesnow.space/api/v0.1/countries/state/cities";
const FETCH_COUNTRY_CITIES_URL = "https://countriesnow.space/api/v0.1/countries/cities";

window.addEventListener('load', (event) => {
    
    addMessageEvent('input[id="email"]', "change");    
    addMessageEvent('input[type="text"]', "change");   
    addMessageEvent('textarea[id="address"]', "change");
    addMessageEvent('input[type="radio"]', "change");
    addMessageEvent('input[type="checkbox"]', "change");
    addMessageEvent("select", "change");

    numericAllowedOnly();
    fecthCountriesSelectList();

    bindCheckingKeyPressUserName();

    disableElementBySelector("#stateSelect");
    disableElementBySelector("#citySelect");
});

function bindCheckingKeyPressUserName(){
    var userNameEle = document.querySelector("#userName");

    userNameEle.addEventListener("keyup", checkIfUserNameExists);
}

/**
 * remove the options from select list found by selector if keepDefaultOptionFlag param is 
 * set to false, else keep the option with disabled attribute and removes other options.
 * 
 * @param {String} selector a CSS selector for select list
 * @param {boolean} keepDefaultOptionFlag set true to keep options with disbaled attribute, set false to remove all ooptions.
 */
function removeOptionsFromSelect(selector, keepDefaultOptionFlag){

    var targetEle = document.querySelector(selector);

    targetEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
    targetEle.classList.remove(VALID_FEEDBACK_STYLE_CLASS);

    targetEle.querySelectorAll("option").forEach(
        function (option, idx, list){
            if(keepDefaultOptionFlag){
                if(option.disabled != true){
                    option.remove();
                    return;
                }
                else{
                    option.selected = true;
                }
                return;
            }
            option.remove();
            return;
        }
    );
}

function prepareCountriesSelectList(Response){

    removeOptionsFromSelect("#countrySelect", true);
    removeOptionsFromSelect("#countryDailCodeSelect", true);

    disableElementBySelector("#stateSelect");
    disableElementBySelector("#citySelect");

    var targetEle = document.querySelector("#countrySelect");
    var dialCodeList = document.querySelector("#countryDailCodeSelect");
        
    var countries = Response.country;

    if(countries == undefined){                
        generateFeedback(targetEle, `An error occurred while fetching data. error: ${res.msg}`);
    }
    
    countries.forEach(function(country){        
        var countryOption = new Option(country.name, country.code);
        targetEle.add(countryOption);

        var dailCodeOption = new Option(prepareCountryDialCodeText(country.dial_code, country.name), country.dial_code);        
        dialCodeList.add(dailCodeOption);             
        
    });
    
    Array.from(dialCodeList.options).forEach(opt => {if(opt.value == "+91") {opt.selected = true; opt.defaultSelected = true;} })
}

function prepareCountryDialCodeText(dailCode, countryName){
    return `${dailCode} (${countryName})`;        
}

function disableElementBySelector(selector){
    var targetEle = document.querySelector(selector);

    if(targetEle == undefined){
        return;
    }

    targetEle.disabled = true;
}
function enableElementBySelector(selector){
    var targetEle = document.querySelector(selector);

    if(targetEle == undefined){
        return;
    }

    targetEle.disabled = false;
}

function prepareStatesSelectList(Response){    

    enableElementBySelector("#stateSelect");
    disableElementBySelector("#citySelect");

    var targetEle = document.querySelector("#stateSelect");   

    var listData = Response.states;

    // if(listData == undefined){                
    //     generateFeedback(targetEle, `An error occured while fetching data. error: ${res.msg}`);
    // }

    listData.forEach(function(state){        
        var stateOption = new Option(state.name, state.state_code);
        targetEle.add(stateOption);         
    });

    if(listData.length <= 0){
        // country does not have any states in it.
        setDefaultOptionSelected(targetEle);
        generateNotification(targetEle, `${ Response.name} does not have any states in it.`);
        // fetchCitySelectListForCountry();
    }
    else{
        generateNotification(targetEle, `states fetched for ${Response.name}`);
    }    
}

function prepareCitiesSelectList(Response){    

    enableElementBySelector("#citySelect");

    var targetEle = document.querySelector("#citySelect");   

    var listData = Response.cities;

    // if(listData == undefined){                
    //     generateFeedback(targetEle, `An error occured while fetching data. error: ${res.msg}`);
    // }

    listData.forEach(function(city){        
        var cityOption = new Option(city.name, city.name);
        targetEle.add(cityOption);         
    });

    if(listData.length <= 0){
        // state or country does not have any city in it.
        setDefaultOptionSelected(targetEle);
        generateNotification(targetEle, `${Response.msg} does not have any city in it.`);
    }   
    else{
        generateNotification(targetEle, `cities fetched for ${Response.name}`);
    }
}

function markSubmitButtonForOpreation(MethodType){
    var submitBtn = document.getElementById("form-submit");

    submitBtn.setAttribute("data-submit-type", MethodType);
}




function isValidEmail(str)
{
    var reg1 = /^[A-Za-z0-9_\-\.]+\@(\[?)[\w\-\.]+\.([a-zA-Z]{2,8}|[0-9]{1,3})(\]?)\;{0,}$/;
    var reg2 = /\.{2,}/;
    if (str != "" && (!reg1.test(str) || reg2.test(str))) return false;
    return true;
}

/**
 * returns true, if at least 1 check input is checked in the selector group, else false.
 * return false, if selector did not found any reference to the elements.
 * 
 * @param {number} selector a selector for check input group
 * @returns {boolean} boolean
 */
function isAtleastCheckInputChecked(selector){
    
    var inputGroup = document.querySelectorAll(selector);

    if(inputGroup == undefined){
        return false;
    }

    var isChecked = false;

    inputGroup.forEach(        
        function(currentValue, currentIndex, listObj) {
            if(currentValue.checked){
                isChecked = true;
            }
        }
    );

    return isChecked;
}

function isSanitizedValue(val){
    if(val == undefined || val == ""){
        return true;
    }

    var xssRegex = new RegExp("[<>();{}=]+", "g");

    if(xssRegex.test(val)){
        return false;
    }

    return true;
}

function isAlphabatesOnly(val){
    if(val == undefined || val == ""){
        return false;
    }

    var re = new RegExp("^([a-zA-Z]{1,})$");

    return re.test(val);
}

function isNumericKey(val){  

    if(val == undefined || val == ""){
        return false;
    }

    var re = new RegExp("^([0-9]{1,})$");

    return re.test(val);         
}

function isValueLengthOver(value, maxLength){

    var regex = new RegExp(`^[a-z\\d]{1,${maxLength}}$`,"g");

    if(value == ""){
        return true;
    }

    return regex.test(value);
}

function isValueCompleteLength(value, completeLength){

    var regex = new RegExp(`^[a-z\\d]{${completeLength}}$`,"g");

    if(value == ""){
        return false;
    }

    return regex.test(value);
}

function setDefaultOptionSelected(targetElement){
    
    targetElement.querySelectorAll("option").forEach(
        function (option, idx, list){
            if(option.value == 0){
                
                option.value = -1;
                option.disabled = false;

            }
        }
    );   

}

function toggleEmailControl(evt){

   if(evt.srcElement.type == "checkbox"){
      var toggleELe = document.querySelector("input[name='email']"); 

      if(evt.srcElement.checked){
        toggleELe.removeAttribute("disabled");
      }
      else{
        toggleELe.setAttribute("disabled", "disabled");          
      }
   }
}


function numericAllowedOnly(){    

    document.querySelectorAll('input[inputmode="numeric"]').forEach(item => {
        item.addEventListener("keypress", event => {        
           //added this event for key press event
        var key = event.key;
        var currentEle = event.srcElement;

        if(key == undefined){            
            return;
        }     

        if(!isNumericKey(key)){
            generateFeedback(currentEle, VALIDATIONS.numericAllowed);
            event.preventDefault();
            return;
        }

        var currentVal = currentEle.value + key

        if(currentEle.name == "phonenumber" && !isValueLengthOver(currentVal, PHONE_NUMBER_LENGTH)){     
            event.preventDefault();
            return;
        }

        if(currentEle.name == "pincode" && !isValueLengthOver(currentVal, PINCODE_LENGTH)){     
            event.preventDefault();
            return;
        }

        currentEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
        return;

        }, false)
    });
}

//element events

function addMessageEvent(selector, eventType){
        
    document.querySelectorAll(selector).forEach(item => {
        item.addEventListener(eventType, event => {        
           validationMessage(event);
           var validationResponse = formFeildsDisperency();

           var submitBtn = document.getElementById("form-submit");

           if(validationResponse.status){              
               submitBtn.removeAttribute("disabled");
           }
           else{
            submitBtn.setAttribute("disabled", "disabled");
           }

           submitBtn.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
           submitBtn.classList.remove(VALID_FEEDBACK_STYLE_CLASS);

        }, false)
    });
}

function validationMessage(evt){
    var currentEle = evt.srcElement;    
    currentEle.classList.add(VALUE_CHANGED_CLASS);
    
    if(currentEle.type == "text"){
        var textValue = currentEle.value;

        if(textValue == ""){
            return;
        }

        if(!isSanitizedValue(textValue)){          
            generateFeedback(currentEle, VALIDATIONS.santizationFailure);
            return;
        }

        if(currentEle.inputMode == "text"){

            if(!isAlphabatesOnly(currentEle.value)){                       
                generateFeedback(currentEle, VALIDATIONS.alphabatesAllowed);
                return;
            }

            if(currentEle.name == "username"){
                checkIfUserNameExists(evt);
            } 

            currentEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
            return;
        }
        
        if(currentEle.inputMode != "numeric"){            
            currentEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
            return;
        }        

        //below is validations for numeric text             
        if(currentEle.name == "phonenumber" && !isValueCompleteLength(currentEle.value, PHONE_NUMBER_LENGTH)){
            generateFeedback(currentEle, VALIDATIONS.controlledLength);
            evt.preventDefault();
            return;
        }
        else if(currentEle.name == "pincode" && !isValueCompleteLength(currentEle.value, PINCODE_LENGTH)){
            generateFeedback(currentEle, VALIDATIONS.controlledLength);      
            evt.preventDefault();
            return;
        }

        currentEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
        return;        
    }    

    else if(currentEle.type == "textarea"){
        var textValue = currentEle.value;

        if(!isSanitizedValue(textValue)){                       
            generateFeedback(currentEle, VALIDATIONS.santizationFailure);
            return;
        }

        currentEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
        return;
    }

    else if(currentEle.type == "email"){
        var textValue = currentEle.value;

        if(!isSanitizedValue(textValue)){                      
            generateFeedback(currentEle, VALIDATIONS.santizationFailure);
            return;
        }

        if(!isValidEmail(textValue)){                        
            generateFeedback(currentEle, VALIDATIONS.invalidEmail);
            return;
        }
        else{
            currentEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
            return;
        }
    }   

    else if(currentEle.type == "radio" || currentEle.type == "checkbox"){

        var feildGroupName = currentEle.name;
        
        if(feildGroupName == "flexSwitchforemail"){
            return;
        }

        var isAnyChecked = isAtleastCheckInputChecked(`input[name='${feildGroupName}']`);
        
        if(!isAnyChecked){
            
            var ele = document.querySelectorAll(`input[name='${feildGroupName}']`);
            
            ele.forEach(function(currentEle, idx, list){
                currentEle.classList.add(INVALID_FEEDBACK_STYLE_CLASS);
            });

            generateFeedback(currentEle, VALIDATIONS.leastCheckInputSelection);
            return;
        }

        //remove any error validation
        var ele = document.querySelectorAll(`input[name='${feildGroupName}']`);

        ele.forEach(function(currentEle, idx, list){
            currentEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
            currentEle.classList.add(VALUE_CHANGED_CLASS);
        });

        return;
    }

    else if(currentEle.type == "select-one"){

        var selectedOptionValue = currentEle.value;
        var selectListName = currentEle.name;
        var selectedOptionText = currentEle.options[currentEle.selectedIndex].text;

        if(selectedOptionValue == 0 || selectedOptionText == "" || selectListName == ""){
            currentEle.classList.add(INVALID_FEEDBACK_STYLE_CLASS);
            return;
        }      
        currentEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);


        if(selectListName == "countryselect"){
            fecthStatesSelectList(selectedOptionText);   
        }
        else if(selectListName == "stateselect"){
            currentEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
            fetchCitySelectListForStates(selectedOptionText);  
        }

        return;
    }
    
}

function generateFeedback(targetElement, feedbackMessage){      

    var parent = targetElement.closest("div.col-md");

    if(parent == undefined){
        return;
    }

    var feedbackEle =  parent.querySelector(".feedback.invalid-feedback");

    if(feedbackEle){
        targetElement.classList.add(INVALID_FEEDBACK_STYLE_CLASS);
        feedbackEle.innerText = feedbackMessage;
    }    
}

function generateNotification(targetElement, feedbackMessage){      

    var parent = targetElement.closest("div.col-md");

    if(parent == undefined){
        return;
    }

    var feedbackEle =  parent.querySelector(".feedback.valid-feedback");

    if(feedbackEle){
        targetElement.classList.add(VALID_FEEDBACK_STYLE_CLASS);
        feedbackEle.innerText = feedbackMessage;
    }    
}

function validateForm(evt){

    var validationOBJ = formFeildsDisperency();

    evt.preventDefault();

    if (!validationOBJ.status) {
        evt.stopPropagation();

        RespondValdationFailure(validationOBJ.data);
        return;
    }   

    var submitBtn = document.getElementById("form-submit");

    var SubmitMethodType = submitBtn.getAttribute("data-submit-type");

    if(SubmitMethodType == SUBMIT_METHOD_TYPE_PATCH){
        var serilizedForm = formToJSON(evt.target, true);
        PatchFormData(serilizedForm);
    }
    else{
        var serilizedForm = formToJSON(evt.target, false);
        PostFormData(serilizedForm);
    }   
    
}

function formToJSON( elem, filterByChanged ) {
    var entries, item, key, output, value;
    output = {};
    entries = new FormData( elem ).entries();
    // Iterate over values, and assign to item.
    for (var item of entries)
    {
          
        // assign to variables to make the code more readable.
        key = item[0];
        value = item[1];
        
        if(filterByChanged){
            var formFeild = document.getElementsByName(key);

            if(!formFeild[0].classList.contains(VALUE_CHANGED_CLASS)){
                continue;
            }        
        }

        if(key == "desiredpositionchecks"){
            var current;

            //check if key already exists
            if (Object.prototype.hasOwnProperty.call( output, key)) {
                //array already exists, just add value.
                current = output[ key ];                
                current.push( value ); // Add the new value to the array.
            }    
            else{
                current = output[key] = []                
                current.push( value ); 
            }
        }
        else {
          output[ key ] = value;
        }
      }
      return JSON.stringify( output );
}

function PostFormData(formSubmitData){

    fetch('/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json;charset=utf-8'          
        },
        body: formSubmitData
    }).then(res => res.json())
    .then((res) => handlePostFormDataResponse(res))
    .error((err) => handleSubmitDataErrorResponse(err));
}

function PatchFormData(formSubmitData){

    fetch('/register', {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json;charset=utf-8'          
        },
        body: formSubmitData
    }).then(res => res.json())
    .then((res) => handlePatchFormDataResponse(res))
    .error((err) => handleSubmitDataErrorResponse(err));
}

function handlePostFormDataResponse(res){

    //An error occured while processing request post data
    if(res.status === false){
        var submitBtn = document.querySelector("#form-submit");       
        generateFeedback(submitBtn, res.message);
    }

    //Posted form data is invalid as of standard
    if(res.success === false){
        RespondValdationFailure(res.data);
    }

    //user created !!!
    
    var submitBtn = document.querySelector("#form-submit");      
    submitBtn.setAttribute("disabled", "disabled");   
    generateNotification(submitBtn, "User registered succefully !!!.");
    
    ResetForm();
}

function handlePatchFormDataResponse(res){

    //An error occured while processing request post data
    if(res.status === false){
        var submitBtn = document.querySelector("#form-submit");       
        generateFeedback(submitBtn, res.message);
    }

    //Posted form data is invalid as of standard
    if(res.success === false){
        RespondValdationFailure(res.data);
    }

    //user updated !!!
    
    var submitBtn = document.querySelector("#form-submit");      
    submitBtn.setAttribute("disabled", "disabled");   
    generateNotification(submitBtn, "User updated succefully !!!.");
    
    ResetForm();
}

function handleSubmitDataErrorResponse(error){

    console.log(error);

    var submitBtn = document.querySelector("#form-submit");       
    generateFeedback(submitBtn, "Unable to perform the opreation.");
}

function RespondValdationFailure(failedValidations){
    loadGif();

    failedValidations.forEach(function(eachObj){

        if(eachObj.isValid){
            //if valid go to next iteration
            return;
        }

        var ele = document.querySelectorAll(`[name="${eachObj.feildname}"`); 

        if(ele == undefined){
            return;
        }

        ele.forEach(function(currentElement, idx, list){            
            generateFeedback(currentElement, eachObj.feedbackMesssage);
        })

        return;
    });
}


function ResetForm(){

    var usernameEle = document.querySelector("#userName");
    usernameEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
    usernameEle.classList.remove(VALID_FEEDBACK_STYLE_CLASS);

    var toggleELe = document.querySelector("input[name='email']");
    toggleELe.setAttribute("disabled", "disabled"); 

    var stateSelectList = document.querySelector("#stateSelect");
    stateSelectList.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
    stateSelectList.classList.remove(VALID_FEEDBACK_STYLE_CLASS);

    var citySelectList = document.querySelector("#citySelect");
    citySelectList.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
    citySelectList.classList.remove(VALID_FEEDBACK_STYLE_CLASS);

    disableFetchUserDataBtn();
    markSubmitButtonForOpreation(SUBMIT_METHOD_TYPE_POST);

    document.getElementById("registration-form").reset();
}

function checkIfUserNameExists(event){
    var userNameEle = event.srcElement;

    var providedUserName = userNameEle.value;    

    disableFetchUserDataBtn();
    userNameEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
    userNameEle.classList.remove(VALID_FEEDBACK_STYLE_CLASS);

    if(providedUserName == ''){        
        return;
    }

    if(!isSanitizedValue(providedUserName)){
        return;
    }

    if(!isAlphabatesOnly(providedUserName)){
        return;
    }

    var requestOptions = {
        method: 'GET'
    };
      
    fetch(`/username-taken?username=${providedUserName}`, requestOptions)
    .then(response => response.json())
    .then((result) => {
        handleCheckUsernameResponse(result);
    })
    .catch(error => console.log('error', error));

}

function handleCheckUsernameResponse(res){

    var userNameEle = document.querySelector("#userName");

    if(!res.status){
        console.log(`An error occured while fetching username data. error: ${res.message}`)
        return;
    }
    if(!res.success){        
        return;
    }

    generateNotification(userNameEle, VALIDATIONS.usernameAlreadyExists);
    enableFetchUserDataBtn();
    return;
}

function disableFetchUserDataBtn(){
    var fetchUserDataBtn = document.querySelector("#retriveUserDataBtn");

    fetchUserDataBtn.disabled = true;
}

function enableFetchUserDataBtn(){
    var fetchUserDataBtn = document.querySelector("#retriveUserDataBtn");

    fetchUserDataBtn.disabled = false;
}




var stateObjs = {
    "country": [
        {
            "name": "India",
            "code": "IN",
            "dial_code": "+91",
            "states": [
                {
                    "name": "Punjab",
                    "state_code": "PB",
                    "cities": [
                        {
                            "name": "Amritsar"                            
                        },
                        {
                            "name": "Nabha"                            
                        }
                    ]
                },
                {
                    "name": "Rajasthan",
                    "state_code": "RJ",
                    "cities": [
                        {
                            "name": "Jaisalmer"                            
                        },
                        {
                            "name": "Jaipur"
                        }
                    ]
                }
            ]
        },
        {
            "name": "Norway",
            "code": "NO",
            "dial_code": "+47",
            "states": [
                {
                    "name": "Oslo",
                    "state_code": "03",
                    "cities": [
                        {
                            "name": "Drammen"
                        },
                        {
                            "name": "Fredrikstad"
                        }
                    ]
                },
                {
                    "name": "Vestfold",
                    "state_code": "07",
                    "cities": [
                        {
                            "name": "Notodden"                           
                        },
                        {
                            "name": "Porsgrunn"
                        }
                    ]
                }
            ]
        },
        {
            "name": "Denmark",
            "code": "DK",
            "dial_code": "+45",
            "states": [
                {
                    "name": "North Denmark",
                    "state_code": "81",
                    "cities": [
                        {
                            "name": "Aras"
                        },
                        {
                            "name": "Skagen"
                        }
                    ]
                },
                {
                    "name": "Zealand",
                    "state_code": "85",
                    "cities": [
                        {
                            "name": "Faxe"                            
                        },
                        {
                            "name": "Greve"
                        }
                    ]
                }
            ]
        }
    ]
}


/**
 * executes a GET fetch request to the resource. return object will contains data prop having an array of 
 * type country in ideal case, else error will be set to false and msg prop will have error message.
 * 
 * @returns returns a resourceResponse type object.
 */
function fecthCountriesSelectList(){

    prepareCountriesSelectList(stateObjs);

}

function fecthStatesSelectList(selectedCountry){
    
    var targetEle = document.querySelector("#stateSelect");
    targetEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
    targetEle.classList.remove(VALID_FEEDBACK_STYLE_CLASS);

    removeOptionsFromSelect("#stateSelect", true);
    removeOptionsFromSelect("#citySelect", true);
   
    var countryNode = stateObjs.country.filter((c) => c.name == selectedCountry)[0];

    if(countryNode == undefined){
        console.error(`Invalid country selected: ${selectedCountry}`);
        generateFeedback(targetEle, `An error occurred while fetching states data. error: ${error}`);
    }

    prepareStatesSelectList(countryNode);
}

function fetchCitySelectListForStates(SelectedStates){

    var selectedCountry = document.querySelector("#countrySelect");
    var countryName = selectedCountry.options[selectedCountry.selectedIndex].text;

    var targetEle = document.querySelector("#citySelect");
    targetEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
    targetEle.classList.remove(VALID_FEEDBACK_STYLE_CLASS);

    removeOptionsFromSelect("#citySelect", true);
    
    var countryNode = stateObjs.country.filter((c) => c.name == countryName)[0];
    
    if(countryNode == undefined){
        console.error(`Invalid country selected: ${countryName}`);
        generateFeedback(targetEle, `An error occurred while fetching states data. error: ${error}`);
    }

    var stateNode = countryNode.states.filter((c) => c.name == SelectedStates)[0];

    if(countryNode == undefined){
        console.error(`Invalid state selected: ${SelectedStates}`);
        generateFeedback(targetEle, `An error occurred while fetching states data. error: ${error}`);
    }

    prepareCitiesSelectList(stateNode);
}

function fetchCitySelectListForCountry(){
    
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var selectedCountry = document.querySelector("#countrySelect");
    var countryName = selectedCountry.options[selectedCountry.selectedIndex].text;

    var raw = JSON.stringify({country: countryName});

    var myInit = { 
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow',
        //cache: 'no-cache'
    };
    
    var myRequest = new Request(FETCH_COUNTRY_CITIES_URL,myInit);

    var parsedResponse = new resourceResponse(true, "", null);

    var targetEle = document.querySelector("#citySelect");
    targetEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
    targetEle.classList.remove(VALID_FEEDBACK_STYLE_CLASS);

    removeOptionsFromSelect("#citySelect", true);
      
    fetch(myRequest)
    .then(response => response.json())
    .then(result => {
        parsedResponse = result; 
        
        if(parsedResponse.error){
            throw new Error(parsedResponse.msg);
        }        

        prepareCitiesSelectList(parsedResponse);
    })
    .catch(error => {        
        console.error(error);
        generateFeedback(targetEle, `An error occurred while fetching cities data. error: ${error}`);
    });
}


function fetchUserDataByUserName(){
    var userNameEle = document.querySelector("#userName");

    var providedUserName = userNameEle.value;  

    userNameEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);

    ResetAllFeildValueChangedStatus();
    
    userNameEle.classList.add(VALUE_CHANGED_CLASS);

    var requestOptions = {
        method: 'GET'
    };

    fetch(`/user?username=${providedUserName}`, requestOptions)
    .then(response => response.json())
    .then((result) => {
        setUserData(result.data);                      
        generateNotification(userNameEle, "Data fetched successfully.");
        markSubmitButtonForOpreation(SUBMIT_METHOD_TYPE_PATCH);
    })
    .catch((error) =>
    {                      
        generateFeedback(userNameEle, VALIDATIONS.internalServError);
        console.log('error', error)
    });

}

function setUserData(userDataJSON){
    
    for (var key in userDataJSON){
        var value = userDataJSON[key];
        
        var elementList = document.getElementsByName(key);     
        
        for (let curElement of elementList) {

            // curElement.style.webkitAnimationName = '';
            // curElement.classList.add("alerts-border");

            if(curElement.type == "text" || curElement.type == "email" || curElement.type == "textarea" ){
                curElement.value = value;
            }

            else if(curElement.type == "select-one"){

                curElement.value = value;

                var selectListName = curElement.name;
                var selectedOptionText = curElement.options[curElement.selectedIndex].text;

                if(selectListName == "countryselect"){
                    fecthStatesSelectList(selectedOptionText);   
                }
                else if(selectListName == "stateselect"){
                    curElement.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
                    fetchCitySelectListForStates(selectedOptionText);  
                }
            }

            else if(curElement.type == "radio"){               
                if(curElement.value == value){
                    curElement.checked = true;
                }      
                else{
                    curElement.checked = false;
                }       
            }

            else if(curElement.type == "checkbox"){                
                if(value.constructor == [].constructor){
                    if(value.includes(parseInt(curElement.value))){
                        curElement.checked = true;
                    }
                    else{
                        curElement.checked = false;
                    }  
                }                    
            }
        }
    }
}


function ResetAllFeildValueChangedStatus(){
    var elements = document.getElementsByClassName(VALUE_CHANGED_CLASS);

    for (const c of elements) {
        c.classList.remove(VALUE_CHANGED_CLASS);    
    }      
}


class ValidationResponse {
    /**
     * create new instance of ValidationData
     * 
     * @param {boolean} status set false, if there is any validation error, else true.
     * @param {boolean} success set false, if there is any error occured while processing, else true.
     */
    constructor(status, success) {
      this.status = status;
      this.success = success;

      this.data = [];
    }

    /**
     * add new failed validation feild into data
     * 
     * @param {ValidationData} validationData
     */
    addInvalidFeild(validationData){

        var objIdx = this.data.findIndex(c=> c.feildname === validationData.feildname )

        if(objIdx < 0){
            this.data.push(validationData);
        }
        else{
            this.data[objIdx].isValid = validationData.isValid;
            this.data[objIdx].feedbackMesssage = validationData.feedbackMesssage;
        }        
    }
}

class ValidationData{

    /**
     * create new instance of ValidationData
     * 
     * @param {string} feildname selector feild attribute name
     * @param {boolean} isValid is feild data valid
     * @param {string} feedbackMesssage valiation failure message
     */
    constructor(feildname, isValid, feedbackMesssage){
        this.feildname = feildname;
        this.isValid = isValid;
        this.feedbackMesssage = feedbackMesssage;
    }
}

/**
 * method will validate .form-feild form, returns instance with data corrosponding to invlid feilds data.
 * 
 * @returns {ValidationData} validationData
 */
  
function formFeildsDisperency(){

    var validationRes = new ValidationResponse(true, true);

    var formFeilds = document.querySelectorAll(".form-feild");

    formFeilds.forEach(        
        function(currentEle, currentIndex, listObj) {      

            if(currentEle.type == "text"){
                var textValue = currentEle.value;

                if(textValue == undefined || textValue == ""){

                    if(currentEle.name == "pincode" || currentEle.name == "lastname"){
                        return;
                    }

                    validationRes.status = false;
                    validationRes.addInvalidFeild(new ValidationData(currentEle.name, false, VALIDATIONS.emptyValue));
                    //go to next iteration
                    return;
                }

                if(currentEle.inputMode == "text"){

                    if(!isAlphabatesOnly(currentEle.value)){                        
                        validationRes.status = false;
                        validationRes.addInvalidFeild(new ValidationData(currentEle.name, false, VALIDATIONS.alphabatesAllowed));
                        return;
                    } 
                }
        
                if(!isSanitizedValue(textValue)){
                    validationRes.status = false;
                    validationRes.addInvalidFeild(new ValidationData(currentEle.name, false, VALIDATIONS.santizationFailure));
                    //go to next iteration
                    return;
                }
                
                if(currentEle.inputMode != "numeric"){                    
                    return;
                }        
                
                if(!isNumericKey(currentEle.value)){
                    validationRes.status = false;
                    validationRes.addInvalidFeild(new ValidationData(currentEle.name, false, VALIDATIONS.numericAllowed));
                    return;
                }

                if(currentEle.name == "phonenumber" && !isValueCompleteLength(currentEle.value, PHONE_NUMBER_LENGTH)){            
                    validationRes.status = false;
                    validationRes.addInvalidFeild(new ValidationData(currentEle.name, false, VALIDATIONS.controlledLength));
                    return;
                }
                else if(currentEle.name == "pincode" && !isValueCompleteLength(currentEle.value, PINCODE_LENGTH)){            
                    validationRes.status = false;
                    validationRes.addInvalidFeild(new ValidationData(currentEle.name, false, VALIDATIONS.controlledLength));
                    return;
                }

                return;        
            }    
        
            else if(currentEle.type == "textarea"){
                var textValue = currentEle.value;
        
                if(!isSanitizedValue(textValue)){
                    validationRes.status = false;
                    validationRes.addInvalidFeild(new ValidationData(currentEle.name, false, VALIDATIONS.santizationFailure));
                    return;
                }

                return;
            }
        
            else if(currentEle.type == "email"){
                var textValue = currentEle.value;

                if(textValue == undefined || textValue == ""){
                    validationRes.status = false;
                    validationRes.addInvalidFeild(new ValidationData(currentEle.name, false, VALIDATIONS.emptyValue));
                    //go to next iteration
                    return;
                }
        
                if(!isSanitizedValue(textValue)){
                    validationRes.status = false;
                    validationRes.addInvalidFeild(new ValidationData(currentEle.name, false, VALIDATIONS.santizationFailure));
                    return;
                }
        
                if(!isValidEmail(textValue)){
                    validationRes.status = false;
                    validationRes.addInvalidFeild(new ValidationData(currentEle.name, false, VALIDATIONS.invalidEmail));
                    return;
                }

                return;
            }

            else if(currentEle.type == "radio" || currentEle.type == "checkbox"){

                var feildGroupName = currentEle.name;
                var isAnyChecked = isAtleastCheckInputChecked(`input[name='${feildGroupName}']`);
                
                if(!isAnyChecked){
                    validationRes.status = false;
                    validationRes.addInvalidFeild(new ValidationData(currentEle.name, false, VALIDATIONS.leastCheckInputSelection));
                    return;
                }

                //remove any error validation
                var ele = document.querySelectorAll(`input[name='${feildGroupName}']`);

                ele.forEach(function(currentEle, idx, list){
                    currentEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
                })

                return;
            }

            else if(currentEle.type == "select-one"){

                var selectedOptionValue = currentEle.value;

                if(selectedOptionValue == 0){
                    validationRes.status = false;
                    validationRes.addInvalidFeild(new ValidationData(currentEle.name, false, VALIDATIONS.selectValidOption));
                    return;
                }

                return;
            }
        }
    );    

    return validationRes;
}





function loadGif(){

    var GifImg = document.querySelector("#securityGif");
    
    var curGifID = getRandomInt(1, 5);

    GifImg.src = `gifs/${curGifID}.gif`;

    var modalBtn = document.querySelector("#raiseSecurityModal");
    modalBtn.click();
}

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min) + min); //The maximum is exclusive and the minimum is inclusive
  }
  