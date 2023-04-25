package ru.otus.listener.homework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import java.util.Optional;
import ru.otus.model.Message.Builder;
import ru.otus.model.ObjectForMessage;

public class HistoryListener implements Listener, HistoryReader {
    private Map<Long, Message> storageMap;

    public HistoryListener() {
        this.storageMap = new HashMap<>();
    }

    @Override
    public void onUpdated(Message msg) {

        storageMap.put(msg.getId(), msg.copy());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(storageMap.get(id));
    }
}
