package io.gatling.computerdatabase.httpprotocols.factories;

import io.gatling.computerdatabase.data.EnvironmentType;
import io.gatling.computerdatabase.httpprotocols.ProdEnvHttpProtocol;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class HttpProtocolFactory {

    public static HttpProtocolBuilder getHttpProtocol(EnvironmentType environmentType) {
        switch(environmentType) {
            case DEV: return null;
            case STAGING: return null;
            case PRE_PROD: return null;
            default: return ProdEnvHttpProtocol.get();
        }
    }
}
