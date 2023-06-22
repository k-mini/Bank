package shop.mtcoding.bank.util;

import java.time.format.DateTimeFormatter;

public class CustomDateUtil {

    public static String toStringFormat(java.time.LocalDateTime localDateTime) {
        // System.out.println(localDateTime);
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
