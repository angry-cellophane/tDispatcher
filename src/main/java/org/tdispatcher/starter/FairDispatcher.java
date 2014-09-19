package org.tdispatcher.starter;

import org.tdispatcher.fair.Callback;
import org.tdispatcher.fair.Client;
import org.tdispatcher.fair.FairTaskOrderer;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class FairDispatcher implements Dispatcher {

    private final ThreadPoolExecutor pool;
    private final List<Client<?>> clients;
    private final FairTaskOrderer fairTaskOrderer;

    FairDispatcher(int nThreads) {
        pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
        clients = new CopyOnWriteArrayList<>();

        fairTaskOrderer = new FairTaskOrderer(clients, pool);
        new Thread(fairTaskOrderer).start();
    }


    @Override
    public <T> void submit(List<? extends Callable<T>> tasks,
                              Callback<T> callback) {
        clients.add(new Client<>(tasks, callback));
    }

    public void shutdown() {
        if (!pool.isShutdown()) pool.shutdown();
        fairTaskOrderer.shutdown();
    }
}
