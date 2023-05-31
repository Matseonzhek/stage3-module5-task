package com.mjc.school.repository.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Utils {
    static Random random = new Random();

    private Utils() {
    }

    public static List<String> readFromFile(String fileName) {
        List<String> data = new ArrayList<>();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream resourceAsStream = classLoader.getResourceAsStream(fileName);
            InputStreamReader inputStreamReader
                    = new InputStreamReader(Objects.requireNonNull(resourceAsStream));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                data.add(line);
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e.getMessage());
        }
        return data;
    }

    public static LocalDateTime createdRandomDate() {
        LocalDate day = LocalDate.now().plusDays(random.nextInt(30));
        int hour = random.nextInt(24);
        int minutes = random.nextInt(60);
        int seconds = random.nextInt(60);
        LocalTime time = LocalTime.of(hour, minutes, seconds);
        return LocalDateTime.of(day, time);
    }

    public static Long createRandomNumber(Long range) {
        return random.nextLong(range);
    }


}
