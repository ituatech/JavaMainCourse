package com.it_uatech.l32.messages;

import com.it_uatech.l32.app.Msg;

public class PingMsg extends Msg {
    private final long time;
    private final String pid;

    public PingMsg(String pid) {
        super(PingMsg.class);
        this.pid = pid;
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    public String getPid() {
        return pid;
    }

    @Override
    public String toString() {
        return "PingMsg{" + "pid='" + pid + ", time=" + time + '\'' + '}';
    }
}
