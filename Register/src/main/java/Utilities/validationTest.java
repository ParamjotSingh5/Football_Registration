package Utilities;

import org.junit.gen5.api.Assertions;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validationTest {

    public static void main(String[] args) {
        String name =  "param";
        System.out.println(new Validations().isAlphabetOnly(name));
        System.out.println(new Validations().isAlphabetOnly("alert()"));
        System.out.println(new Validations().isAlphabetOnly("!"));
    }

}
