package com.it_uatech;

import com.it_uatech.messageSystem.MessageSystemSocketServer;
import com.it_uatech.runner.ProcessRunner;
import com.it_uatech.runner.ProcessRunnerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageSystemMain {

    private static final Logger logger = LoggerFactory.getLogger(MessageSystemMain.class);
    private static final String COMMAND_DB_SERVICE_START = "java -jar L31.1.2-dbServiceClient/target/db_server.jar";
    private static final String SOURCE_JETTY_HOME = "C:/JAVA_LERN/Jetty_Webserver";
    private static final String SOURCE_JETTY_WEBAPPS = SOURCE_JETTY_HOME + "/webapps";
    private static final String SOURCE_WAR = "L31.1.3-frontendServiceClient/target/frontend.war";
    private static final int CLIENT_START_DELAY = 3;


    public static void main(String[] args) {
        new MessageSystemMain().start();
    }

    public void start() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        MessageSystemSocketServer socketServer = new MessageSystemSocketServer();
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName objectName = new ObjectName("com.it_uatech.Multiprocessor:type=Server");
            mBeanServer.registerMBean(socketServer, objectName);
        } catch (Exception ex) {
            logger.error("MBean creation exception", ex);
        }

        deployWar(SOURCE_WAR, SOURCE_JETTY_WEBAPPS);
        startClient(executorService, COMMAND_DB_SERVICE_START);
        socketServer.start();
        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService, String command) {
        executorService.schedule(() -> {
            logger.info("Scheduled task execution, start db service");
            try {
                final ProcessRunner processRunner = new ProcessRunnerImpl();
                processRunner.start(command);
            } catch (Exception e) {
                logger.error("Process started with exception: {}", e.getLocalizedMessage());
            }
        }, CLIENT_START_DELAY, TimeUnit.SECONDS);
    }

    private void deployWar(String warPath, String jettyHomeWarPath) {
        logger.info("Copy war from path: {}, to path: {}",Paths.get(warPath),Paths.get(jettyHomeWarPath));
        try {
            Files.copy(Paths.get(warPath),
                    Paths.get(jettyHomeWarPath + "\\root.war"),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("Error {} copying war {} to jetty home {}",e,warPath,jettyHomeWarPath);
        }
    }
}
