package ru.otus.service.statistic;

public interface StatisticService {

    void startStatisticCase(String testName);

    void endStatisticCase();

    void endStatisticCase(Exception e);

    void printStatisticAndCLear();
}
