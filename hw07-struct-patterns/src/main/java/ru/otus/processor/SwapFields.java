package ru.otus.processor;

import ru.otus.model.Message;
import ru.otus.model.Message.Builder;

public class SwapFields implements Processor {

    @Override
    public Message process(Message message) {
        String field11Temp = message.getField11();
        Builder builder = message.toBuilder();
        builder.field11(message.getField12());
        builder.field12(field11Temp);

        return builder.build();
    }
}
