package org.tdispatcher.starter;

import org.tdispatcher.fair.Callback;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class FairDispatcherTest {

    private static class DummyTask implements Callable<Integer> {

        public final int i;

        private DummyTask(int i) {
            this.i = i;
        }

        @Override
        public Integer call() throws Exception {
            return i;
        }
    }

    /**
     * Simple test to check there are no exceptions
     */
    @Test
    public void simpleTest() throws InterruptedException {
        final Dispatcher dispatcher = Dispatchers.newFairDispatcher();
        try {
            AtomicBoolean isChanged = new AtomicBoolean(false);

            List<Callable<Integer>> tasks = IntStream.range(0, 1_000)
                    .mapToObj(DummyTask::new)
                    .collect(Collectors.toList());

            Callback<Integer> callback = futures -> {
                long count = futures.stream()
                        .map(f -> {
                                    try {
                                        return f.get();
                                    } catch (InterruptedException | ExecutionException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        )
                        .count();
                System.out.println("size = "+count);
                isChanged.set(true);
                assertEquals(tasks.size(), count);
            };

            dispatcher.submit(tasks, callback);
            TimeUnit.MILLISECONDS.sleep(100);
            assertTrue(isChanged.get());
        } finally {
            dispatcher.shutdown();
        }
    }
}