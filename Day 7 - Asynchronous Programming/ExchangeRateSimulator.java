import java.util.*;
import java.util.concurrent.*;

public class ExchangeRateSimulator {

    private final Map<String, Integer> sourceDelays = Map.of(
            "source1", 500,
            "source2", 800,
            "source3", 300,
            "source4", 700,
            "source5", 600
    );

    public Map.Entry<String, Double> simulateApiCall(String source){
        try {
            int delay = sourceDelays.getOrDefault(source, 500);
            Thread.sleep(delay);
            double rate = 83.0 + (Math.random() * 0.5);  // Dummy exchange rate
            System.out.println(source + " responded with rate: " + rate);
            return Map.entry(source, rate);
        } catch (InterruptedException e) {
            throw new CompletionException(e);
        }
    }
}
