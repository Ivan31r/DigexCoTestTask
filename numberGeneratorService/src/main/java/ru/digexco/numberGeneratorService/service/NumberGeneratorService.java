package ru.digexco.numberGeneratorService.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.digexco.NumberGenerator.NumberRequest;
import ru.digexco.NumberGenerator.NumberResponse;
import ru.digexco.NumberGeneratorServiceGrpc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@GrpcService
@Slf4j
public class NumberGeneratorService extends NumberGeneratorServiceGrpc.NumberGeneratorServiceImplBase {
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void getNumber(NumberRequest request, StreamObserver<NumberResponse> responseObserver) {
        log.info("New request received with parameters {}", request);
        AtomicLong firstValue = new AtomicLong(request.getFirstValue());
        Runnable task = () -> {
            long nextValue = firstValue.incrementAndGet();
            NumberResponse numberResponse = NumberResponse
                    .newBuilder()
                    .setNumber(nextValue)
                    .build();
            responseObserver.onNext(numberResponse);
            if (nextValue >= request.getLastValue()) {
                responseObserver.onCompleted();
                log.info("Request completed with value {}", nextValue);
            }
        };
        executorService.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
    }
}
