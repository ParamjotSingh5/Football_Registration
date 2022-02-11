package DTO;

public class ValidationReport {

    public ValidationReport(){
        this.feildname = "";
        this.isValid = true;
        this.feedbackMesssage = "";
    }

    public ValidationReport(String fieldname, boolean isValid, String feedbackMesssage){
        this.feildname = fieldname;
        this.isValid = isValid;
        this.feedbackMesssage = feedbackMesssage;
    }

    public String feildname;
    public boolean isValid;
    public String feedbackMesssage;
}
