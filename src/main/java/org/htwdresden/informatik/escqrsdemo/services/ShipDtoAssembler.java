package org.htwdresden.informatik.escqrsdemo.services;

import org.htwdresden.informatik.escqrsdemo.model.Port;
import org.htwdresden.informatik.escqrsdemo.model.Ship;

import java.util.Optional;

public class ShipDtoAssembler {

    public static ShipDto.ShipDetailsDto assemble(Ship ship) {
        if(ship==null) return null;

        return new ShipDto.ShipDetailsDto(ship.getId(),
                ship.getName());
    }

    public static ShipDto.ShipIdDto assemble(Long id) {
        return new ShipDto.ShipIdDto(id);
    }

    public static ShipDto.ShipAtPortDto assemble(Ship ship, Port port) {

        return new ShipDto.ShipAtPortDto(
                ship==null ? -1: ship.getId(),
                ship==null?"null":ship.getName(),
                port==null?-1:port.getId(),
                port==null?"null":port.getName());
    }
}
