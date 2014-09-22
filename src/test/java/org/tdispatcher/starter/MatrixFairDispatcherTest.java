package org.tdispatcher.starter;

import org.tdispatcher.common.Callback;
import org.tdispatcher.utils.DummyTask;
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

public class MatrixFairDispatcherTest {

    /**
     * Simple test to check there are no exceptions
     */
    @Test
    public void simpleTest() throws InterruptedException {
        final Dispatcher dispatcher = Dispatchers.newMatrixDispatcher();
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