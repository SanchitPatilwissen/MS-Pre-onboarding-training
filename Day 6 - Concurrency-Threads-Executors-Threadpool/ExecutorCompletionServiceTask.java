import java.util.concurrent.*;

public class ExecutorCompletionServiceTask {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletionService<String> completionService = new ExecutorCompletionService<>(executor);

        for(int i=1;i<=5;i++){
            int taskId = i;
            completionService.submit(()->{
                System.out.println("Executing task " + taskId);
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                return "Task " + taskId;
            });
        }

        // executor.shutdownNow(); // Initiates an orderly shutdown

        for(int i=1;i<=5;i++){
            try{
                Future<String> future = completionService.take();
                System.out.println("Completed: " + future.get());
            }
            catch(Exception e){
                System.out.println(e);
            }
        }

        executor.shutdown();

        try{
            completionService.submit(()->{
                return "This task will not be accepted";
            });
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
