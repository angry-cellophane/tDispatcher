package org.tdispatcher.starter;

public class Dispatchers {

    private static final int N_THREADS = 4;


    public static Dispatcher newMatrixDispatcher() {
        return new MatrixFairDispatcher(N_THREADS);
    }

    public static Dispatcher newMatrixDispatcher(Options options) {
        return new MatrixFairDispatcher(options.nThreads);
    }

    public static Dispatcher newContantHashingDispatcher() {
        return new CircularFairDispatcher(N_THREADS);
    }

    public static Dispatcher newContantHashingDispatcher(Options options) {
        return new CircularFairDispatcher(options.nThreads);
    }

    private Dispatchers(){}
}
