package org.htwdresden.informatik.escqrsdemo.event.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public abstract class DomainEvent {

    private static final Logger log = LoggerFactory.getLogger(DomainEvent.class);

    LocalDateTime recordedDate, occuredDate;

    public DomainEvent(LocalDateTime occuredDate) {
        log.info("created at "+occuredDate.toString());
        this.occuredDate = occuredDate;
        this.recordedDate = LocalDateTime.now();
    }

}
