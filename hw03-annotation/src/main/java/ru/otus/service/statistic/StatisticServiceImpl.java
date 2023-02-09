package ru.otus.service.statistic;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticServiceImpl implements StatisticService {

    int okTest;
    int failTest;

    public StatisticServiceImpl() {
        this.okTest = 0;
        this.failTest = 0;
    }

    @Override
    public void addSuccessCase() {
        okTest++;
    }

    @Override
    public void addFailTest() {
        failTest++;
    }

    @Override
    public void printStatistic() {
        int summ = okTest + failTest;
        System.out.println("Статистика выполнения");
        System.out.println("Всего тестов " + summ);
        System.out.println("Успешно выполнено тестов " + okTest);
        System.out.println("Получено ошибок " + failTest);
    }
}
