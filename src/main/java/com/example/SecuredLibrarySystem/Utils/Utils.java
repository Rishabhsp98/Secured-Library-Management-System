package com.example.SecuredLibrarySystem.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {


    public static Map<String,String> getAuthoritiesForUser(){

        Map<String,String> authorities = new HashMap<>();

        List<String> studentAuthorities = Arrays.asList(Constants.STUDENT_SELF_INFO_AUTHORITY);

        List<String> adminAuthorities = Arrays.asList
                (
                        Constants.STUDENT_INFO_AUTHORITY,
                        Constants.CREATE_ADMIN_AUTHORITY
                );

        //converting List into String of authorities using delimeter
        String StudentAuthoritiesAsString = String.join(Constants.DELIMITER,studentAuthorities);

        String AdminAuthoritiesAsString = String.join(Constants.DELIMITER,adminAuthorities);

        authorities.put(Constants.STUDENT_USER,StudentAuthoritiesAsString);
        authorities.put(Constants.ADMIN_USER,AdminAuthoritiesAsString);


        return authorities;
    }
}
