import java.time.LocalTime;
import java.util.concurrent.*;

public class CallableFuture {
    public static void sum1to100(){
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<Integer> future = executor.submit(() -> {
            int sum = 0;
            for(int i=1;i<=100;i++){
                sum += i;
            }
            return sum;
        });

        try {
            System.out.println("Calculating sum from 1 to 100... : "+future.get());
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            executor.shutdown();    
        }
    }

    public static void tenTasksthreeThreads(){ // only 3 threads are created and threads are reused.
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for(int i=0;i<10;i++){
            executor.submit(()->{
                System.out.println("Task executed by : "+Thread.currentThread().getName());
                try{
                    Thread.sleep(2000);
                }
                catch(Exception e){
                    System.out.println(e);
                }
            });
        }

        executor.shutdown();
    }

    public static void usingCachedThreadPool(){ // 10 threads are created
        ExecutorService executor = Executors.newCachedThreadPool();

        for(int i=0;i<10;i++){
            executor.submit(()->{
                System.out.println("Task executed by : "+Thread.currentThread().getName());
                try{
                    Thread.sleep(2000);
                }
                catch(Exception e){
                    System.out.println(e);
                }
            });
        }

        executor.shutdown();
    }

    public static void customThreadPool(){
        BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, taskQueue, (r, e)->{
             System.out.println(LocalTime.now() + " - Task " + r.toString() + " was rejected");
        });

        for(int i=1;i<=6;i++){
            int taskId = i;
            executor.submit(()->{
                System.out.println(LocalTime.now() + " - Task " + taskId + " started by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(2000); // Simulate task running for 2 seconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(LocalTime.now() + " - Task " + taskId + " finished by " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }

    public static void main(String[] args){
        sum1to100();
        tenTasksthreeThreads();
        usingCachedThreadPool();
        customThreadPool();
    }   
}
