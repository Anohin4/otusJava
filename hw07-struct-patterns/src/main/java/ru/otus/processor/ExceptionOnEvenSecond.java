package ru.otus.processor;

import java.time.LocalDateTime;
import ru.otus.model.Message;

public class ExceptionOnEvenSecond implements Processor {

    @Override
    public Message process(Message message) {
        LocalDateTime now = LocalDateTime.now();
        if (now.getSecond() % 2 == 0) {
            throw new RuntimeException("There is an exception for some reason");
        }
        return message;
    }
}
