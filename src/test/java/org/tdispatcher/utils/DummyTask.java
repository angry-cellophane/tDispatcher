package org.tdispatcher.utils;

import java.util.concurrent.Callable;

public class DummyTask implements Callable<Integer> {

    public final int i;

    public DummyTask(int i) {
        this.i = i;
    }

    @Override
    public Integer call() throws Exception {
        return i;
    }
}
