package ru.otus.cache;


import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private final List<HwListener<K, V>> listeners;
    private final Map<K, V> cacheStorage;

    public MyCache() {
        this.listeners = new ArrayList<>();
        this.cacheStorage = new WeakHashMap<>();
    }

    @Override
    public void put(K key, V value) {
        cacheStorage.put(key, value);
        notifyListeners(key,value, CacheActionsEnum.PUT.getValue());

    }

    @Override
    public void remove(K key) {
        V removedValue = cacheStorage.remove(key);
        if (nonNull(removedValue)) {
            notifyListeners(key, removedValue, CacheActionsEnum.REMOVE.getValue());
        }
    }

    @Override
    public V get(K key) {
        return cacheStorage.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(K key, V value, String action) {
        listeners.forEach(it -> it.notify(key, value, action));
    }
}
