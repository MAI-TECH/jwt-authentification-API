package com.maitech.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@NoArgsConstructor
public class Validation {

    private static final String EMAILREGEX = "";
    private static final String PASSWORDREGEX = "";
    private static final String PHONEREGEX = "";

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAILREGEX);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORDREGEX);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public boolean isValidPhone(String phone) {
        Pattern pattern = Pattern.compile(PHONEREGEX);
        Matcher matcher = pattern.matcher(phone);

        return matcher.matches();
    }
}
