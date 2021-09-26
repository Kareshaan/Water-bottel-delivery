package com.example.watersy.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    public static final Pattern VALID_SL_PHONE_NUMBER_REGEX = Pattern.compile("^(?:0|94|\\+94)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|912)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$");

    public static boolean isEmailValid(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean isPasswordValid(String password){
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    public static boolean isPasswordsMatching(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

    public static boolean isPhoneValid(String phone){
        Matcher matcher = VALID_SL_PHONE_NUMBER_REGEX.matcher(phone);
        return  matcher.find();
    }


}
