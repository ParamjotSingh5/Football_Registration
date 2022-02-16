
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
        generateFeedback(targetEle, `An error occured while fetching data. error: ${res.msg}`);
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
  