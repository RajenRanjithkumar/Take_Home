package com.scotia.takehome.utils;
import java.util.regex.Pattern;

// Helper class for Junit testing
public class Helper {

    public String userName(String username){

        if(username.length() < 25 && !username.isEmpty()){

            return "valid";

        } else

        {
            return "invalid";
        }

    }

    //User name cannot be just numbers
    public  String isNumeric(String username) {
        try {
            Double.parseDouble(username);
            return "invalid";
        } catch(NumberFormatException e){
            return "valid";
        }
    }

    public String isSpecialCharacters(String username){

        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");

         if (regex.matcher(username).find()) {

            return "invalid";

        } else {

             return "valid";

        }


    }




}
