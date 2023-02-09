package ru.otus.command;

import java.lang.reflect.Method;
import java.util.List;
import lombok.SneakyThrows;
import ru.otus.exceptions.TearDownException;
import ru.otus.service.statistic.StatisticService;

public class TearDownTestStep implements TestStep {

    private final List<Method> steps;
    private final Object testObject;
    private final StatisticService service;

    public TearDownTestStep(List<Method> steps, Object testObject, StatisticService service) {
        this.steps = steps;
        this.testObject = testObject;
        this.service = service;
    }

    @Override
    @SneakyThrows
    public void execute() {
        try {
            for (var testStep : steps) {
                testStep.invoke(testObject);
            }
        } catch (Exception e) {
            System.out.println("Ошибка во время выполнения шага TearDown. Останавливаем выполнение тестов");
            service.addFailTest();
            service.printStatistic();
            throw new TearDownException(e.getCause().getMessage());
        }
    }
}
