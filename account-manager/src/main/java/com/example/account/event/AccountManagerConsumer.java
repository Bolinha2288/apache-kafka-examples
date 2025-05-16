package com.example.account.event;

import com.example.account.domain.model.Account;
import com.example.account.domain.repository.AccountRepository;
import com.example.account.dto.UserEventDTO;
import com.example.account.utils.AccountNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountManagerConsumer {

    private final AccountRepository accountRepository;
    private final AccountNumber accountNumber;

    public AccountManagerConsumer(AccountRepository accountRepository, AccountNumber accountNumber) {
        this.accountRepository = accountRepository;
        this.accountNumber = accountNumber;
    }

    @KafkaListener( topics = "${spring.kafka.topic.name}",
                    groupId = "${spring.kafka.consumer.group-id}",
                    containerFactory = "kafkaListenerContainerFactory")
    public void consumeEvent(UserEventDTO userEventDTO) {
        log.info("User event received in account-manager service {}", userEventDTO);

        Account account = createAccount(userEventDTO);

        accountRepository.save(account);

        log.info("Account created successfully {}", account);
    }

    private Account createAccount(UserEventDTO userEventDTO) {
        Account account = new Account();
        account.setUserReference(userEventDTO.getUserDTO().getIdReference());
        account.setNumberAccount(accountNumber.generate(userEventDTO.getUserDTO().getIdReference()));
        account.setName(userEventDTO.getUserDTO().getName());
        return account;
    }

}
