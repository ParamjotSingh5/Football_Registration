package DTO;

import Domain.User;
import Utilities.CustomDefaultsValues;
import Utilities.GenericResponse;
import Utilities.ValidationMessages;
import Utilities.Validations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    public String desiredPositionChecksToString(){
        return IntStream.of(this.desiredpositionchecks)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "));
    }

    public FormRegisterResponseDTO ValidatePatchFormData(){

        FormRegisterResponseDTO parentDTO = new FormRegisterResponseDTO();

        parentDTO.status = true; parentDTO.success = true;

        boolean isUpdateRequired = GenerateReportForPatch(parentDTO);

        if(!isUpdateRequired){
            parentDTO.success = false;
            parentDTO.message = "Invalid request: please mention a valid change.";
        }

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

    private boolean GenerateReportForPatch(FormRegisterResponseDTO parentDTO){

        boolean isUpdateNeeded = false;

        AnalyzeReport(validateUserName(), parentDTO);

        if(this.firstname != null){
            isUpdateNeeded = true;
            AnalyzeReport(validateFirstName(), parentDTO);
        }

        if(this.lastname != null){
            isUpdateNeeded = true;
            AnalyzeReport(validateLastName(), parentDTO);
        }

        if(this.countrydailcodeselect != null){
            isUpdateNeeded = true;
            AnalyzeReport(validateCountryDialCode(), parentDTO);
        }

        if(this.phonenumber != null){
            isUpdateNeeded = true;
            AnalyzeReport(validatePhoneNumber(), parentDTO);
        }

        if(this.email != null){
            isUpdateNeeded = true;
            AnalyzeReport(validateEmail(), parentDTO);
        }

        if(this.agegroup != 0){
            isUpdateNeeded = true;
            AnalyzeReport(validateAgeGroup(), parentDTO);
        }

        if(this.desiredteamradios != 0){
            isUpdateNeeded = true;
            AnalyzeReport(validateDesiredTeam(), parentDTO);
        }

        if(this.desiredpositionchecks != null){
            isUpdateNeeded = true;
            AnalyzeReport(validateDesiredPositions(), parentDTO);
        }

        if(this.address != null){
            isUpdateNeeded = true;
            AnalyzeReport(validateAddress(), parentDTO);
        }

        if(this.pincode != null){
            isUpdateNeeded = true;
            AnalyzeReport(validatePinCode(), parentDTO);
        }

        if(this.countryselect != null){
            isUpdateNeeded = true;
            AnalyzeReport(validateCountry(), parentDTO);
        }

        if(this.stateselect != null){
            isUpdateNeeded = true;
            AnalyzeReport(validateState(), parentDTO);
        }

        if(this.cityselect != null){
            isUpdateNeeded = true;
            AnalyzeReport(validateCity(), parentDTO);
        }
        return isUpdateNeeded;
    }

    private ValidationReport validateUserName(){

        if(this.username == null){
            return new ValidationReport("username", false, ValidationMessages.PARAMETER_REQUIRED.toString());
        }

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

        return new ValidationReport("username", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validateFirstName(){

        if(this.firstname == null){
            return new ValidationReport("firstname", false, ValidationMessages.PARAMETER_REQUIRED.toString());
        }

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

        if(this.lastname == null){
            return new ValidationReport("lastname", false, ValidationMessages.PARAMETER_REQUIRED.toString());
        }

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

        if(this.countrydailcodeselect == null){
            return new ValidationReport("countrydailcodeselect", false, ValidationMessages.PARAMETER_REQUIRED.toString());
        }

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

        if(this.phonenumber == null){
            return new ValidationReport("phonenumber", false, ValidationMessages.PARAMETER_REQUIRED.toString());
        }

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

        if(this.email == null){
            return new ValidationReport("email", false, ValidationMessages.PARAMETER_REQUIRED.toString());
        }

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

        if(this.desiredpositionchecks == null){
            return new ValidationReport("desiredpositionchecks", false, ValidationMessages.PARAMETER_REQUIRED.toString());
        }

        Validations validations = new Validations();

        if(this.desiredpositionchecks.length == 0){
            return new ValidationReport("desiredpositionchecks", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        //TODO: validate if this.desiredpositionchecks exists in the database.

        return new ValidationReport("desiredpositionchecks", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validateAddress(){

        if(this.address == null){
            return new ValidationReport("address", false, ValidationMessages.PARAMETER_REQUIRED.toString());
        }

        this.address = this.address.trim();

        Validations validations = new Validations();

        if(!validations.isSanitizedValue(this.address)){
            return new ValidationReport("address", false, ValidationMessages.CORRUPTED_VALUE.toString());
        }

        return new ValidationReport("address", true, ValidationMessages.VALID.toString());
    }

    private ValidationReport validatePinCode(){

        if(this.pincode == null){
            return new ValidationReport("pincode", false, ValidationMessages.PARAMETER_REQUIRED.toString());
        }

        this.pincode = this.pincode.trim();

        Validations validations = new Validations();

        if(this.pincode.isEmpty()){
            return new ValidationReport("pincode", true, ValidationMessages.VALID.toString());
        }

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

        if(this.countryselect == null){
            return new ValidationReport("countryselect", false, ValidationMessages.PARAMETER_REQUIRED.toString());
        }

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

        if(this.stateselect == null){
            return new ValidationReport("stateselect", false, ValidationMessages.PARAMETER_REQUIRED.toString());
        }

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

        if(this.cityselect == null){
            return new ValidationReport("cityselect", false, ValidationMessages.PARAMETER_REQUIRED.toString());
        }

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

    @Override
    public String toString() {
        return "FormRegisterRequestDTO{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", countrydailcodeselect='" + countrydailcodeselect + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", email='" + email + '\'' +
                ", agegroup=" + agegroup +
                ", desiredteamradios=" + desiredteamradios +
                ", desiredpositionchecks=" + Arrays.toString(desiredpositionchecks) +
                ", address='" + address + '\'' +
                ", pincode='" + pincode + '\'' +
                ", countryselect='" + countryselect + '\'' +
                ", stateselect='" + stateselect + '\'' +
                ", cityselect='" + cityselect + '\'' +
                '}';
    }
}
