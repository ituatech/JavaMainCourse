package com.it_uatech.application;

import com.google.gson.Gson;
import com.it_uatech.api.FrontendServiceAccount;
import com.it_uatech.api.FrontendServiceUser;
import com.it_uatech.channel.SocketMessageTransferClient;
import com.it_uatech.service.FrontendServiceAccountImpl;
import com.it_uatech.service.FrontendServiceUserImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean(name = "messageTransfer")
    SocketMessageTransferClient getSocketMessageTransferClient(@Value("${frontend.host}")String host, @Value("${frontend.port}")int port) throws IOException {
        return new SocketMessageTransferClient(host, port);
    }

    @Bean(name = "serviceAccount")
    FrontendServiceAccount getFrontendServiceAccount(SocketMessageTransferClient worker){
        return new FrontendServiceAccountImpl(worker);
    }

    @Bean(name = "serviceUser")
    FrontendServiceUser getFrontendServiceUser(SocketMessageTransferClient worker){
        return new FrontendServiceUserImpl(worker, new Gson());
    }
}
