package org.htwdresden.informatik.escqrsdemo.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.htwdresden.informatik.escqrsdemo.event.base.Command;
import org.htwdresden.informatik.escqrsdemo.event.base.DomainEvent;
import org.htwdresden.informatik.escqrsdemo.model.Port;
import org.htwdresden.informatik.escqrsdemo.model.Ship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ArrivalEvent extends DomainEvent implements Command {
    private static final Logger log = LoggerFactory.getLogger(ArrivalEvent.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Port port;

    @ManyToOne
    private Ship ship;

    @NonNull
    public ArrivalEvent(LocalDateTime date, Port port,  Ship ship) {
        super(date);
        setPort(port);
        setShip(ship);
    }

}
