
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ThreadSafeCollections {

    public static void executorServiceVsParallelStream() {
        int tasks = 100;

        ExecutorService executor = Executors.newFixedThreadPool(10); // It runs threads as per the pool size
        CountDownLatch latch = new CountDownLatch(tasks);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < tasks; i++) {
            executor.submit(() -> {
                try {
                    Thread.sleep(50); // Simulate work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }

        try {
            latch.await();
            executor.shutdown();
            long endTime = System.currentTimeMillis();
            System.out.println("ExecutorService Time Taken: " + (endTime - startTime) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        startTime = System.currentTimeMillis();

        IntStream.range(0, tasks).parallel().forEach(task -> { // Parallel stream runs number of threads according to available cores in the system
            try {
                Thread.sleep(50); // Simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long endTime = System.currentTimeMillis();
        System.out.println("Parallel Stream Time Taken: " + (endTime - startTime) + " ms");
    }

    public static void counterIncrement() {
        AtomicInteger counter = new AtomicInteger(0);

        CountDownLatch latch = new CountDownLatch(1000);

        System.out.println("Initial Counter Value : " + counter.get());

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                counter.incrementAndGet();
                latch.countDown();
            }).start();
        }

        try {
            latch.await();
            System.out.println("Final Counter Value: " + counter.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void intStreamDemo() {
        IntStream.range(1, 101).parallel().forEach(i -> {
            System.out.println("Value : " + i + " | Thread : " + Thread.currentThread().getName());
        }); // This will use ForkJoinPool.commonPool(). It creates 100 threads and runs the tasks in parallel.
    }

    public static void synchronizedListDemo() {
        // List<Integer> list = new ArrayList<>(); Normal List (not thread-safe)
        // List<Integer> list = Collections.synchronizedList(new ArrayList<>()); Synchronized List - Locks the entire list regardless of read or write operation

        List<Integer> list = new CopyOnWriteArrayList<>(); // Faster for read-heavy operations

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
                System.out.println("Writer Thread: Exception caught -> " + e);
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
        // synchronizedListDemo();
        // counterIncrement();
        // intStreamDemo();
        executorServiceVsParallelStream();
    }
}
