package xm.java8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

/**
 * 日期和时间
 * Date Time DateTime
 * Created by xuming on 2016/8/8.
 *   SQL -> Java
     --------------------------
     date -> LocalDate
     time -> LocalTime
     timestamp -> LocalDateTime
 */
public class NewData {

    public static void main(String[] args){
        //LocalDate
        LocalDate today = LocalDate.now();

        LocalDate crisChri = LocalDate.of(2014,4,5);
        LocalDate endOfFeb = LocalDate.parse("2000-02-03");
        LocalDate d =  LocalDate.parse("2003-09-03");

        LocalDate firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());

        LocalDate secDayOfMonth = today.withDayOfMonth(2);
        LocalDate lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate firstDayOfYear = lastDayOfMonth.withDayOfYear(1);
        LocalDate firstMondayOfYear = LocalDate.of(2013,1,1).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

        LocalTime nowTime = LocalTime.now();
        LocalTime nowWithoutNano = nowTime.withNano(0);
        LocalTime nowWithNano = nowTime.withNano(1);
        LocalTime zero = LocalTime.of(0,0);
        LocalTime mid = LocalTime.parse("12:03:33");

    }
}
