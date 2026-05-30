package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class CountSleeplessNights implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(
                    "Количество бессонных ночей: ", "Нет данных");
        }

        LocalDateTime firstStart = sessions.getFirst().getStartSleep();
        LocalDateTime lastEnd = sessions.getLast().getEndSleep();

        LocalDate startNightDate = firstStart.toLocalDate();
        if (firstStart.getHour() >= 12) {
            startNightDate = startNightDate.plusDays(1);
        }

        LocalDate endNightDate = lastEnd.toLocalDate();
        if (lastEnd.getHour() < 12) {
            endNightDate = endNightDate.minusDays(1);
        }

        long totalNights = ChronoUnit.DAYS.between(startNightDate, endNightDate) + 1;

        long sleeplessNights = Stream.iterate(startNightDate, date -> date.plusDays(1))
                .limit(totalNights)
                .filter(date -> isSleepless(date, sessions))
                .count();

        return new SleepAnalysisResult("Количество бессонных ночей: ", sleeplessNights);
    }

    private boolean isSleepless(LocalDate date, List<SleepingSession> sessions) {
        LocalDateTime nightStart = date.atTime(0, 0);
        LocalDateTime nightEnd = date.atTime(6, 0);

        return sessions.stream().noneMatch(session ->
                session.getStartSleep().isBefore(nightEnd) &&
                        session.getEndSleep().isAfter(nightStart)
        );
    }
}