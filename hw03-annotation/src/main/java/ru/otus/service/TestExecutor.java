package ru.otus.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.command.LogStep;
import ru.otus.command.SetUpStep;
import ru.otus.command.SingleTestStep;
import ru.otus.command.TearDownTestStep;
import ru.otus.command.TestExecution;
import ru.otus.command.TestStep;
import ru.otus.exceptions.handling.ExceptionHandlingServiceImpl;
import ru.otus.service.statistic.StatisticService;
import ru.otus.service.validation.TestClassValidator;
import ru.otus.service.validation.TestClassValidatorImpl;
import ru.otus.utils.Multimap;

public class TestExecutor {

    private final List<Class<? extends Annotation>> annotationList = List.of(Test.class, Before.class, After.class);
    private final Multimap<Class, Method> repoMap;
    private final TestClassValidator validator;
    private final StatisticService statisticService;

    public TestExecutor(StatisticService statisticService) {
        this.repoMap = new Multimap<>();
        this.statisticService = statisticService;
        this.validator = new TestClassValidatorImpl(repoMap);
    }

    public void runTestsForClass(Class testClazz)
            throws Exception {
        Deque<TestStep> testStepQueue = new LinkedList<>();
        var exceptionHandler = new ExceptionHandlingServiceImpl(testStepQueue);

        prepareRepoMap(testClazz);
        validator.validate();

        List<Method> testsItselfList = repoMap.getOrEmptyList(Test.class);

        for (Method testItself : testsItselfList) {
            statisticService.startStatisticCase(testItself.getName());
            fillTestQueue(testClazz, testStepQueue, testItself);

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
        statisticService.printStatistic();
    }

    /**
     * Метод заполняет тестовую очередь для одного теста
     */
    private void fillTestQueue(Class testClazz, Deque<TestStep> testStepQueue, Method testItself)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var testObject = testClazz.getConstructor().newInstance();

        testStepQueue.add(new LogStep(testItself.getName()));
        testStepQueue.add(new SetUpStep(repoMap.getOrEmptyList(Before.class), testObject));
        testStepQueue.add(new SingleTestStep(testItself, testObject));
        testStepQueue.add(new TearDownTestStep(repoMap.getOrEmptyList(After.class), testObject));
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
