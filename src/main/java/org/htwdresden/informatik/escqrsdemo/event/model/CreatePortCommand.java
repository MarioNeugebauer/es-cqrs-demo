package org.htwdresden.informatik.escqrsdemo.event.model;

import lombok.Getter;
import org.htwdresden.informatik.escqrsdemo.event.base.Command;
import org.htwdresden.informatik.escqrsdemo.event.base.CommandSynchronous;
import org.htwdresden.informatik.escqrsdemo.event.base.DomainEvent;
import org.htwdresden.informatik.escqrsdemo.model.Port;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class CreatePortCommand extends DomainEvent implements CommandSynchronous {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Port port;

    public CreatePortCommand(LocalDateTime occuredDate, Port newPort) {
        super(occuredDate);
        this.port = newPort;
    }
}
