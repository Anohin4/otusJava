package ru.otus.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.command.TestExecution;
import ru.otus.command.TestStep;
import ru.otus.exceptions.handling.ExceptionHandlingServiceImpl;
import ru.otus.service.statistic.StatisticService;
import ru.otus.service.validation.TestClassValidator;
import ru.otus.service.validation.TestClassValidatorImpl;
import ru.otus.utils.Multimap;

public class TestExecutor {

    private final List<Class<? extends Annotation>> annotationList = List.of(Test.class, Before.class, After.class);
    private final Multimap<Class<? extends Annotation>, Method> repoMap;
    private final TestClassValidator validator;
    private final StatisticService statisticService;
    private final TestQueueWriterImpl queueWriter;

    public TestExecutor(StatisticService statisticService) {
        this.repoMap = new Multimap<>();
        this.statisticService = statisticService;
        this.validator = new TestClassValidatorImpl(repoMap);
        this.queueWriter  = new TestQueueWriterImpl(repoMap);
    }

    public void runTestsForClass(Class testClazz)
            throws Exception {
        //создаем очередь, в которые пишем порядок выполнения тестов
        Deque<TestStep> testStepQueue = new LinkedList<>();

        var exceptionHandler = new ExceptionHandlingServiceImpl(testStepQueue);

        prepareRepoMap(testClazz);
        validator.validate();

        List<Method> testList = repoMap.getOrEmptyList(Test.class);
        for (Method testMethod : testList) {
            statisticService.startStatisticCase(testMethod.getName());
            queueWriter.fillTestQueue(testClazz, testStepQueue, testMethod);

            var testExecution = new TestExecution(testStepQueue,
                    exceptionHandler, statisticService);
            try {
                testExecution.execute();
            } catch (Exception e) {
                System.out.println("Ошибка во время выполнения теста");
                System.out.println(e.getMessage());
                break;
            }
        }
        statisticService.printStatisticAndCLear();
        repoMap.clear();
    }

    /**
     * Метод подготавливает мапу , сортируя методы по аннотациям
     */
    private void prepareRepoMap(Class testClazz) {
        List<Method> methods = Arrays.asList(testClazz.getMethods());
        methods.stream()
                .forEach(elem -> {
                    for (Class<? extends Annotation> annotationClass : annotationList) {
                        if (elem.isAnnotationPresent(annotationClass)) {
                            repoMap.put(annotationClass, elem);
                        }
                    }
                });
    }

}
