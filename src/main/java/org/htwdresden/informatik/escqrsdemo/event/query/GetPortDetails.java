package org.htwdresden.informatik.escqrsdemo.event.query;

import lombok.Getter;
import org.htwdresden.informatik.escqrsdemo.event.base.DomainEvent;
import org.htwdresden.informatik.escqrsdemo.event.base.Query;

import java.time.LocalDateTime;

@Getter
public class GetPortDetails extends DomainEvent implements Query {
    private Long portId;
    public GetPortDetails(LocalDateTime occuredDate) {
        super(occuredDate);
    }
}
