package io.gatling.computerdatabase.simulations.e2e.load;

import io.gatling.computerdatabase.actions.OpenBrowser;
import io.gatling.computerdatabase.actions.OpenComputer;
import io.gatling.computerdatabase.actions.SearchForComputer;
import io.gatling.computerdatabase.data.EnvironmentType;
import io.gatling.computerdatabase.httpprotocols.factories.HttpProtocolFactory;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.feed;

public class SearchForComputerAndViewItLoadTest extends Simulation {

    public static class LoadProfile {
        private static final int TOTAL_LOAD = 10;
        private static final int USER_RAMP_UP_PER_INTERVAL = 1;
        private static final Duration TOTAL_RAMP_UP_TIME = Duration.of(20, ChronoUnit.SECONDS);
        private static final Duration STEADY_STATE_DURATION = Duration.of(40, ChronoUnit.SECONDS);

        public static ClosedInjectionStep[] generate() {
            return new ClosedInjectionStep[]{
                    rampConcurrentUsers(USER_RAMP_UP_PER_INTERVAL).to(TOTAL_LOAD).during(TOTAL_RAMP_UP_TIME),
                    constantConcurrentUsers(TOTAL_LOAD).during(STEADY_STATE_DURATION)
            };
        }
    }

    FeederBuilder<String> feeder = csv("search.csv").random();
    HttpProtocolBuilder httpProtocol = HttpProtocolFactory.getHttpProtocol(EnvironmentType.PROD);
    ChainBuilder openComputersPage = exec(OpenBrowser.on("Computers Page").usingEndpoint("/computers"));
    ChainBuilder searchForComputer = exec(SearchForComputer.withNameContaining("#{searchCriterion}"));
    ChainBuilder openComputer = exec(OpenComputer.withName("searchComputerName"));

    ScenarioBuilder searchAndOpenScn = scenario("Load Test: Search for Computer and View It")
            .exec(feed(feeder), openComputersPage, searchForComputer, openComputer);

    {
        setUp(searchAndOpenScn.injectClosed(LoadProfile.generate())).protocols(httpProtocol);
    }
}
