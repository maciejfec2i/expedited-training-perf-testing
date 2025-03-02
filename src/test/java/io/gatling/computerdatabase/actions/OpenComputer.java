package io.gatling.computerdatabase.actions;

import io.gatling.computerdatabase.data.ThinkTime;
import io.gatling.javaapi.core.ChainBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.temporal.ChronoUnit;

import static io.gatling.computerdatabase.data.SessionKeys.*;
import static io.gatling.javaapi.core.CoreDsl.*;

/**
 * Mimics the action of opening a page of a specific computer.
 *
 * @author Maciej Fec
 * @version 02/03/2025
 */
public class OpenComputer {

    public static ChainBuilder withName(String computerName) {
        return exec(
                session -> {
                    String computerNameResolved = session.getString(computerName);
                    String htmlResponse = session.getString(HTML_RESPONSE.key());
                    if(htmlResponse == null) throw new RuntimeException("No HTML response in current session");

                    Document html = Jsoup.parse(htmlResponse);;
                    Element element = html.select("a[href*='/computers']")
                            .stream()
                            .filter(link -> link.text().equalsIgnoreCase(computerNameResolved))
                            .findFirst()
                            .orElse(null);

                    if(element == null) throw new RuntimeException(String.format("Could not find computer called '%s'", computerNameResolved));

                    String computerUrl = element.attr("href");
                    return session.set(COMPUTER_URL.key(), computerUrl);
                }
        ).exec(
                OpenBrowser.on(String.format("#{%s} Page", computerName)).usingEndpoint("#{"+ COMPUTER_URL.key() +"}"),
                pause(ThinkTime.randomBetween(1, 2, ChronoUnit.SECONDS))
        );
    }
}
