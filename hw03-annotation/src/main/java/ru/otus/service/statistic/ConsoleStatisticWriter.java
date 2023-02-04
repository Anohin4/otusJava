package ru.otus.service.statistic;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.otus.model.StatisticCase;
import ru.otus.model.TestResultEnum;
import ru.otus.utils.Multimap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConsoleStatisticWriter implements StatisticWriter {

    Multimap<TestResultEnum, StatisticCase> resultMap;

    public ConsoleStatisticWriter(Multimap<TestResultEnum, StatisticCase> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public void writeStatistic() {
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Статистика");

        Set<Entry<TestResultEnum, List<StatisticCase>>> entrySet = resultMap.getEntrySet();
        for (var entry : entrySet) {
            List<String> methodsNames = entry.getValue().stream()
                    .map(StatisticCase::getMethod)
                    .toList();

            System.out.print(entry.getKey().description + " ");
            System.out.println(entry.getValue().size() + " шт.");
            System.out.print("К ним относятся: ");
            System.out.println(String.join(",", methodsNames));
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
