import java.util.ArrayList;
import java.util.List;

public class ThreadSafeCollections {
    public static void synchronizedListDemo(){
        List<Integer> list = new ArrayList<>();

        // Add initial elements
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }

        // Thread 1: Iterates over the list
        Thread reader = new Thread(() -> {
            try {
                for (Integer value : list) {
                    System.out.println("Reading: " + value);
                    Thread.sleep(100); // Slow down to allow modification during iteration
                }
            } catch (Exception e) {
                System.out.println("Reader Thread: Exception caught -> " + e);
            }
        });

        // Thread 2: Modifies the list after a short delay
        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(250); // Ensure reader starts first
                System.out.println("Writer Thread: Modifying list...");
                list.add(99); // Modify the list during iteration
                System.out.println("Writer Thread: Modification done.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        reader.start();
        writer.start();

        try {
            reader.join();
            writer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main Thread: Program finished.");
    }
    
    public static void main(String[] args) {
        synchronizedListDemo();
    }
}
