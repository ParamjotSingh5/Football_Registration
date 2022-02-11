package DTO;

import Domain.User;
import Utilities.GenericResponse;
import Utilities.ValidationMessages;
import Utilities.Validations;

public class FormRegisterRequestDTO {
    public String username;
    public String firstname;
    public String lastname;
    public String countrydailcodeselect;
    public String phonenumber;
    public String email;
    public int agegroup;
    public int desiredteamradios;
    public int[] desiredpositionchecks;
    public String address;
    public String pincode;
    public String countryselect;
    public String stateselect;
    public String cityselect;

    public FormRegisterResponseDTO ValidateFormData(){

        FormRegisterResponseDTO parentDTO = new FormRegisterResponseDTO();

        parentDTO.status = true; parentDTO.success = true;

        GenerateReport(parentDTO);

        return parentDTO;
    }

    private void AnalyzeReport(ValidationReport fieldReport, FormRegisterResponseDTO parentResponseDTO){
        if(!fieldReport.isValid){
            parentResponseDTO.status = true;
            parentResponseDTO.success = false;
            parentResponseDTO.data.add(fieldReport);
        }
    }

    private void GenerateReport(FormRegisterResponseDTO parentDTO){

        AnalyzeReport(validateUserName(), parentDTO);

        AnalyzeReport(validateFirstName(), parentDTO);

        AnalyzeReport(validateLastName(), parentDTO);

        AnalyzeReport(validateCountryDialCode(), parentDTO);

        AnalyzeReport(validatePhoneNumber(), parentDTO);

        AnalyzeReport(validateEmail(), parentDTO);

        AnalyzeReport(validateAgeGroup(), parentDTO);

        AnalyzeReport(validateDesiredTeam(), parentDTO);

        AnalyzeReport(validateDesiredPositions(), parentDTO);

        AnalyzeReport(validateAddress(), parentDTO);

        AnalyzeReport(validatePinCode(), parentDTO);

        AnalyzeReport(validateCountry(), parentDTO);

        AnalyzeReport(validateState(), parentDTO);

        AnalyzeReport(validateCity(), parentDTO);
    }

