package libs;

import org.aeonbits.owner.Config;

@Config.Sources(value = "file:./src/main/java/resources/hiddenConfig.properties")
public interface ConfigHiddenProperties extends Config {
    String login();

    String password();

    String login2();

    String password2();

    String login3();

    String password3();

    String login4();

    String password4();

    String login5();

    String password5();

    String login6();

    String password6();
}
