package org.tdispatcher.matrix;

import org.tdispatcher.common.Client;
import org.tdispatcher.common.DoCallback;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class FairTaskOrderer implements Runnable {

    private final List<Client<?>> clients;
    private final ExecutorService pool;

    private volatile boolean isShutdown;

    private int[] processedClientTasks;

    public FairTaskOrderer(List<Client<?>> clients,
                           ExecutorService pool) {
        this.clients = clients;
        this.pool = pool;

        processedClientTasks = new int[0];
        isShutdown = false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        int i = 0;

        for (;;) {
            if (isShutdown && processedClientTasks.length == 0) break;

            int clientsSize = clients.size();
            if (clientsSize != processedClientTasks.length) {
                processedClientTasks = Arrays.copyOf(processedClientTasks, clientsSize);
            }

            if (processedClientTasks.length == 0) continue;

            if (++i == clientsSize) i = 0;

            int j = processedClientTasks[i] ++;
            Client<?> client = clients.get(i);

            if (j == client.tasks.size()) {
                clients.remove(i);

                if (i == 0) {
                    processedClientTasks = new int[0];
                } else {
                    System.arraycopy(processedClientTasks, i + 1, processedClientTasks, i, processedClientTasks.length - i - 1);
                }
                pool.execute(new DoCallback<>(client));
                continue;
            }
            Callable<?> task = client.tasks.get(j);
            Future future = pool.submit(task);
            client.futures.add(future);
        }
    }

    public void shutdown() {
        isShutdown = true;
    }
}
