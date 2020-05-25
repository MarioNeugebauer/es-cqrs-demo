package org.htwdresden.informatik.escqrsdemo.event;

import lombok.Getter;
import org.htwdresden.informatik.escqrsdemo.event.base.Command;
import org.htwdresden.informatik.escqrsdemo.event.base.DomainEvent;
import org.htwdresden.informatik.escqrsdemo.model.Port;
import org.htwdresden.informatik.escqrsdemo.model.Ship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DepartureEvent extends DomainEvent implements Command {

    private static final Logger log = LoggerFactory.getLogger(DepartureEvent.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;
    @ManyToOne
    private Port port;
    @ManyToOne
    private Ship ship;

    public DepartureEvent(LocalDateTime date, Port port, Ship ship) {
        super(date);
        this.port = port;
        this.ship = ship;
    }

    public Port getPort() {
        return port;
    }

    public DepartureEvent setPort(Port port) {
        this.port = port;
        return this;
    }

    public Ship getShip() {
        return ship;
    }

    public DepartureEvent setShip(Ship ship) {
        this.ship = ship;
        return this;
    }
}
