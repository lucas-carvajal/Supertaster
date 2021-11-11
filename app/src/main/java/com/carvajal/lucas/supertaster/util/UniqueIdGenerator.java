package com.carvajal.lucas.supertaster.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Unique id is composed of:
 * current time stamp - 47 bits (millisecond precision w/a custom epoch gives as 69 years)
 * sequence number - 17 bits - rolls over every 65536 with protection to avoid rollover in the same ms
 **/

// Class copied from Stackoverflow - all credit to User HIREN011
// https://stackoverflow.com/questions/26400779/how-to-generate-a-uniqueid-based-on-current-date/42797708

public class UniqueIdGenerator {
    private static final long twepoch = 1288834974657L;
    private static final long sequenceBits = 17;
    private static final long sequenceMax = 65536;
    private static volatile long lastTimestamp = -1L;
    private static volatile long sequence = 0L;

//    public static void main(String[] args) {
//        Set<Long> uniqueIds = new HashSet<Long>();
//        long now = System.currentTimeMillis();
//        for(int i=0; i < 100000; i++)
//        {
//            uniqueIds.add(generateLongId());
//        }
//        System.out.println("Number of Unique IDs generated: " + uniqueIds.size() + " in " + (System.currentTimeMillis() - now) + " milliseconds");
//    }

    public static synchronized Long generateLongId() {
        long timestamp = System.currentTimeMillis();
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) % sequenceMax;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        Long id = ((timestamp - twepoch) << sequenceBits) | sequence;
        return id;
    }

    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
