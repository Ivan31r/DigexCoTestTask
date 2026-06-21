package ru.digexco.numberConsumerService.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.digexco.NumberGenerator;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class NumberClientStreamObserver implements StreamObserver<NumberGenerator.NumberResponse> {

    private final AtomicLong lastNumber;

    public NumberClientStreamObserver() {
        this.lastNumber = new AtomicLong(0);
    }

    @Override
    public void onNext(NumberGenerator.NumberResponse value) {
        log.info("Received new value from server: {}", value.getNumber());
        lastNumber.set(value.getNumber());
    }

    @Override
    public void onError(Throwable t) {
    }

    @Override
    public void onCompleted() {
        log.info("Number client stream completed");
    }

    public long consumeLastNumber() {
        return lastNumber.getAndSet(0);
    }
}
