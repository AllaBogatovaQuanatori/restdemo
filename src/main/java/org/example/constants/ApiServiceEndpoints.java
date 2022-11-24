package org.example.constants;

import org.aeonbits.owner.ConfigFactory;
import org.example.utils.config.ServiceConfig;

public class ApiServiceEndpoints {
    private ApiServiceEndpoints() {
        throw new IllegalStateException("Constants class");
    }

    private static final String VERSION = ConfigFactory.create(ServiceConfig.class).basePath();

    public static final String GET_TOKEN = "v1/token";

    public static final String AIRLINES = VERSION + "airlines";
    public static final String AIRLINES_BY_ID = VERSION + "airlines/%s";
    public static final String PASSENGER = VERSION + "passenger";
    public static final String PASSENGER_BY_ID = VERSION + "passenger/%s";

}
