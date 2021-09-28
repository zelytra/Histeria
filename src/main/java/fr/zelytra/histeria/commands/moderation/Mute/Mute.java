/*
 * Copyright (c) 2021. Zelytra
 * All right reserved
 */

package fr.zelytra.histeria.commands.moderation.Mute;

public class Mute {

    private final long startTime;
    private long time;
    private final String reason;
    private final String staffName;

    public Mute(long startTime, long time, String reason, String staffName) {
        this.startTime = startTime;
        this.time = time;
        this.reason = reason;
        this.staffName = staffName;
    }

    public void unMute() {
        this.time = 0;
    }

    public boolean isMute() {
        return ((System.currentTimeMillis() - this.startTime) / 1000) <= time;
    }

    public String getReason() {
        return reason;
    }

    public String getStaffName() {
        return staffName;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getTime() {
        return time;
    }
}
