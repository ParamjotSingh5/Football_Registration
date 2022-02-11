class country{

    /**
     * create new instance of country
     * 
     * @param {string} name selector feild attribute name
     * @param {boolean} code is feild data valid
     * @param {string} dailcode valiation failure message
     */
    constructor(name, code, dailcode){
        this.name = name;
        this.code = code;
        this.dailcode = dailcode;
    }

}