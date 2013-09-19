import java.applet.*;
import java.awt.*;

class SharedData {
    int data=0;

    // synchronized makes sure that threads can call this method one at a time
    synchronized void doWork() {
        try {
            // make the thread running doWork pause for a second
            Thread.sleep(1000);
        } catch(InterruptedException e) {}

        // update the shared data
        data=1;
        // notify the waiting thread that the data is ready
        notify();
    }

    synchronized int getResult() {
        try {
            // wait till other threads send the okay (notify)
            wait();
        } catch(InterruptedException e) {}

        return data;
    }
}

class CustomThread1 extends Thread {
    // reference to the shared data
    SharedData shared;

    public CustomThread1(SharedData shared, String string) {
        super(string);
        this.shared = shared;
        // tell the thread to run
        start();
    }

    @Override
    public void run() {
        // call the SharedData getResult method
        System.out.println("The result is " + shared.getResult());
    }
}

class CustomThread2 extends Thread {
    // reference to the shared data
    SharedData shared;

    public CustomThread2(SharedData shared, String string) {
        super(string);
        this.shared = shared;
        // tell the thread to run
        start();
    }

    @Override
    public void run() {
        // call the SharedData getResult method
        shared.doWork();
    }
}

/****************************************
 * Creates a shared data object
 * Creates one thread to write to the shared data
 * Creates one thread to read from the shared data
 * What's the point - the Shared data object
 * coordinates the threads so reading of the data
 * returns what is expected.
 *
 * @author Dan
 */
class wait {
    public static void main(String args[]) {
        SharedData shared = new SharedData();
        CustomThread1 thread1 = new CustomThread1(shared, "one");
        CustomThread2 thread2 = new CustomThread2(shared, "two");
    }
}