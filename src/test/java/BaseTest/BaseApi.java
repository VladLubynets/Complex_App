package BaseTest;

import api.ApiHelper;
import org.apache.log4j.Logger;

public class BaseApi {

    public ApiHelper apiHelper;
    public Logger logger;

    public BaseApi() {
        this.apiHelper = new ApiHelper();
        this.logger = Logger.getLogger(getClass());
    }
}