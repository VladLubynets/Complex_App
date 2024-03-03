package libs;

import org.aeonbits.owner.Config;

@Config.Sources(value = "file:./src/main/resources/hiddenConfig.properties")
public interface ConfigHiddenProperties extends Config {
    String login_default();

    String password_default();

    String login_min_value();

    String password_min_value();

    String login_max_value();

    String password_max_value();

    String put_in_login_ValidPassword();

    String put_in_password_ValidLogin();

    String login_api_default();
    String password_api_default();
    String login_api_with_posts();
    String password_api_with_posts();
}
