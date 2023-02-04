package ru.otus.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Deque;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.command.LogStep;
import ru.otus.command.SetUpStep;
import ru.otus.command.SingleTestStep;
import ru.otus.command.TearDownTestStep;
import ru.otus.command.TestStep;
import ru.otus.utils.Multimap;

public class TestQueueWriterImpl implements TestQueueWriter {

    private Multimap<Class<? extends Annotation>, Method> repoMap;

    public TestQueueWriterImpl(Multimap<Class<? extends Annotation>, Method> repoMap) {
        this.repoMap = repoMap;
    }

    /**
     * Метод заполняет тестовую очередь для одного теста
     */
    @Override
    public void fillTestQueue(Class testClazz, Deque<TestStep> testStepQueue, Method testMethod)
            throws Exception {
        var testObject = testClazz.getConstructor().newInstance();

        testStepQueue.add(new LogStep(testMethod.getName()));
        testStepQueue.add(new SetUpStep(repoMap.getOrEmptyList(Before.class), testObject));
        testStepQueue.add(new SingleTestStep(testMethod, testObject));
        testStepQueue.add(new TearDownTestStep(repoMap.getOrEmptyList(After.class), testObject));
    }
}