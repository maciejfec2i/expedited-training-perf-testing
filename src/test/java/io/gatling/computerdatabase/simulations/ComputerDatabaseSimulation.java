package computerdatabase;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.computerdatabase.actions.OpenBrowser;
import io.gatling.computerdatabase.actions.OpenComputer;
import io.gatling.computerdatabase.actions.SearchForComputer;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

/**
 * This sample is based on our official tutorials:
 * <ul>
 *   <li><a href="https://docs.gatling.io/tutorials/recorder/">Gatling quickstart tutorial</a>
 *   <li><a href="https://docs.gatling.io/tutorials/advanced/">Gatling advanced tutorial</a>
 * </ul>
 */
public class ComputerDatabaseSimulation extends Simulation {

    FeederBuilder<String> feeder = csv("search.csv").random();

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("https://computer-database.gatling.io")
                    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .acceptLanguageHeader("en-US,en;q=0.5")
                    .acceptEncodingHeader("gzip, deflate")
                    .userAgentHeader(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0"
                    );

    ChainBuilder openComputersPage = exec(OpenBrowser.on("Computers Page").usingEndpoint("/computers"));
    ChainBuilder searchForComputer = exec(SearchForComputer.withNameContaining("#{searchCriterion}"));
    ChainBuilder openComputer = exec(OpenComputer.withName("searchComputerName"));

    ScenarioBuilder customScenario = scenario("Custom Scenario").exec(
            feed(feeder),
            openComputersPage,
            searchForComputer,
            openComputer
    );

    {
        setUp(
                customScenario.injectOpen(rampUsers(15).during(10))
        ).protocols(httpProtocol);
    }
}
