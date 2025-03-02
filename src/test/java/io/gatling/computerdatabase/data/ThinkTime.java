package io.gatling.computerdatabase.data;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.ThreadLocalRandom;

public class ThinkTime {

    public static Duration randomBetween(long lowerRange, long upperRange, TemporalUnit timeUnit) {
        if(lowerRange > upperRange) throw new IllegalArgumentException("Lower range cannot be higher than the upper range");
        long thinkTime = ThreadLocalRandom.current().nextLong(lowerRange, upperRange);

        return Duration.of(thinkTime, timeUnit);
    }
}
