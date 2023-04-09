package ru.otus.dataprocessor;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;

public class ProcessorAggregator implements Processor {

        //группирует выходящий список по name, при этом суммирует поля value
    @Override
    public Map<String, Double> process(List<Measurement> data) {
        return data
                .stream()
                .collect(Collectors.toMap(Measurement::getName,
                        Measurement::getValue,
                        Double::sum,
                        LinkedHashMap::new));

    }

}
