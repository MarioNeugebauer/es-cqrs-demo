package org.htwdresden.informatik.escqrsdemo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Port {

    private static final Logger log = LoggerFactory.getLogger(Port.class);

    public static final Port AT_SEA = new Port("", Country.NONE);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Country country;

    public Port(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }
}
