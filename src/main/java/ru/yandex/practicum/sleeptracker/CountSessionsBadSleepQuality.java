package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class CountSessionsBadSleepQuality implements Function<List<SleepingSession>, SleepAnalysisResult>  {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(
                    "Количество сессий с плохим качеством сна: ","Нет данных");
        }
        long badQualityCount = sessions
                .stream()
                .filter(session -> session.getSleepQuality() == SleepQuality.BAD)
                .count();

        return new SleepAnalysisResult("Количество сессий с плохим качеством сна: ", badQualityCount);
    }
}
