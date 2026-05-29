package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerAppTest {

    private List<SleepingSession> sessions;
    private List<SleepingSession> emptySessions;

    @BeforeEach
    void setUp() {
        sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 1, 22, 15),
                        LocalDateTime.of(2026, 10, 2, 8, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 2, 23, 0),
                        LocalDateTime.of(2026, 10, 3, 8, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 3, 14, 30),
                        LocalDateTime.of(2026, 10, 3, 15, 20),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 3, 23, 30),
                        LocalDateTime.of(2026, 10, 4, 6, 20),
                        SleepQuality.BAD
                )
        );

        emptySessions = Collections.emptyList();
    }

    @Test
    void testCountSleepSessionsPerPeriod_WithSessions() {
        CountSleepSessionsPerPeriod function = new CountSleepSessionsPerPeriod();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(4, result.getResult());
    }

    @Test
    void testCountSleepSessionsPerPeriod_EmptyList() {
        CountSleepSessionsPerPeriod function = new CountSleepSessionsPerPeriod();
        SleepAnalysisResult result = function.apply(emptySessions);

        assertEquals("Нет данных", result.getResult());
    }

    @Test
    void testMinimumSessionDurationInMinutes_WithSessions() {
        MinimumSessionDurationInMinutes function = new MinimumSessionDurationInMinutes();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(50, (long) result.getResult());
    }

    @Test
    void testMinimumSessionDurationInMinutes_SingleSession() {
        MinimumSessionDurationInMinutes function = new MinimumSessionDurationInMinutes();
        List<SleepingSession> singleSession = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 1, 22, 0),
                        LocalDateTime.of(2026, 10, 2, 6, 0),
                        SleepQuality.GOOD
                )
        );

        SleepAnalysisResult result = function.apply(singleSession);
        assertEquals(480, (long) result.getResult());
    }

    @Test
    void testMinimumSessionDurationInMinutes_EmptyList() {
        MinimumSessionDurationInMinutes function = new MinimumSessionDurationInMinutes();
        SleepAnalysisResult result = function.apply(emptySessions);

        assertEquals("Нет данных", result.getResult());
    }

    @Test
    void testMaximumSessionDurationInMinutes_WithSessions() {
        MaximumSessionDurationInMinutes function = new MaximumSessionDurationInMinutes();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(585, (long) result.getResult());
    }

    @Test
    void testMaximumSessionDurationInMinutes_SingleSession() {
        MaximumSessionDurationInMinutes function = new MaximumSessionDurationInMinutes();
        List<SleepingSession> singleSession = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 1, 23, 0),
                        LocalDateTime.of(2026, 10, 2, 7, 0),
                        SleepQuality.GOOD
                )
        );

        SleepAnalysisResult result = function.apply(singleSession);
        assertEquals(480, (long) result.getResult());
    }

    @Test
    void testMaximumSessionDurationInMinutes_EmptyList() {
        MaximumSessionDurationInMinutes function = new MaximumSessionDurationInMinutes();
        SleepAnalysisResult result = function.apply(emptySessions);

        assertEquals("Нет данных", result.getResult());
    }

    @Test
    void testAverageSessionDurationInMinutes_WithSessions() {
        AverageSessionDurationInMinutes function = new AverageSessionDurationInMinutes();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(396, (long) result.getResult());
    }

    @Test
    void testAverageSessionDurationInMinutes_SingleSession() {
        AverageSessionDurationInMinutes function = new AverageSessionDurationInMinutes();
        List<SleepingSession> singleSession = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 1, 22, 0),
                        LocalDateTime.of(2026, 10, 2, 6, 0),
                        SleepQuality.GOOD
                )
        );

        SleepAnalysisResult result = function.apply(singleSession);
        assertEquals(480, (long) result.getResult());
    }

    @Test
    void testAverageSessionDurationInMinutes_EmptyList() {
        AverageSessionDurationInMinutes function = new AverageSessionDurationInMinutes();
        SleepAnalysisResult result = function.apply(emptySessions);

        assertEquals("Нет данных", result.getResult());
    }

    @Test
    void testCountSessionsBadSleepQuality_WithBadSessions() {
        CountSessionsBadSleepQuality function = new CountSessionsBadSleepQuality();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(1, (long) result.getResult());
    }

    @Test
    void testCountSessionsBadSleepQuality_NoBadSessions() {
        CountSessionsBadSleepQuality function = new CountSessionsBadSleepQuality();
        List<SleepingSession> goodSessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 1, 22, 0),
                        LocalDateTime.of(2026, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 2, 23, 0),
                        LocalDateTime.of(2026, 10, 3, 7, 0),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = function.apply(goodSessions);
        assertEquals(0, (long) result.getResult());
    }

    @Test
    void testCountSessionsBadSleepQuality_AllBadSessions() {
        CountSessionsBadSleepQuality function = new CountSessionsBadSleepQuality();
        List<SleepingSession> badSessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 1, 22, 0),
                        LocalDateTime.of(2026, 10, 2, 6, 0),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 2, 23, 0),
                        LocalDateTime.of(2026, 10, 3, 7, 0),
                        SleepQuality.BAD
                )
        );

        SleepAnalysisResult result = function.apply(badSessions);
        assertEquals(2, (long) result.getResult());
    }

    @Test
    void testCountSessionsBadSleepQuality_EmptyList() {
        CountSessionsBadSleepQuality function = new CountSessionsBadSleepQuality();
        SleepAnalysisResult result = function.apply(emptySessions);

        assertEquals("Нет данных", result.getResult());
    }

    @Test
    void testCountSleeplessNights_NoSleeplessNights() {
        CountSleeplessNights function = new CountSleeplessNights();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(0, (long) result.getResult());
    }


    @Test
    void testCountSleeplessNights_DaytimeSessionOnly() {
        CountSleeplessNights function = new CountSleeplessNights();
        List<SleepingSession> daytimeSession = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 1, 14, 0),
                        LocalDateTime.of(2026, 10, 1, 16, 0),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = function.apply(daytimeSession);
        assertEquals(0, (long) result.getResult());
    }

    @Test
    void testCountSleeplessNights_EmptyList() {
        CountSleeplessNights function = new CountSleeplessNights();
        SleepAnalysisResult result = function.apply(emptySessions);

        assertEquals("Нет данных", result.getResult());
    }

    @Test
    void testCountSleeplessNights_MultipleNights() {
        CountSleeplessNights function = new CountSleeplessNights();
        List<SleepingSession> sessionsWithMultipleNights = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 1, 22, 0),
                        LocalDateTime.of(2026, 10, 2, 8, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 4, 23, 0),
                        LocalDateTime.of(2026, 10, 5, 7, 0),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = function.apply(sessionsWithMultipleNights);
        assertEquals(2, (long) result.getResult());
    }

    @Test
    void testChronotypeFunction_Owl() {
        DeterminingUsersChronotype function = new DeterminingUsersChronotype();
        List<SleepingSession> owlSessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 1, 23, 30),
                        LocalDateTime.of(2026, 10, 2, 10, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 2, 23, 30),
                        LocalDateTime.of(2026, 10, 3, 9, 30),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = function.apply(owlSessions);

        assertEquals("Сова", result.getResult());
    }

    @Test
    void testChronotypeFunction_Lark() {
        DeterminingUsersChronotype function = new DeterminingUsersChronotype();
        List<SleepingSession> larkSessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 1, 21, 0),
                        LocalDateTime.of(2026, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 2, 21, 30),
                        LocalDateTime.of(2026, 10, 3, 6, 30),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = function.apply(larkSessions);
        assertEquals("Жаворонок", result.getResult());
    }

    @Test
    void testChronotypeFunction_Dove() {
        DeterminingUsersChronotype function = new DeterminingUsersChronotype();
        List<SleepingSession> doveSessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 1, 21, 0),
                        LocalDateTime.of(2026, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 2, 23, 30),
                        LocalDateTime.of(2026, 10, 3, 10, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 10, 3, 22, 30),
                        LocalDateTime.of(2026, 10, 4, 7, 30),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = function.apply(doveSessions);
        assertEquals("Голубь", result.getResult());
    }

    @Test
    void testChronotypeFunction_EmptyList() {
        DeterminingUsersChronotype function = new DeterminingUsersChronotype();
        SleepAnalysisResult result = function.apply(emptySessions);

        assertEquals("Нет данных", result.getResult());
    }
}