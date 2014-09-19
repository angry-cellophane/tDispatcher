package org.tdispatcher.starter;

public class Options {

    public static final class Builder {

        private int threads;

        public Builder threads(int threadNumber) {
            this.threads = threadNumber;
            return this;
        }
    }


    public int nThreads;

    private Options(int nTheads) {
        this.nThreads = nTheads;
    }
}
