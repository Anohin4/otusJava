package ru.otus;

import ru.otus.examples.SimpleTestClass;
import ru.otus.examples.TestClassWithExceptionInAfter;
import ru.otus.examples.TestClassWithExceptionInBefore;
import ru.otus.examples.TestClassWithExceptionInTest;
import ru.otus.examples.TestClassWithFailedAssertion;
import ru.otus.examples.TestClassWithState;
import ru.otus.service.TestExecutorImpl;
import ru.otus.service.statistic.StatisticServiceImpl;

public class Main {

    public static void main(String[] args) throws Exception {

        var testExecutor = new TestExecutorImpl();
        testExecutor.runTestsForClass(SimpleTestClass.class);
        testExecutor.runTestsForClass(TestClassWithState.class);
        testExecutor.runTestsForClass(TestClassWithFailedAssertion.class);
        testExecutor.runTestsForClass(TestClassWithExceptionInBefore.class);
        testExecutor.runTestsForClass(TestClassWithExceptionInTest.class);
        testExecutor.runTestsForClass(TestClassWithExceptionInAfter.class);

    }
}