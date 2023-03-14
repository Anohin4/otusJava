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
import ru.otus.command.LogStep;
import ru.otus.command.SetUpStep;
import ru.otus.command.SingleTestStep;
import ru.otus.command.TearDownTestStep;
import ru.otus.command.TestStep;
import ru.otus.service.statistic.StatisticService;
import ru.otus.service.statistic.StatisticServiceImpl;
import ru.otus.utils.Multimap;

public class TestExecutorImpl implements TestExecutor {

    private final List<Class<? extends Annotation>> annotationList = List.of(Test.class, Before.class, After.class);
    private final Multimap<Class<? extends Annotation>, Method> repoMap;
    private StatisticService statisticService;

    public TestExecutorImpl() {
        this.repoMap = new Multimap<>();
    }

    public void runTestsForClass(Class<?> testClazz)
            throws Exception {
        statisticService = new StatisticServiceImpl();
        //создаем очередь, в которые пишем порядок выполнения тестов
        Deque<TestStep> testStepQueue = new LinkedList<>();

        prepareRepoMap(testClazz);

        List<Method> testList = repoMap.getOrEmptyList(Test.class);
        for (Method testMethod : testList) {
            var testObject = testClazz.getConstructor().newInstance();
            fillTestQueue(testStepQueue, testMethod, testObject);

            boolean withoutExceptions = executeTest(testStepQueue, testMethod, testObject);
            if(withoutExceptions) {
                statisticService.addSuccessCase();
            }
        }
        statisticService.printStatistic();
        repoMap.clear();
    }


    /**
     * Метод подготавливает мапу , сортируя методы по аннотациям
     */
    private void prepareRepoMap(Class<?> testClazz) {
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

    /**
     * Заполняем очередь комманд самим тестом
     */
    private void fillTestQueue(Deque<TestStep> testStepQueue, Method testMethod, Object testObject) {
        testStepQueue.add(new LogStep(testMethod.getName()));
        testStepQueue.add(new SetUpStep(repoMap.getOrEmptyList(Before.class), testObject));
        testStepQueue.add(new SingleTestStep(testMethod, testObject));
    }

    /**
     * Выполняем тест и обрабатываем возникающие ошибки Если тест прошел успешно - возвращается true
     */
    private boolean executeTest(Deque<TestStep> testStepQueue, Method testMethod, Object testObject) {
        boolean withoutExceptions = true;
        try {
            testStepQueue.forEach(TestStep::execute);
        } catch (Exception e) {
            statisticService.addFailTest();
            withoutExceptions = false;
            System.out.println("Ошибка во время выполнения теста " + testMethod.getName());
            System.out.println(e.getMessage());
            testStepQueue.clear();
        } finally {
            new TearDownTestStep(repoMap.getOrEmptyList(After.class),
                    testObject, statisticService).execute();
        }
        return withoutExceptions;
    }

}
