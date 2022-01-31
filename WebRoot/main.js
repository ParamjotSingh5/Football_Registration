
window.addEventListener('load', (event) => {
    
    addMessageEvent('input[id="email"]', "focusout");    
    addMessageEvent('input[type="text"]', "focusout");   
    addMessageEvent('textarea[id="address"]', "focusout");
    addMessageEvent('input[type="radio"]', "change");
    addMessageEvent('input[type="checkbox"]', "change");
    addMessageEvent("select", "change");

    numericAllowedOnly();
});

/**
 * remove all of the options from select list found by selector if keepDefaultOptionFlag param is 
 * set to false, else keep the option with disbaled attribute and removes other options.
 * 
 * @param {String} selector a selector for select list
 * @param {boolean} keepDefaultOptionFlag 
 */
function removeOptionsFromSelect(selector, keepDefaultOptionFlag){

    var combinedSelector = `${selector} option`; 

    document.querySelectorAll(combinedSelector).forEach(
        function (option, idx, list){
            if(keepDefaultOptionFlag){
                if(option.disabled != true){
                    option.remove();
                    return;
                }
                return;
            }
            option.remove();
            return;
        }
    );
}