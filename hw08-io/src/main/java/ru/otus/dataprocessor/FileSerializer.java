package ru.otus.dataprocessor;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final File file;
    private final Gson gson;

    public FileSerializer(String fileName) {
        this.file = new File(fileName);
        this.gson = new Gson();
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        String jsonAsString = gson.toJson(data);

        try (var objectOutputStream = new FileWriter(file)) {
            objectOutputStream.write(jsonAsString);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
