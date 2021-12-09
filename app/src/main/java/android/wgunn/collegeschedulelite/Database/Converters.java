package android.wgunn.collegeschedulelite.Database;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.util.Date;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static LocalDateTime toDate(String value) {
        return value == null ? null : LocalDateTime.parse(value);
    }

    @TypeConverter
    public static String toDateString(LocalDateTime date) {
        return date == null ? null : date.toString();
    }
}
