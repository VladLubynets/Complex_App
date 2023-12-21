package TestData;

import libs.ConfigProvider;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class TestData {
    public final static String VALID_LOGIN = System.getProperty("validLogin", ConfigProvider.configHiddenProperties.login_default());
    public final static String VALID_PASSWORD = System.getProperty("validPassword", ConfigProvider.configHiddenProperties.password_default());
    public final static String VALID_LOGIN_UPPER_CASE = System.getProperty("validLoginUpperCase", ConfigProvider.configHiddenProperties.login_default().toUpperCase(Locale.ROOT));
    public final static String VALID_PASSWORD_UPPER_CASE = System.getProperty("validPasswordUpperCase", ConfigProvider.configHiddenProperties.password_default().toUpperCase(Locale.ROOT));

    public final static String VALID_LOGIN_MIN_SIZE = System.getProperty("validLoginMinSize", ConfigProvider.configHiddenProperties.login_min_value());
    public final static String VALID_PASSWORD_MIN_SIZE = System.getProperty("validPasswordMinSize", ConfigProvider.configHiddenProperties.password_min_value());

    public final static String VALID_LOGIN_MAX_SIZE = System.getProperty("validLoginMaxSize", ConfigProvider.configHiddenProperties.login_max_value());
    public final static String VALID_PASSWORD_MAX_SIZE = System.getProperty("validPasswordMaxSize", ConfigProvider.configHiddenProperties.password_max_value());

    public final static String INVALID_LOGIN = "aacomplex";
    public final static String INVALID_PASSWORD = "123";

    public final static String INVALID_LOGIN_EMPTY = "";
    public final static String INVALID_PASSWORD_EMPTY = "";

    public final static String INVALID_LOGIN_REVERSE = System.getProperty("invalidLoginReverse", ConfigProvider.configHiddenProperties.put_in_login_ValidPassword());
    public final static String INVALID_PASSWORD_REVERSE = System.getProperty("invalidPasswordReverse", ConfigProvider.configHiddenProperties.put_in_password_ValidLogin());
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
}
