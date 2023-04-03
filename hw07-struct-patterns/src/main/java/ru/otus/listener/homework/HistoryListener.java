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
        Builder builder = msg.toBuilder();
        List<String> deepCopy = new ArrayList<>(msg.getField13().getData());
        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(deepCopy);
        builder.field13(objectForMessage);

        storageMap.put(msg.getId(), builder.build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(storageMap.get(id));
    }
}
