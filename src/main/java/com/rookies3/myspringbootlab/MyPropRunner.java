package com.rookies3.myspringbootlab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyPropRunner.class);
//  @Value("${myprop.username}")
//  private String username;

//  @Value("${myprop.port}")
//  private int port;

    @Autowired
    private MyPropProperties props;

    @Autowired(required = false)
    private MyEnvironment myEnv;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("========= 로거로 값 출력하기 =========");
        logger.debug("myprop.username (DEBUG) = {}", props.getUsername());
        logger.info("myprop.username (INFO) = {}", props.getUsername());
        logger.info("myprop.port = {}", props.getPort());
        logger.info("=====================================");

        if (myEnv != null) {
            logger.info(">> 현재 활성화된 프로파일: {}", myEnv.getMode());
        } else {
            logger.info(">> 활성화된 프로파일이 없습니다.");
        }
    }
}
