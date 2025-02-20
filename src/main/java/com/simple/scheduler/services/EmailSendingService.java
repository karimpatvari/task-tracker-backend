package com.simple.scheduler.services;


import com.common.EmailSendingDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailSendingService {

    private final KafkaTemplate<String, EmailSendingDto> kafkaTemplate;

    public EmailSendingService(KafkaTemplate<String, EmailSendingDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void SendEmail(EmailSendingDto task) {
        kafkaTemplate.send("EMAIL_SENDING_TASKS", task);
    }
}
