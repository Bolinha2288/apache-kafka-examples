package com.example.email.event;

import com.example.email.dto.UserEventDTO;
import com.example.email.utils.DispatchMail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class EmailManagerConsumer {

    private final DispatchMail dispatchMail;

    public EmailManagerConsumer(DispatchMail dispatchMail) {
        this.dispatchMail = dispatchMail;
    }

    @KafkaListener( topics = "${spring.kafka.topic.name}",
                    groupId = "${spring.kafka.consumer.group-id}",
                    containerFactory = "kafkaListenerContainerFactory")
    public void consumer(UserEventDTO userEventDTO){
        log.info("User event received in email-manager service {}", userEventDTO);

        sendMessageEmail(userEventDTO);

    }

    private void sendMessageEmail(UserEventDTO userEventDTO) {
        String subject = "Welcome!";
        String templateName = "welcome-user";

        Map<String, Object> model = Map.of(
                "userName", userEventDTO.getUserDTO().getName(),
                "greeting", "Hellow, how are you?",
                "subject", subject
        );

        dispatchMail.sendHtmlEmail(userEventDTO.getUserDTO().getEmail(), subject, templateName, model);
    }

}
