var INVALID_FEEDBACK_STYLE_CLASS = "is-invalid";
var VALID_FEEDBACK_STYLE_CLASS = "is-valid";
var PHONE_NUMBER_LENGTH = 10;
var PINCODE_LENGTH = 6;


function isValidEmail(str)
{
    var reg1 = /^[A-Za-z0-9_\-\.]+\@(\[?)[\w\-\.]+\.([a-zA-Z]{2,8}|[0-9]{1,3})(\]?)\;{0,}$/;
    var reg2 = /\.{2,}/;
    if (str != "" && (!reg1.test(str) || reg2.test(str))) return false;
    return true;
}

/**
 * returns true, if at least 1 check input is checked in the selctor group, else false.
 * return false, if selector did not found any refernce to the elements.
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

//isAtleastCheckInputChecked("input[name='desiredPositionChecks']");
//isAtleastCheckInputChecked("input[name='desiredTeamRadios']");

function isSanitizedValue(val){
    if(val == undefined || val == ""){
        return true;
    }

    // var limitRegex = new RegExp(`^.{0,8000}$`,"g");
    
    // if(!limitRegex.test(val)){
    //     return false;
    // }

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

            // generateFeedback(currentEle, VALIDATIONS.maxLength);
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
//addMessageEvent('input[type="text"]', "focusout");

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
    }   
    else{        

        // var root = "{";
        // for(var e of new FormData(evt.target).entries()){
        //   root += `\"${e[0]}\":\"${e[1]}\", `;
        // }
        // root = root.substring(0, root.length - 2);
        // root += "}";

        var serilizedForm = formToJSON(evt.target);
        PostFormData(serilizedForm);
    }
}

function formToJSON( elem ) {
    var current, entries, item, key, output, value;
    output = {};
    entries = new FormData( elem ).entries();
    // Iterate over values, and assign to item.
    while ( item = entries.next().value )
      {
        // assign to variables to make the code more readable.
        key = item[0];
        value = item[1];
        // Check if key already exist
        if (Object.prototype.hasOwnProperty.call( output, key)) {
          current = output[ key ];
          if ( !Array.isArray( current ) ) {
            // If it's not an array, convert it to an array.
            current = output[ key ] = [ current ];
          }
          current.push( value ); // Add the new value to the array.
        } else {
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
    .then((res) => handlePostFormDataResponse(res));
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

function RespondValdationFailure(failedValidations){

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

    var stateSelectList = document.querySelector("#stateSelect");
    stateSelectList.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
    stateSelectList.classList.remove(VALID_FEEDBACK_STYLE_CLASS);

    var citySelectList = document.querySelector("#citySelect");
    citySelectList.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
    citySelectList.classList.remove(VALID_FEEDBACK_STYLE_CLASS);

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
