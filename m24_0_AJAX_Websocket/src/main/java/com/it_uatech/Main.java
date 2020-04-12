package com.it_uatech;

import com.it_uatech.ajax.AjaxTimerServlet;
import com.it_uatech.timer.TimerServlet;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.server.CachedContentFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.it_uatech.polling.MessengerServlet;
import com.it_uatech.websocket.WebSocketChatServlet;

/**
 * @author v.chibrikov
 */
public class Main {
    private final static int PORT = 8081;
    private final static String PUBLIC_HTML = "public_html";

    public static void main(String[] args) throws Exception {

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setCacheControl("no-store");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        //page reloaded by the timer
        context.addServlet(TimerServlet.class, "/timer");
        //part of a page reloaded by the timer
        context.addServlet(AjaxTimerServlet.class, "/server-time");
        //long-polling waits till a message
        context.addServlet(new ServletHolder(new MessengerServlet()), "/messenger");
        //web chat
        context.addServlet(WebSocketChatServlet.class, "/chat");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
}
}
