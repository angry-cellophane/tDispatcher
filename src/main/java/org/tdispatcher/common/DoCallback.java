package org.tdispatcher.common;

public class DoCallback<T> implements Runnable {


    private final Client<T> client;

    public DoCallback(Client<T> client) {
        this.client = client;
    }

    @Override
    public void run() {
        client.callback.apply(client.futures);
    }
}
