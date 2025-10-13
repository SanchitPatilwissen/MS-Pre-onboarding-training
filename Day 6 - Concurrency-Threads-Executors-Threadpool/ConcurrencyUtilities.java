import java.util.concurrent.*;

public class ConcurrencyUtilities {
    public static void countDownLatchDemo(){
        int numberOfTasks = 3;
        CountDownLatch latch = new CountDownLatch(numberOfTasks);

        for(int i=1;i<=numberOfTasks;i++){
            final int taskId = i;

            new Thread(() ->{
                System.out.println("Task " + taskId + " is working...");
                try {
                    Thread.sleep(1000 * taskId); // simulate variable time
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Task " + taskId + " completed.");
                latch.countDown(); // signal task completion
            }).start();
        }

        try {
            latch.await(); // wait for all tasks to complete
            System.out.println("All tasks completed. Proceeding with main thread.");
        } catch (Exception e) {
            e.printStackTrace();    
        }
    }

    public static void semaphoreDemo(){
        int maxConcurrentTasks = 2;
        Semaphore semaphore = new Semaphore(maxConcurrentTasks);

        for(int i=1;i<=5;i++){
            final int taskId = i;

            new Thread(() ->{
                try{
                    semaphore.acquire();
                    System.out.println("Task " + taskId + " is working...");
                    Thread.sleep(2000); // simulate work
                    System.out.println("Task " + taskId + " completed.");
                }
                catch(Exception e){
                    System.out.println(e);
                }
                finally{
                    semaphore.release();
                }
            }).start();
        }
    }
    
    public static void cyclicBarrierDemo(){
        int numberOfPlayers = 3;
        CyclicBarrier barrier = new CyclicBarrier(numberOfPlayers, () -> {
            System.out.println("All players are ready. Starting the game!");
        });

        for(int i=1;i<=numberOfPlayers;i++){
            final int playerId = i;

            new Thread(() ->{
                try {
                    System.out.println("Player " + playerId + " is getting ready...");
                    Thread.sleep(1000 * playerId); // simulate preparation time
                    System.out.println("Player " + playerId + " is ready.");
                    barrier.await(); // wait for all players to be ready
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static void main(String[] args){
        countDownLatchDemo();
        semaphoreDemo();
        cyclicBarrierDemo();
    }    
}
