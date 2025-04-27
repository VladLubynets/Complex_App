package libs;

import java.sql.SQLException;

import TestData.TestData;
import org.aeonbits.owner.ConfigFactory;

public class MySQL_Database {
    private static ConfigProperties configProperties = ConfigFactory.create(ConfigProperties.class);


    public static Database getDataBase() throws SQLException, ClassNotFoundException {
        return new Database(
                configProperties.MySQL(),
                configProperties.MySQL_DB(),
                TestData.VALID_LOGIN_FOR_DB,
                TestData.VALID_PASSWORD_FOR_DB
        );
    }

}