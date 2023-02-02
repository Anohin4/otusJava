package ru.otus.exceptions.handling;

import ru.otus.service.statistic.StatisticService;

public class TearDownExceptionHandler implements ExceptionHandler {

    private final StatisticService statisticService;

    public TearDownExceptionHandler(StatisticService statisticService) {
        this.statisticService = statisticService;
    }


    @Override
    public void handleException(Exception exception) throws Exception {
        System.out.println("Ошибка во время выполнения блока after, останавливаем выполнение тестов");
        throw new RuntimeException("Выполнение тестов остановлено");
    }
}
