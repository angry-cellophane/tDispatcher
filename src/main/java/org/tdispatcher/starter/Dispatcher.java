package org.tdispatcher.starter;

import org.tdispatcher.common.Callback;

import java.util.List;
import java.util.concurrent.Callable;

public interface Dispatcher {

    <T> void submit(List<? extends Callable<T>> tasks, Callback<T> callback);

    void shutdown();
}