    private ValidationReport validateUserName(){

        this.username = this.username.trim();

        Validations validations = new Validations();

        if(this.username.trim().isEmpty()){
            return new ValidationReport("username", false, ValidationMessages.VALUE_REQUIRED.toString());
        }

        else if(!validations.isSanitizedValue(this.username)){
            return new ValidationReport("username", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        else if(!validations.isAlphabetOnly(this.username)){
            return new ValidationReport("username", false, ValidationMessages.ONLY_ALPHABET.toString());
        }

        GenericResponse res = new User().isUserNameAlreadyExists(this.username);

        if(res.status && res.success){
            return new ValidationReport("username", false, ValidationMessages.USERNAME_EXISTS.toString());
        }

        return new ValidationReport("username", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validateFirstName(){

        this.firstname = this.firstname.trim();

        Validations validations = new Validations();

        if(this.firstname.trim().isEmpty()){
            return new ValidationReport("firstname", false, ValidationMessages.VALUE_REQUIRED.toString());
        }

        else if(!validations.isSanitizedValue(this.firstname)){
            return new ValidationReport("firstname", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        else if(!validations.isAlphabetOnly(this.firstname)){
            return new ValidationReport("firstname", false, ValidationMessages.ONLY_ALPHABET.toString());
        }

        return new ValidationReport("firstname", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validateLastName(){

        this.lastname = this.lastname.trim();

        Validations validations = new Validations();

        if(this.lastname.trim().isEmpty()){
            return new ValidationReport("lastname", true, ValidationMessages.VALID.toString());
        }

        if(!validations.isSanitizedValue(this.lastname)){
            return new ValidationReport("lastname", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        else if(!validations.isAlphabetOnly(this.lastname)){
            return new ValidationReport("lastname", false, ValidationMessages.ONLY_ALPHABET.toString());
        }

        return new ValidationReport("lastname", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validateCountryDialCode(){

        this.countrydailcodeselect = this.countrydailcodeselect.trim();

        Validations validations = new Validations();

        if(this.countrydailcodeselect.isEmpty()){
            return new ValidationReport("countrydailcodeselect", false, ValidationMessages.VALUE_REQUIRED.toString());
        }

        else if(!validations.isSanitizedValue(this.countrydailcodeselect)){
            return new ValidationReport("countrydailcodeselect", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        return new ValidationReport("countrydailcodeselect", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validatePhoneNumber(){

        this.phonenumber = this.phonenumber.trim();

        Validations validations = new Validations();

        if(this.phonenumber.isEmpty()){
            return new ValidationReport("phonenumber", false, ValidationMessages.VALUE_REQUIRED.toString());
        }

        else if(!validations.isSanitizedValue(this.phonenumber)){
            return new ValidationReport("phonenumber", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        else if(!validations.isNumericOnly(this.phonenumber)){
            return new ValidationReport("phonenumber", false, ValidationMessages.ONLY_NUMERIC.toString());
        }

        else if(!validations.isValueOfCompleteLength(10, this.phonenumber)){
            return new ValidationReport("phonenumber", false, ValidationMessages.INVALID_LENGTH.toString());
        }

        return new ValidationReport("phonenumber", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validateEmail(){

        this.email = this.email.trim();

        Validations validations = new Validations();

        if(this.email.isEmpty()){
            return new ValidationReport("email", false, ValidationMessages.VALUE_REQUIRED.toString());
        }

        else if(!validations.isSanitizedValue(this.email)){
            return new ValidationReport("email", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        else if(!validations.isValidEmail(this.email)){
            return new ValidationReport("email", false, ValidationMessages.INVALID_Email.toString());
        }

        return new ValidationReport("email", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validateAgeGroup(){

        Validations validations = new Validations();

        if(this.agegroup <= 0){
            return new ValidationReport("agegroup", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        //TODO: validate if this.agegroup exists in the database.

        return new ValidationReport("agegroup", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validateDesiredTeam(){
        Validations validations = new Validations();

        if(this.desiredteamradios <= 0){
            return new ValidationReport("desiredteamradios", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        //TODO: validate if this.desiredteamradios exists in the database.

        return new ValidationReport("desiredteamradios", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validateDesiredPositions(){
        Validations validations = new Validations();

        if(this.desiredpositionchecks.length == 0){
            return new ValidationReport("desiredpositionchecks", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        //TODO: validate if this.desiredpositionchecks exists in the database.

        return new ValidationReport("desiredpositionchecks", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validateAddress(){

        this.address = this.address.trim();

        Validations validations = new Validations();

        if(!validations.isSanitizedValue(this.address)){
            return new ValidationReport("address", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        return new ValidationReport("address", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validatePinCode(){

        this.pincode = this.pincode.trim();

        Validations validations = new Validations();

        if(!validations.isSanitizedValue(this.pincode)){
            return new ValidationReport("pincode", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        else if(!validations.isNumericOnly(this.pincode)){
            return new ValidationReport("pincode", false, ValidationMessages.ONLY_NUMERIC.toString());
        }

        else if(!validations.isValueOfCompleteLength(6, this.pincode)){
            return new ValidationReport("pincode", false, ValidationMessages.INVALID_LENGTH.toString());
        }

        return new ValidationReport("pincode", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validateCountry(){

        this.countryselect = this.countryselect.trim();

        Validations validations = new Validations();

        if(this.countryselect.isEmpty()){
            return new ValidationReport("countryselect", false, ValidationMessages.VALUE_REQUIRED.toString());
        }

        if(!validations.isSanitizedValue(this.countryselect)){
            return new ValidationReport("countryselect", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        //TODO: validate if this.countryselect exists in the database.

        return new ValidationReport("countryselect", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validateState(){

        this.stateselect = this.stateselect.trim();

        Validations validations = new Validations();

        if(this.stateselect.isEmpty()){
            return new ValidationReport("stateselect", false, ValidationMessages.VALUE_REQUIRED.toString());
        }

        if(!validations.isSanitizedValue(this.stateselect)){
            return new ValidationReport("stateselect", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        //TODO: validate if this.stateselect exists in the database.

        return new ValidationReport("stateselect", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validateCity(){

        this.cityselect = this.cityselect.trim();

        Validations validations = new Validations();

        if(this.cityselect.isEmpty()){
            return new ValidationReport("cityselect", false, ValidationMessages.VALUE_REQUIRED.toString());
        }

        if(!validations.isSanitizedValue(this.cityselect)){
            return new ValidationReport("cityselect", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        //TODO: validate if this.cityselect exists in the database.

        return new ValidationReport("cityselect", true, ValidationMessages.VALID.toString());
    }

}
