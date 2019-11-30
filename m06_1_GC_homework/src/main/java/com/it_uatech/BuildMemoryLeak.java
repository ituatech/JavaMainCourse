package com.it_uatech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BuildMemoryLeak {
    private static final Logger logger = LoggerFactory.getLogger(BuildMemoryLeak.class);
    private final int increaseListSize = 450;
    private final int decreaseListSize = 245;
    private List<String> list = new LinkedList<>();

    private BuildMemoryLeak() {
    }

    public static BuildMemoryLeak getInstance() {
        return new BuildMemoryLeak();
    }

    private void increaseList() {
        for (int i = 0; i < increaseListSize; i++) {
            list.add(new String(new char[0]));
        }
        logger.info("Added {} objects", increaseListSize);
    }

    private void decreaseList() {
        for (int i = 0; i < decreaseListSize; i++) {
            list.remove(0);
        }
        logger.info("Removed {} objects", decreaseListSize);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void appStart() throws OutOfMemoryError {
        while (true) {
            increaseList();
            decreaseList();
            logger.info("Total objects in the List {}", list.size());
            try {
                Thread.sleep(65);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
