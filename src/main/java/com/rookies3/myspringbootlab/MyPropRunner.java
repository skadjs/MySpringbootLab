package com.rookies3.myspringbootlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements ApplicationRunner {

//    @Value("${myprop.username}")
//    private String username;

//    @Value("${myprop.port}")
//    private int port;

    @Autowired
    private MyPropProperties props;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("========= @Value 어노테이션으로 값 읽기 =========");
//      System.out.println("myprop.username = " + username);
        System.out.println("myprop.username = " + props.getUsername());
//      System.out.println("myprop.port = " + port);
        System.out.println("myprop.port = " + props.getPort());
        System.out.println("==============================================");
    }
}
