package ru.otus;

import ru.otus.examples.TestClassWithExceptionInAfter;
import ru.otus.service.TestExecutorImpl;
import ru.otus.service.statistic.StatisticServiceImpl;

public class Main {

    public static void main(String[] args) throws Exception {

        TestExecutorImpl testExecutor = new TestExecutorImpl(new StatisticServiceImpl());
//        testExecutor.runTestsForClass(SimpleTestClass.class);
//        testExecutor.runTestsForClass(TestClassWithState.class);
//        testExecutor.runTestsForClass(TestClassWithState.class);
//        testExecutor.runTestsForClass(TestClassWithFailedAssertion.class);
//        testExecutor.runTestsForClass(TestClassWithExceptionInBefore.class);
//        testExecutor.runTestsForClass(TestClassWithExceptionInTest.class);
        testExecutor.runTestsForClass(TestClassWithExceptionInAfter.class);

    }
}