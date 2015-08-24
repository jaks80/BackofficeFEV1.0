package com.ets.fe.util;

/**
 *
 * @author Yusuf
 */
public class PerformanceTimer {

    public static long getStartTime() {
        long startTime = System.currentTimeMillis();
        return startTime;
    }

    public static float elapsedTime(long startTime) {

        long stopTime = System.currentTimeMillis();
        long elapsedTime = (stopTime - startTime);
        System.out.println("Time Taken: "+elapsedTime);
        return elapsedTime;
    }
}
