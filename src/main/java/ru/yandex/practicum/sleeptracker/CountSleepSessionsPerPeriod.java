package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class CountSleepSessionsPerPeriod implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(
                    "Количество сессий сна за период: ", "Нет данных");
        }

        return new SleepAnalysisResult("Количество сессий сна за период: ", sessions.size());
    }
}
