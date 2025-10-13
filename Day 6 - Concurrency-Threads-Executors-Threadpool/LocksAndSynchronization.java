
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class SharedObj{
    private int count = 0;

    private ReentrantLock lock = new ReentrantLock();

    // public synchronized void incrementAndGet(int value){

    //     this.count += value;
    // }

    public void incrementAndGet(){
        try {
            // lock.lock();
            this.count++;
        } finally {
            // lock.unlock();
        }
    }

    public int getCount(){
        return this.count;
    } 
}

class SharedList{
    List<Integer> list = new ArrayList<>();

    ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void addToList(int value){
        rwLock.writeLock().lock();
        try {
            list.add(value);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public List<Integer> getList(){
        try {
            rwLock.readLock().lock();
            return this.list;
        } finally {
            rwLock.readLock().unlock();
        }
    }
}

public class LocksAndSynchronization {
    public static void firstThreeQuestions(){
        SharedObj sharedClass = new SharedObj();
        Thread t1 = new Thread(()->{
            for(int i=0;i<1000;i++){
                sharedClass.incrementAndGet();
            }
        });
        Thread t2 = new Thread(()->{
            for(int i=0;i<1000;i++){
                sharedClass.incrementAndGet();
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
            System.out.println(sharedClass.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void readWriteLocks(){
        SharedList shareList = new SharedList();
        Thread writerThread = new Thread(()->{
            for(int i=0;i<1000;i++){
                shareList.addToList(i);
            }
        });
        Thread readerThread1 = new Thread(()->{
            for(int i=0;i<1000;i++){
                List<Integer> list = shareList.getList();
                System.out.println("Reader 1: "+list.size());
            }
        });
        Thread readerThread2 = new Thread(()->{
            for(int i=0;i<1000;i++){
                List<Integer> list = shareList.getList();
                System.out.println("Reader 2: "+list.size());
            }
        });
        writerThread.start();
        readerThread1.start();
        readerThread2.start();

        try{
            writerThread.join();
            readerThread1.join();
            readerThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final List Size: "+shareList.getList().size());
    }

    public static void main(String[] args) {
        firstThreeQuestions();
        readWriteLocks();
    }   
}
