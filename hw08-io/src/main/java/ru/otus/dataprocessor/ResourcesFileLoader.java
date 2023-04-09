package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private final String filename;
    private final Gson gson;

    public ResourcesFileLoader(String fileName) {
        this.filename = fileName;
        this.gson = new Gson();
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        try (var jsonReader = new JsonReader(
                new InputStreamReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(filename)))) {
            Measurement[] measurements = gson.fromJson(jsonReader, Measurement[].class);
            return Arrays.asList(measurements);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
