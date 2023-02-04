package ru.otus.command;

import static java.util.Objects.nonNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Deque;
import lombok.SneakyThrows;
import ru.otus.exceptions.handling.ExceptionHandlingService;
import ru.otus.service.statistic.StatisticService;

public class TestExecution implements TestStep {

    private final Deque<TestStep> testSteps;
    private final ExceptionHandlingService handler;
    private final StatisticService statisticService;


    public TestExecution(Deque<TestStep> testSteps, ExceptionHandlingService handler,
            StatisticService statisticService) {
        this.testSteps = testSteps;
        this.handler = handler;
        this.statisticService = statisticService;
    }


    @Override
    @SneakyThrows
    public void execute() {
        var testStep = testSteps.poll();
        while (nonNull(testStep)) {
            try {
                testStep.execute();
            } catch (Exception e) {
                statisticService.endStatisticCase(e);
                handler.handleException(e);
            } finally {
                testStep = testSteps.poll();
            }

        }
        statisticService.endStatisticCase();
    }
}
