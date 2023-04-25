package ru.otus;

import java.util.List;
import java.util.Optional;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.ExceptionOnEvenSecond;
import ru.otus.processor.SwapFields;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяьться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */

        var processors = List.of(new ExceptionOnEvenSecond(),
                new SwapFields());

        var complexProcessor = new ComplexProcessor(processors, ex -> {
        });
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(List.of("String1", "String2"));
        var message = new Message.Builder(1L)
                .field1("field1")
                .field13(objectForMessage)
                .field12("field12")
                .field11("field11")
                .field10("field10")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        Optional<Message> messageById = historyListener.findMessageById(1L);
        if (messageById.isEmpty()) {
            throw new RuntimeException("Smth went wrong");
        }

        complexProcessor.removeListener(historyListener);
    }
}
