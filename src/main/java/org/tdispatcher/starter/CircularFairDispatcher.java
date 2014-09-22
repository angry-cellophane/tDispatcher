package org.tdispatcher.starter;

import org.tdispatcher.common.Callback;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CircularFairDispatcher implements Dispatcher {

    private final ThreadPoolExecutor pool;
    private volatile boolean isShutdown;

    public CircularFairDispatcher(int nThreads) {
        pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
        isShutdown = false;
    }

    @Override
    public <T> void submit(List<? extends Callable<T>> tasks, Callback<T> callback) {
        if (isShutdown) {
            throw new IllegalStateException("dispatcher is shutting down");
        }


    }

    @Override
    public void shutdown() {
        if (!pool.isShutdown()) {
            pool.shutdown();
        }
    }
}
