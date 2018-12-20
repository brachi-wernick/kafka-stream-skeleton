package com.kafka_stream_skeleton.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.kafka_stream_skeleton.model.LoginData;

import java.text.SimpleDateFormat;
import java.util.Properties;

public class LoginProducer {


    private Producer producer;

    private Producer<String, LoginData> getProducer() {
        String kafkaUrl = System.getenv("KAFKA_URL");

        if(kafkaUrl==null){
            throw new RuntimeException("kafka url must be given");
        }
        System.out.println("start produce data to kafka "+kafkaUrl);


        if (producer == null) {
            Properties configProperties = new Properties();
            configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
            configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.kafka_stream_skeleton.producer.serialization.JsonPOJOSerializer");

            producer = new KafkaProducer<String, String>(configProperties);
        }

        return producer;

    }


    public void produce(String topicName, String userName, String userPassword, String ip, Long date) {

        LoginData loginData = new LoginData(userName, userPassword, ip, date, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").format(date));

        System.out.println("produce:" + loginData);

        ProducerRecord<String, LoginData> rec = new ProducerRecord<>(topicName, loginData);

        getProducer().send(rec);
    }

}
