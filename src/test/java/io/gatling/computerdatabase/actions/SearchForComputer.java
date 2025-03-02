package io.gatling.computerdatabase.actions;

import io.gatling.computerdatabase.data.ThinkTime;
import io.gatling.javaapi.core.ChainBuilder;

import java.time.temporal.ChronoUnit;

import static io.gatling.computerdatabase.data.SessionKeys.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

/**
 * Mimics the action of searching for a computer by sending a GET request to the /computers endpoint along with
 * the filter query parameter. Random think time is applied once the request is completed to better simulate real
 * user behaviour and pauses between actions.
 *
 * @author Macie Fec
 * @version 02/03/2025
 */
public class SearchForComputer {

    public static ChainBuilder withNameContaining(String text) {
        return exec(
                http(String.format("Search for '%s'", text))
                        .get(String.format("/computers?f=%s", text))
                        .check(
                                status().is(200),
                                bodyString().saveAs(HTML_RESPONSE.key())
                        ),
                pause(ThinkTime.randomBetween(1, 2, ChronoUnit.SECONDS))
        );
    }
}
