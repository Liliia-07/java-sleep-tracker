package ru.yandex.practicum.sleeptracker;


import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MinimumSessionDurationInMinutes implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(
                    "Минимальная продолжительность сессии сна (в минутах): ", "Нет данных");
        }

        Duration duration = sessions
                .stream()
                .map(session -> Duration.between(session.getStartSleep(), session.getEndSleep()))
                .min(Duration::compareTo)
                .orElse(Duration.ZERO);

        return new SleepAnalysisResult("Минимальная продолжительность сессии (в минутах): ", duration.toMinutes());
    }
}
