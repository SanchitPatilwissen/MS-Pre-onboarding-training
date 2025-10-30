class FileDownload implements Runnable {

    public void run() {
        try {
            System.out.println("File " + Thread.currentThread().getName() + " is downloading....");

            for (int downloadPercent = 0; downloadPercent <= 100; downloadPercent += 10) {
                System.out.println("File " + Thread.currentThread().getName() + " is " + downloadPercent + "% downloaded");
                Thread.sleep(1000);
            }

            System.out.println("File " + Thread.currentThread().getName() + " download completed");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

public class Threads {

    public static void printCountAndThreadNames() {
        Thread thread = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    System.out.println("The count is : " + i + " and thread name is : " + Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        thread.start();
    }

    public static void fileDownload() {
        Thread task1 = new Thread(new FileDownload(), "file1");
        Thread task2 = new Thread(new FileDownload(), "file2");
        Thread task3 = new Thread(new FileDownload(), "file3");
        task1.start();
        task2.start();
        task3.start();
        try {
            task1.join();
            task2.join();
            task3.join();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("All files downloaded");
    }

    public static void main(String[] args) {
        fileDownload();
        printCountAndThreadNames();
    }
}
