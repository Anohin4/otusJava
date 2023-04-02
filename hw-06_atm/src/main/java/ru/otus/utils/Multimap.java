package ru.otus.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Multimap<K, V> {

    private Map<K, List<V>> repoMap;

    public Multimap() {
        this.repoMap = new HashMap<>();
    }

    public void put(K key, V value) {
        if (repoMap.containsKey(key)) {
            repoMap.get(key).add(value);
        } else {
            List<V> valueList = new ArrayList<>();
            valueList.add(value);
            repoMap.put(key, valueList);
        }
    }

    public List<V> get(K key) {
        return List.copyOf(repoMap.get(key));
    }


    public List<V> getOrEmptyList(K key) {
        if (repoMap.containsKey(key)) {
            return List.copyOf(repoMap.get(key));
        }
        return Collections.emptyList();
    }

    public void clear() {
        repoMap.clear();
    }

    public Set<Entry<K, List<V>>> getEntrySet() {
        return repoMap.entrySet();
    }
}
