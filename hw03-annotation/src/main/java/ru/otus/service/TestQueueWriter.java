package ru.otus.service;

import java.lang.reflect.Method;
import java.util.Deque;
import ru.otus.command.TestStep;

public interface TestQueueWriter {

    void fillTestQueue(Class testClazz, Deque<TestStep> testStepQueue, Method testMethod)
            throws Exception;
}
