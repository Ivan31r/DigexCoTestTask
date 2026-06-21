package ru.digexco.numberConsumerService.service;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.digexco.NumberGeneratorServiceGrpc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static ru.digexco.NumberGenerator.NumberRequest;

@Slf4j
@Service
public class GrpcClientService {
    @GrpcClient("myGrpcService")
    private NumberGeneratorServiceGrpc.NumberGeneratorServiceStub numberGeneratorServiceStub;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final AtomicLong currentValue = new AtomicLong(0);

    public void startValueProcessing() {
        NumberRequest request = getInitialRequest();
        NumberClientStreamObserver numberObserver = new NumberClientStreamObserver();
        numberGeneratorServiceStub.getNumber(request, numberObserver);

        AtomicInteger counter = new AtomicInteger();
        executor.scheduleAtFixedRate(() -> {
            long lastServerValue = numberObserver.consumeLastNumber();
            long result = currentValue.addAndGet(lastServerValue + 1);
            log.info("Current value: {}", result);
            if (counter.incrementAndGet() >= 50) {
                executor.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private static NumberRequest getInitialRequest() {
        return NumberRequest
                .newBuilder()
                .setFirstValue(0)
                .setLastValue(30)
                .build();
    }


}
