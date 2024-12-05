package api2;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ComputationCoordinatorEmpty implements ComputationCoordinator {

    private DataStore datastore; // for API 2
    private ComputeEngine engine; // for API 3
    private static final int numOfThreads = 4;

    public ComputationCoordinatorEmpty(DataStore datastore, ComputeEngine engine) {
        this.datastore = datastore;
        this.engine = engine;
    }

    @Override
    public ComputeResult compute(ComputeRequest request) {
        ExecutorService threadPool = Executors.newFixedThreadPool(numOfThreads);

        try {
            List<Integer> userInput = request.getInputConfig().getInputList();
            if (userInput == null || userInput.isEmpty()) {
                System.err.println("User input list is null or empty.");
                return ComputeResult.FAILURE;
            } else {
                System.out.println("Received input list: " + userInput);
            }

            // Partition input for batch processing
            int batchSize = (int) Math.ceil((double) userInput.size() / numOfThreads);
            List<List<Integer>> partitions = partitionList(userInput, batchSize);

            // Create tasks for each batch
            List<Callable<ComputeResult>> tasks = partitions.stream()
                .map(batch -> (Callable<ComputeResult>) () -> {
                    String result = engine.compute(batch);
                    if (result == null || result.isEmpty()) {
                        System.err.println("ComputeEngine returned null or empty output for batch.");
                        return ComputeResult.FAILURE;
                    }
                    System.out.println("Output for batch: " + result);
                    datastore.appendSingleResult(request.getOutputConfig(), result);
                    return ComputeResult.SUCCESS;
                })
                .collect(Collectors.toList());

            // Execute tasks
            List<Future<ComputeResult>> futures = threadPool.invokeAll(tasks);

            // Check results
            for (Future<ComputeResult> future : futures) {
                if (future.get() != ComputeResult.SUCCESS) {
                    return ComputeResult.FAILURE;
                }
            }

            return ComputeResult.SUCCESS;
        } catch (Exception e) {
            System.err.println("Error occurred during computation: " + e.getMessage());
            return ComputeResult.FAILURE;
        } finally {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException ex) {
                threadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    // Helper method to partition a list into smaller chunks
    private List<List<Integer>> partitionList(List<Integer> list, int batchSize) {
        List<List<Integer>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += batchSize) {
            partitions.add(list.subList(i, Math.min(i + batchSize, list.size())));
        }
        return partitions;
    }
}

