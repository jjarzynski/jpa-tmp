package com.evojam.time4jokes;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;


@SpringBootApplication
public class Time4JokesApp {

    public static void main(String[] args) {
        SpringApplication.run(Time4JokesApp.class, args);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Port() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}

// print sql
