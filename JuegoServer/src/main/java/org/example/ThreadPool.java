package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.example.Logger.*;

public class ThreadPool {
    private final Worker[] workers;
    private final BlockingQueue<Runnable> queue;

    public ThreadPool(int size) {
        assert size > 0;

        this.queue = new LinkedBlockingQueue<>();
        this.workers = new Worker[size];
        for (int id = 0; id < size; id++) {
            workers[id] = new Worker(id, queue);
        }
    }

    public void execute(Runnable task) {
        queue.add(task);
    }

    public int getPoolSize() {
        return workers.length;
    }

    public void shutdown() {
        awaitTermination();
        for (Worker worker : workers) {
            worker.thread.interrupt();
        }
    }

    private void awaitTermination() {
        while (!queue.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                error("Error while waiting for the queue to be empty");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void join() {
        while (!queue.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                error("Error while waiting for the queue to be empty");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private static class Worker implements Runnable {
        private final int id;
        private final Thread thread;
        private final BlockingQueue<Runnable> queue;

        public Worker(int id, BlockingQueue<Runnable> queue) {
            this.id = id;
            this.queue = queue;
            this.thread = new Thread(this);
            this.thread.start();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = queue.take();
                    info("Worker " + (id + 1) + " got a job, executing.");
                    task.run();
                } catch (InterruptedException e) {
                    warning("Worker " + (id + 1) + " interrupted, shutting down.");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
