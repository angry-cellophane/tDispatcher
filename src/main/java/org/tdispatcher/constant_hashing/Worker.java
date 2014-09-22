package org.tdispatcher.constant_hashing;

import org.tdispatcher.common.Client;

import java.util.List;

public class Worker implements Runnable {

    private final List<Client<?>> clients;

    public Worker(List<Client<?>> clients) {
        this.clients = clients;
    }

    @Override
    public void run() {

    }
}
