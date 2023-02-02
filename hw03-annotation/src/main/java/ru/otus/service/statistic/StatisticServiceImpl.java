package ru.otus.service.statistic;

import static java.util.Objects.nonNull;
import static ru.otus.model.TestCaseResultEnum.OK;
import static ru.otus.model.TestCaseResultEnum.UNKNOWN;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.otus.model.StatisticCase;
import ru.otus.model.TestCaseResultEnum;
import ru.otus.utils.Multimap;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticServiceImpl implements StatisticService {

    StatisticCase statisticCase;
    Multimap<TestCaseResultEnum, StatisticCase> resultMap;
    final StatisticWriter writer;

    public StatisticServiceImpl() {
        this.resultMap = new Multimap<>();
        this.writer = new ConsoleStatisticWriter(resultMap);
    }

    @Override
    public void startStatisticCase(String testName) {
        if (nonNull(statisticCase) && statisticCase.isActive()) {
            //есть активный, когда начинаем новый - что-то пошло не так и прошлый тест не закончен
            resultMap.put(UNKNOWN, statisticCase);
        }
        this.statisticCase = StatisticCase.builder().method(testName)
                .isActive(true)
                .build();
    }

    @Override
    public void endStatisticCase() {
        this.statisticCase.setActive(false);
        resultMap.put(OK, statisticCase);
    }

    @Override
    public void endStatisticCase(Exception e) {
        this.statisticCase.setActive(false);
        resultMap.put(TestCaseResultEnum.getByException(e), statisticCase);

    }

    @Override
    public void printStatistic() {
        writer.writeStatistic();
    }
}
