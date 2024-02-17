package TestData;

import libs.ConfigProvider;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class TestData {
    public final static String VALID_LOGIN = System.getProperty("validLogin", ConfigProvider.configHiddenProperties.login_default());
    public final static String VALID_PASSWORD = System.getProperty("validPassword", ConfigProvider.configHiddenProperties.password_default());
    public final static String VALID_LOGIN_UPPER_CASE = VALID_LOGIN.toUpperCase(Locale.ROOT);

    public final static String VALID_LOGIN_MIN_SIZE = VALID_LOGIN.substring(0, 3); // at least 3 characters
    public final static String VALID_PASSWORD_MIN_SIZE = VALID_PASSWORD.substring(0, 12); // at least 12 characters

    public final static String VALID_LOGIN_MAX_SIZE ="a".repeat(30);   // at most 30 characters
    public final static String VALID_PASSWORD_MAX_SIZE = "a".repeat(30); // at most 30 characters

    public final static String INVALID_LOGIN = "aacomplex";
    public final static String INVALID_PASSWORD = "123";

    public final static String EMPTY_VALUE = "";

    public static String generateRandomString(int length) {  // randomizer  value for email
        String HigerCaseLetters = "ABCZ";
        return RandomStringUtils.randomAlphanumeric(length) + HigerCaseLetters;
    }

    public static String generateRandomEmail() {  // create random email
        String randomPart = generateRandomString(10);
        String domain = "gmail.com";
        return randomPart + "@" + domain;
    }

    public final static String VALID_LOGIN_API_DEFAULT_10chars =  System.getProperty("loginApiDefault", ConfigProvider.configHiddenProperties.login_api_default());
    public final static String VALID_PASSWORD_API_DEFAULT_12chars = System.getProperty("passwordApiDefault", ConfigProvider.configHiddenProperties.password_api_default());
}
