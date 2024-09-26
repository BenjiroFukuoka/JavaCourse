package release.threadpool;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class ThreadPool implements Executor {
    private ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private boolean isRunning = true;

    public ThreadPool(int countThread) {
        for (int i = 0; i < countThread; i++) {
            new Thread(new TaskRunner()).start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    private class TaskRunner implements Runnable {
        @Override
        public synchronized void run() {
            while (isRunning) {
                Runnable task = tasks.poll();
                if (task != null) {
                    task.run();
                }
            }
        }
    }

    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            tasks.offer(command);
        }
    }

    public void runOff() {
        isRunning = false;
    }
}