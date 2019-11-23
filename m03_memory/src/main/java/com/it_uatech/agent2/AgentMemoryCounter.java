package com.it_uatech.agent2;

/**
 * -javaagent:Agent.jar
 */

import java.lang.instrument.Instrumentation;

public class AgentMemoryCounter {
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("Agent.jar is running!");
        AgentMemoryCounter.instrumentation = instrumentation;
    }

    public static long getSize(Object obj) {
        if (instrumentation == null) {
            throw new IllegalStateException("Agent not initialised");
        }
        return instrumentation.getObjectSize(obj);
    }
}
