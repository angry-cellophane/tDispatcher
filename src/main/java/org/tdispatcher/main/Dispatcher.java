package org.tdispatcher.main;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface Dispatcher {

    <T> Future<T> submit(Collection<Callable<T>> tasks);
    void shutdown();

}
