package com.bynder.lottery.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static Timestamp currentTimestamp() {
        LocalDateTime ldt = LocalDateTime.now();
        return Timestamp.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date convert(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date convert(LocalDate ld) {
        return Date.from(ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
