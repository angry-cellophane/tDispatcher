package org.tdispatcher.common;

import java.util.List;
import java.util.concurrent.Future;

@FunctionalInterface
public interface Callback<V>{

    void apply(List<? extends Future<V>> value);
}
