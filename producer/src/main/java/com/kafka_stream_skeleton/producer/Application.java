package com.kafka_stream_skeleton.producer;

import java.util.Date;

public class Application {
    public static void main(String[] args) {
        String topic = System.getenv("INPUT_TOPIC");
        System.out.println(topic);
        LoginProducer loginProducer = new LoginProducer();

        while (true) {

            loginProducer.produce(topic, "bla", "bla", "bla", new Date().getTime());
        }
    }
}
