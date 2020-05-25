package org.htwdresden.informatik.escqrsdemo.event.query;

import org.htwdresden.informatik.escqrsdemo.event.base.DomainEvent;
import org.htwdresden.informatik.escqrsdemo.event.base.Query;

import java.time.LocalDateTime;

public class GetAllPorts extends DomainEvent implements Query {
    public GetAllPorts(LocalDateTime occuredDate) {
        super(occuredDate);
    }
}
