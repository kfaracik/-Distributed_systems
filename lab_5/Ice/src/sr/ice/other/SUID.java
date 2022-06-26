package sr.ice.other;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class SUID {
    private static final long xFFFFFF = 0xFFFFFF;
    private static final int xFF = 0xFF;

    private static final DateFormat SDF_MED = SimpleDateFormat.getDateTimeInstance( //
            SimpleDateFormat.MEDIUM, //
            SimpleDateFormat.MEDIUM);
    private static final SUID[] INSTANCES = new SUID[xFF + 1];

    static { // initiate 0-255 instances, to avoid duplication
        for (int i = 0; i <= xFF; i++) {
            INSTANCES[i] = new SUID(i);
        }
    }

    private final AtomicLong INC = new AtomicLong();
    private int instanceId = 0; // instanceId for different applications

    private SUID(int instanceId) {
        this.instanceId = instanceId;
    }

    public static long id() {
        return INSTANCES[0].next();
    }

    public static long id(int instanceId) {
        if (instanceId < 0 || instanceId > xFF)
            return INSTANCES[0].next();
        else
            return INSTANCES[instanceId].next();
    }

    public static String toString(long id) {
        String hex = Long.toHexString(id);
        return hex.subSequence(0, 8) //
                + "-" + hex.substring(8, 14) //
                + "-" + hex.substring(14);
    }

    public static String toStringLong(long id) {
        long time = (System.currentTimeMillis() >> 42 << 42) + (id >> 22);
        long inc = (id >> 8) & xFFFFFF;
        long instanceId = id & xFF;

        return id + " (DEC)"//
                + "\n" + toString(id) + "  (HEX)" //
                + "\ntime=" + SDF_MED.format(new Date(time)) + ", instanceId=" + instanceId + ", inc=" + inc;
    }

    private long next() {
        return ((System.currentTimeMillis() >> 10) << 32) // timestamp
                + ((INC.incrementAndGet() & xFFFFFF) << 8) // auto incremental
                + instanceId // instance id
                ;
    }
}

