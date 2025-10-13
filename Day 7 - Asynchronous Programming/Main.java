import java.util.*;
import java.util.concurrent.*;

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args){
        try {
            ExchangeRateSimulator api = new ExchangeRateSimulator();
            ExecutorService executor = Executors.newFixedThreadPool(5);

            List<String> sources = List.of("source1", "source2", "source3", "source4", "source5");
            CompletableFuture<Map.Entry<String, Double>>[] futures = new CompletableFuture[5];


            // Launch async calls
            for (int i = 0; i < sources.size(); i++) {
                String source = sources.get(i);
                futures[i] = CompletableFuture.supplyAsync(() -> api.simulateApiCall(source), executor);
            }

            CompletableFuture<Map.Entry<String, Double>> fastestApiFuture =
                    CompletableFuture.anyOf(futures)
                            .thenApply(result -> (Map.Entry<String, Double>) result);

            Map.Entry<String, Double> fastestApi = fastestApiFuture.get(); // block to get result
            System.out.println("\n✅ Fastest API is: " + fastestApi.getKey() + " = " + fastestApi.getValue());

            CompletableFuture<Map<String, Double>> aggregateFuture = CompletableFuture
                    .allOf(futures)
                    .thenApply(v -> {
                        Map<String, Double> result = new HashMap<>();
                        for (CompletableFuture<Map.Entry<String, Double>> future : futures) {
                            Map.Entry<String, Double> entry = future.join(); // safe since allOf is complete
                            result.put(entry.getKey(), entry.getValue());
                        }
                        return result;
                    });

            Map<String, Double> aggregateOutput = aggregateFuture.get(); // block to get result
            System.out.println("\n📊 Aggregate exchange rates:");
            aggregateOutput.forEach((key, value) -> System.out.println("🔹 " + key + " = " + value));

            executor.shutdown();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
