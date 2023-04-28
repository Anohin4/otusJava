package ru.otus.cache;


import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCache<K, V> implements HwCache<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);

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
        V value = cacheStorage.get(key);
        if(nonNull(value)) {
            notifyListeners(key,value,CacheActionsEnum.GET.getValue());
        }
        return value;
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
        listeners.forEach(it -> {
            try{
                it.notify(key, value, action);
            } catch (Exception e) {
                logger.error("there is an exception in the listener {} and I don't know what to do", it.getClass().getSimpleName());
            }
        });
    }
}
