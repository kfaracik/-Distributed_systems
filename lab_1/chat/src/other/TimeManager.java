package other;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimeManager {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private TimeManager() {}

    public static String getTime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return dateFormat.format(timestamp);
    }
}
