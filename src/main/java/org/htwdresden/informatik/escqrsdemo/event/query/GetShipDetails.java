package org.htwdresden.informatik.escqrsdemo.event.query;

import lombok.Getter;
import org.htwdresden.informatik.escqrsdemo.event.base.DomainEvent;
import org.htwdresden.informatik.escqrsdemo.event.base.Query;

import java.time.LocalDateTime;

@Getter
public class GetShipDetails extends DomainEvent implements Query {

    private Long shipId;

    public GetShipDetails(LocalDateTime occuredDate) {
        super(occuredDate);
    }
}
