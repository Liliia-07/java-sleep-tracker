package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Function<List<SleepingSession>, SleepAnalysisResult>> functions = Arrays.asList(
                new AverageSessionDurationInMinutes(),
                new CountSessionsBadSleepQuality(),
                new CountSleepSessionsPerPeriod(),
                new MaximumSessionDurationInMinutes(),
                new MinimumSessionDurationInMinutes(),
                new CountSleeplessNights(),
                new DeterminingUsersChronotype()
        );

        System.out.println("Введите путь к файлу: ");
        Path filePath = Paths.get(scanner.nextLine());

        if (Files.exists(filePath)) {
            try {
                List<SleepingSession> sessions = readSleepSessionsFromFile(filePath);

                if (sessions.isEmpty()) {
                    System.out.println("Файл не содержит записей о сне.");
                    return;
                }

                runAnalysis(sessions, functions);
                System.out.println("Всего проанализировано сессий: " + sessions.size());

            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ошибка при обработке данных: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Файл не найден: " + filePath.toAbsolutePath());
        }
    }

    private static List<SleepingSession> readSleepSessionsFromFile(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

        return lines.stream()
                .filter(line -> !line.trim().isEmpty())
                .map(SleepTrackerApp::parseSession)
                .collect(Collectors.toList());
    }

    private static SleepingSession parseSession(String line) {
        String[] parts = line.split(";");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный формат строки: " + line);
        }

        try {
            LocalDateTime startSleep = LocalDateTime.parse(parts[0].trim(), FORMATTER);
            LocalDateTime endSleep = LocalDateTime.parse(parts[1].trim(), FORMATTER);
            SleepQuality quality = SleepQuality.valueOf(parts[2].trim().toUpperCase());

            return new SleepingSession(startSleep, endSleep, quality);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка парсинга строки: " + line, e);
        }
    }

    private static void runAnalysis(List<SleepingSession> sessions,
                                    List<Function<List<SleepingSession>, SleepAnalysisResult>> functions) {
        functions.stream()
                .map(function -> function.apply(sessions))
                .forEach(SleepTrackerApp::printResult);
    }

    private static void printResult(SleepAnalysisResult result) {
        System.out.println(result.getDescription() + ": " + result.getResult());
    }
}