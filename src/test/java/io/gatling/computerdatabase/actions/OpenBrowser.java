package io.gatling.computerdatabase.actions;

import io.gatling.computerdatabase.data.ThinkTime;
import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static java.time.temporal.ChronoUnit.*;

/**
 * Mimics the action of "Opening a browser" on the specified endpoint by performing a GET request. Random think time is
 * applied once the request is completed to better simulate real user behaviour and pauses between actions.
 *
 * @author Maciej Fec
 * @version 02/03/2025
 */
public class OpenBrowser {

    private String pageName;

    private OpenBrowser(String pageName) {
        this.pageName = pageName;
    }

    public static OpenBrowser on(String pageName) {
        return new OpenBrowser(pageName);
    }

    public ChainBuilder usingEndpoint(String endpoint) {
        return exec(
                http(String.format("Open browser on '%s'", pageName))
                        .get(endpoint)
                        .check(status().is(200)),
                pause(ThinkTime.randomBetween(1, 2, SECONDS))
        );
    }
}
