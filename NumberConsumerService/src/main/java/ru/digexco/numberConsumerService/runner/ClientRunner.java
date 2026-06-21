package ru.digexco.numberConsumerService.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.digexco.numberConsumerService.service.GrpcClientService;

@Component
@Slf4j
public class ClientRunner implements CommandLineRunner {
    private final GrpcClientService clientService;

    public ClientRunner(GrpcClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting valueProcessing");
        clientService.startValueProcessing();
    }
}
