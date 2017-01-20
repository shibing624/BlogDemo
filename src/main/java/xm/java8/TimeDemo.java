package xm.java8;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xuming
 */
public class TimeDemo {
    public static void main(String[] args) {
        Clock c1 = Clock.systemUTC();
        c1.millis();
        c1.getZone();
        System.out.println(LocalDateTime.now() + " " + LocalDate.now() + " " + LocalTime.now());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime d6 = LocalDateTime.parse("2013/12/31 23:59:59", formatter);
        System.out.println(formatter.format(d6));

        LocalDateTime d1 = LocalDateTime.of(2013, 12, 01, 23, 59);
        System.out.println(d1.format(DateTimeFormatter.ISO_DATE_TIME));
        System.out.println(d1.format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " "));
        System.out.println(d1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println("ofPattern: " + d1.format(DateTimeFormatter.ofPattern("yyyy:MM:dd hh:mm:ss")));

        LocalDate d2 = LocalDate.of(2017, 2, 28);
        System.out.println(d2);

        LocalTime d3 = LocalTime.of(14, 12, 29);
        System.out.println(d3);
    }
}
