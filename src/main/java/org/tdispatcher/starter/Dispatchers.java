package org.tdispatcher.starter;

public class Dispatchers {

    private static final int N_THREADS = 4;


    public static Dispatcher newFairDispatcher() {
        return new FairDispatcher(N_THREADS);
    }

    public static Dispatcher newFairDispatcher(Options options) {
        return new FairDispatcher(options.nThreads);
    }

    private Dispatchers(){}
}
