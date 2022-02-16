const FETCH_COUNTRY_DIAL_CODES_URL = "https://countriesnow.space/api/v0.1/countries/codes";
const FETCH_COUNTRY_STATES_URL= "https://countriesnow.space/api/v0.1/countries/states";
const FETCH_STATE_CITIES_URL = "https://countriesnow.space/api/v0.1/countries/state/cities";
const FETCH_COUNTRY_CITIES_URL = "https://countriesnow.space/api/v0.1/countries/cities";


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
        generateFeedback(targetEle, `An error occured while fecthing states data. error: ${error}`);
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
        generateFeedback(targetEle, `An error occured while fecthing states data. error: ${error}`);
    }

    var stateNode = countryNode.states.filter((c) => c.name == SelectedStates)[0];

    if(countryNode == undefined){
        console.error(`Invalid state selected: ${SelectedStates}`);
        generateFeedback(targetEle, `An error occured while fecthing states data. error: ${error}`);
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
        generateFeedback(targetEle, `An error occured while fecthing cities data. error: ${error}`);
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
        generateNotification(userNameEle, "Data fetched succesfully.");
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