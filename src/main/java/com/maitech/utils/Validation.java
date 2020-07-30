package com.maitech.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@NoArgsConstructor
public class Validation {

    private static final String EMAILREGEX = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+(?:[a-zA-Z]{2}|aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel)$";
    private static final String PASSWORDREGEX = "^(?=.*[a-z].*[a-z])(?=.*[A-Z].*[A-Z])(?=.*\\d.*\\d)(?=.*\\W.*\\W)[a-zA-Z0-9\\S]{9,}$";
    private static final String PHONEREGEX = "^(\\(?\\+?[0-9]*\\)?)?[0-9_\\- \\(\\)]*$";

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
