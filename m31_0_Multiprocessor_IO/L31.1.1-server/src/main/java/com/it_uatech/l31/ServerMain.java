package com.it_uatech.l31;

import com.it_uatech.l31.runner.ProcessRunnerImpl;
import com.it_uatech.l31.server.EchoSocketMsgServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tully.
 */
public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    private static final String CLIENT_START_COMMAND = "java -jar L31.1.2-client/target/client.jar";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        for (int i = 0; i < 3; i++) {
           startClient(executorService);
        }

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("com.it_uatech:type=Server");
        EchoSocketMsgServer server = new EchoSocketMsgServer();
        mbs.registerMBean(server, name);

        server.start();

        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(CLIENT_START_COMMAND);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }

}
