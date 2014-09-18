package org.tdispatcher.main;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class FairDispatcher implements Dispatcher {

    @Override
    public <T> Future<T> submit(Collection<Callable<T>> tasks) {
        return null;
    }

}
