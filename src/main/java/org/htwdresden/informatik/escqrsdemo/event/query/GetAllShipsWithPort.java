package org.htwdresden.informatik.escqrsdemo.event.query;

import org.htwdresden.informatik.escqrsdemo.event.base.DomainEvent;
import org.htwdresden.informatik.escqrsdemo.event.base.Query;
import org.htwdresden.informatik.escqrsdemo.eventhandler.impl.SimpleCommandHandlerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class GetAllShipsWithPort extends DomainEvent implements Query {
    public GetAllShipsWithPort(LocalDateTime occuredDate) {
        super(occuredDate);
    }
}
