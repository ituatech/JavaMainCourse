package com.it_uatech.api.session;

import java.io.Closeable;

public interface SessionManager extends AutoCloseable {
    void beginSession();
    void commitSession();
    void rollbackSession();
    void close();

    DatabaseSession getCurrentSession();

    void shutdown();
}
