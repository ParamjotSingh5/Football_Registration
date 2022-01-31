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

    var limitRegex = new RegExp(`^.{0,10000}$`,"g");
    
    if(!limitRegex.test(val)){
        return false;
    }

    var xssRegex = new RegExp("[<>();{}=]+", "g");

    if(xssRegex.test(val)){
        return false;
    }

    return true;
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


function toggleEmailControl(evt){

   if(evt.srcElement.type == "checkbox"){
      var toggleELe = document.getElementById("email"); 

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
            currentEle.classList.add(INVALID_FEEDBACK_STYLE_CLASS);
            generateFeedback(currentEle, VALIDATIONS.numericAllowed);
            event.preventDefault();
            return;
        }

        var currentVal = currentEle.value + key

        if(currentEle.name == "phonenumber" && !isValueLengthOver(currentVal, PHONE_NUMBER_LENGTH)){            
            // currentEle.classList.add(INVALID_FEEDBACK_STYLE_CLASS);
            // generateFeedback(currentEle, VALIDATIONS.maxLength);
            event.preventDefault();
            return;
        }

        if(currentEle.name == "pincode" && !isValueLengthOver(currentVal, PINCODE_LENGTH)){      
            // currentEle.classList.add(INVALID_FEEDBACK_STYLE_CLASS);
            // generateFeedback(currentEle, VALIDATIONS.maxLength);      
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

        //    if(validationResponse.status){              
        //        submitBtn.removeAttribute("disabled");
        //    }
        //    else{
        //     submitBtn.setAttribute("disabled", "disabled");
        //    }
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
            currentEle.classList.add(INVALID_FEEDBACK_STYLE_CLASS);            
            generateFeedback(currentEle, VALIDATIONS.santizationFailure);
            return;
        }
        
        if(currentEle.inputMode != "numeric"){
            currentEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
            return;
        }        

        //below is validations for numeric text             
        if(currentEle.name == "phonenumber" && !isValueCompleteLength(currentEle.value, PHONE_NUMBER_LENGTH)){            
            currentEle.classList.add(INVALID_FEEDBACK_STYLE_CLASS);
            generateFeedback(currentEle, VALIDATIONS.controlledLength);
            evt.preventDefault();
            return;
        }
        else if(currentEle.name == "pincode" && !isValueCompleteLength(currentEle.value, PINCODE_LENGTH)){      
            currentEle.classList.add(INVALID_FEEDBACK_STYLE_CLASS);
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
            currentEle.classList.add(INVALID_FEEDBACK_STYLE_CLASS);                        
            generateFeedback(currentEle, VALIDATIONS.santizationFailure);
            return;
        }

        currentEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
        return;
    }

    else if(currentEle.type == "email"){
        var textValue = currentEle.value;

        if(!isSanitizedValue(textValue)){
            currentEle.classList.add(INVALID_FEEDBACK_STYLE_CLASS);                        
            generateFeedback(currentEle, VALIDATIONS.santizationFailure);
            return;
        }

        if(!isValidEmail(textValue)){
            currentEle.classList.add(INVALID_FEEDBACK_STYLE_CLASS);                        
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

        if(selectedOptionValue == 0){
            currentEle.classList.add(INVALID_FEEDBACK_STYLE_CLASS);
            return;
        }
        
        currentEle.classList.remove(INVALID_FEEDBACK_STYLE_CLASS);
        return;
    }
    
}

function generateFeedback(targetElement, feedbackMessage){      

    var parent = targetElement.closest("div.col-md");

    if(parent == undefined){
        return;
    }

    var feedbackEle =  parent.querySelector(".feedback");

    if(feedbackEle){
        targetElement.classList.add(INVALID_FEEDBACK_STYLE_CLASS);
        feedbackEle.innerText = feedbackMessage;
    }    
}

function validateForm(evt){

    var validationOBJ = formFeildsDisperency();

    if (!validationOBJ.status) {
        evt.preventDefault();
        evt.stopPropagation();

        RespondValdationFailure(validationOBJ.data);
    }   


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
