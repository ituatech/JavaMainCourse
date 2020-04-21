package com.it_uatech.l31.messages;

import com.it_uatech.l31.app.Msg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by tully.
 */
public class PingMsg extends Msg {
    private final String time;
    private final String addressee;

    public PingMsg(String addressee) {
        super(PingMsg.class);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        time = dtf.format(now);
        this.addressee = addressee;
    }

    public String getTime() {
        return time;
    }

    public String getAddressee() {
        return addressee;
    }

    @Override
    public String toString() {
        return "PingMsg from " +addressee+" - {" + "time=" + time + '}';
    }
}
