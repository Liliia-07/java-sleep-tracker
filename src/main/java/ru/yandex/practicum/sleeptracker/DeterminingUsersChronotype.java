package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DeterminingUsersChronotype implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final LocalTime LARK_MAX_START = LocalTime.of(22, 0);
    private static final LocalTime LARK_MAX_END = LocalTime.of(7, 0);
    private static final LocalTime OWL_MIN_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_MIN_END = LocalTime.of(9, 0);
    private static final LocalTime NIGHT_START = LocalTime.of(0, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);


    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(
                    "Хронотип пользователя:  ", "Нет данных");
        }

        Map<Chronotype, Long> chronotypeCounts = sessions.stream()
                .filter(this::isNightSession)
                .map(this::classifySession)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        Chronotype userChronotype = determineUserChronotype(chronotypeCounts);

        return new SleepAnalysisResult("Хронотип пользователя: ", Chronotype.getName(userChronotype));
    }

    private boolean isNightSession(SleepingSession session) {
        LocalDate startDate = session.getStartSleep().toLocalDate();
        LocalDate endDate = session.getEndSleep().toLocalDate();

        if (!startDate.equals(endDate)) {
            return true;
        }

        LocalTime startTime = session.getStartSleep().toLocalTime();
        LocalTime endTime = session.getEndSleep().toLocalTime();

        return !startTime.isAfter(NIGHT_END) && !endTime.isBefore(NIGHT_START);
    }

    private Chronotype classifySession(SleepingSession session) {
        LocalTime startTime = session.getStartSleep().toLocalTime();
        LocalTime endTime = session.getEndSleep().toLocalTime();

        boolean isOwl = startTime.isAfter(OWL_MIN_START) && endTime.isAfter(OWL_MIN_END);
        boolean isLark = startTime.isBefore(LARK_MAX_START) && endTime.isBefore(LARK_MAX_END);

        if (isOwl) {
            return Chronotype.OWL;
        } else if (isLark) {
            return Chronotype.LARK;
        } else {
            return Chronotype.DOVE;
        }
    }

    private Chronotype determineUserChronotype(Map<Chronotype, Long> counts) {
        long owlCount = counts.getOrDefault(Chronotype.OWL, 0L);
        long larkCount = counts.getOrDefault(Chronotype.LARK, 0L);
        long doveCount = counts.getOrDefault(Chronotype.DOVE, 0L);

        long maxCount = Math.max(Math.max(owlCount, larkCount), doveCount);

        long maxTypesCount = counts.values().stream()
                .filter(count -> count == maxCount)
                .count();

        if (maxTypesCount == 1) {
            return counts.entrySet().stream()
                    .filter(entry -> entry.getValue() == maxCount)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(Chronotype.DOVE);
        }

        return Chronotype.DOVE;
    }
}