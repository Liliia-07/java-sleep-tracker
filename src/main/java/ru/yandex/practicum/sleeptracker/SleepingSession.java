package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {
    private LocalDateTime startSleep;
    private LocalDateTime endSleep;
    private SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime startSleep, LocalDateTime endSleep, SleepQuality sleepQuality) {
        this.startSleep = startSleep;
        this.sleepQuality = sleepQuality;
        this.endSleep = endSleep;
    }

    public LocalDateTime getStartSleep() {
        return startSleep;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    public LocalDateTime getEndSleep() {
        return endSleep;
    }
}
