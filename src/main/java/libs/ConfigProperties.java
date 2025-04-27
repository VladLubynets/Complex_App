package libs;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config.properties")
public interface ConfigProperties extends Config {

	@Key("MySQL")
	String MySQL();

	@Key("MySQL_DB")
	String MySQL_DB();

	@Key("MySQL_DB_USER")
	String MySQL_DB_USER();

	@Key("MySQL_DB_PASSWORD")
	String MySQL_DB_PASSWORD();
}