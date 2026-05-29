package ru.yandex.practicum.sleeptracker;

public enum Chronotype {
    OWL,
    LARK,
    DOVE;

    public static String getName(Chronotype chronotype) {
        if (chronotype == Chronotype.OWL) {
            return "Сова";
        } else if (chronotype == Chronotype.LARK) {
            return "Жаворонок";
        } else {
            return "Голубь";
        }
    }
}

