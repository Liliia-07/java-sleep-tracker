package ru.yandex.practicum.sleeptracker;


import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageSessionDurationInMinutes implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Средняя продолжительность сессии сна (в минутах): ", "Нет данных");
        }

        Duration totalDuration = sessions.stream().map(session -> Duration.between(session.getStartSleep(), session.getEndSleep())).reduce(Duration.ZERO, Duration::plus);

        return new SleepAnalysisResult("Средняя продолжительность сессии (в минутах): ", totalDuration.toMinutes() / sessions.size());
    }
}
