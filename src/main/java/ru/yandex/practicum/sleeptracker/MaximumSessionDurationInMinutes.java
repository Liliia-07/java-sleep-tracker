package ru.yandex.practicum.sleeptracker;


import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MaximumSessionDurationInMinutes implements Function<List<SleepingSession>, SleepAnalysisResult>  {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(
                    "Максимальная продолжительность сессии сна (в минутах): ","Нет данных");
        }

        Duration duration = sessions
                .stream()
                .map(session -> Duration.between(session.getStartSleep(),session.getEndSleep()))
                .max(Duration::compareTo)
                .orElse(Duration.ZERO);
        
        return new SleepAnalysisResult("Максимальная продолжительность сессии (в минутах): ", duration.toMinutes());
    }
}
