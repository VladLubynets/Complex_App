package TestData;

import libs.ConfigProvider;

public class TestData {
    public final static String VALID_LOGIN = ConfigProvider.configHiddenProperties.login();
    public final static String VALID_PASSWORD = ConfigProvider.configHiddenProperties.password();
    public final static String VALID_LOGIN_UPPER_CASE = ConfigProvider.configHiddenProperties.login2();
    public final static String VALID_PASSWORD_UPPER_CASE = ConfigProvider.configHiddenProperties.password2();

    public final static String VALID_LOGIN_MIN_SIZE = ConfigProvider.configHiddenProperties.login3();
    public final static String VALID_PASSWORD_MIN_SIZE = ConfigProvider.configHiddenProperties.password3();

    public final static String VALID_LOGIN_MAX_SIZE = ConfigProvider.configHiddenProperties.login4();
    public final static String VALID_PASSWORD_MAX_SIZE = ConfigProvider.configHiddenProperties.password4();

    public final static String INVALID_LOGIN = "aqacomplex";
    public final static String INVALID_PASSWORD = "123";

    public final static String INVALID_LOGIN_REVERSE = ConfigProvider.configHiddenProperties.login5();
    public final static String INVALID_PASSWORD_REVERSE = ConfigProvider.configHiddenProperties.password5();
}
