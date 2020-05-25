package org.htwdresden.informatik.escqrsdemo.event.model;

import lombok.Getter;
import org.htwdresden.informatik.escqrsdemo.event.base.Command;
import org.htwdresden.informatik.escqrsdemo.event.base.CommandSynchronous;
import org.htwdresden.informatik.escqrsdemo.event.base.DomainEvent;
import org.htwdresden.informatik.escqrsdemo.model.Ship;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class CreateShipCommand extends DomainEvent implements CommandSynchronous {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Ship ship;

    public CreateShipCommand(LocalDateTime occuredDate, Ship newShip) {
        super(occuredDate);
        this.ship = newShip;
    }
}
