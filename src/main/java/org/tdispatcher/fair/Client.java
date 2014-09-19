package org.tdispatcher.fair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class Client<V> {

    public final List<? extends Callable<V>> tasks;
    public final List<Future<V>> futures;
    public final Callback<V> callback;

    public Client(List<? extends Callable<V>> tasks, Callback<V> callback) {
        this.tasks = tasks;
        this.callback = callback;
        this.futures = new ArrayList<>(tasks.size());
    }
}
