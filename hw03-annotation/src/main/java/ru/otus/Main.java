package ru.otus;

import ru.otus.service.TestExecutor;
import ru.otus.service.statistic.StatisticServiceImpl;
import ru.otus.service.validation.TestClassValidatorImpl;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
        TestExecutor testExecutor = new TestExecutor(new StatisticServiceImpl());
        testExecutor.runTestsForClass(TestClass.class);
    }
}