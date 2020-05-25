package org.htwdresden.informatik.escqrsdemo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ship {

    private static final Logger log = LoggerFactory.getLogger(Ship.class);

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Port port;
    private String name;
    @OneToMany
    private List<Cargo> cargoList = new ArrayList<Cargo>();

    public Ship(String name) {
        this.name = name;
    }

}
