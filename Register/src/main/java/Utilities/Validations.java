package Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {

    public boolean isSanitizedValue(String value){
        Pattern p = Pattern.compile("[<>();!{}=]+");
        Matcher m = p.matcher(value);

        //checks, if any identifier from p regex exists in value.
        return !m.find();
    }

    public boolean isAlphabetOnly(String value){
        return value.matches("[a-zA-Z]+");
    }

    public boolean isNumericOnly(String value){
        return value.matches("(\\d+)");
    }

    public boolean isValueOfCompleteLength(int length, String value){

        String regex = new StringBuilder().append("^[a-z\\d]{").append(length).append("}$").toString();

        return value.matches(regex);
    }

    public boolean isValidEmail(String value){
        String reg1 = "^[A-Za-z0-9_\\-\\.]+\\@(\\[?)[\\w\\-\\.]+\\.([a-zA-Z]{2,8}|[0-9]{1,3})(\\]?)\\;*$";
        String reg2 = "\\.{2,}";

        return value.matches(reg1) || value.matches(reg2);
    }
}
