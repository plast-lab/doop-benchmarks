import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        Thread thisThread = Thread.currentThread();
        ClassLoader cl = thisThread.getContextClassLoader();
        printThread(thisThread, "current");

        Thread t = new Thread() {
                public void run(){
                }
            };
        printThread(t, "t");
        ThreadGroup tg = t.getThreadGroup();
        System.out.println("Thread group: " + tg);
        t.start();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
                System.out.println("Lambda called.");
            });
        executorService.submit(new Runnable() {
                public void run() {
                    System.out.println("Runnable called.");
                }
            });
        executorService.shutdown();
    }

    static void printThread(Thread t, String desc) {
        System.out.println("Thread <" + desc + ">" +
                           ": id=" + t.getId() +
                           ", name=" + t.getName() +
                           ", priority=" + t.getPriority());
    }
}
