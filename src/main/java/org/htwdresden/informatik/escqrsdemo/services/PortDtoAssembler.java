package org.htwdresden.informatik.escqrsdemo.services;

import org.htwdresden.informatik.escqrsdemo.model.Port;
import org.htwdresden.informatik.escqrsdemo.model.Ship;

public class PortDtoAssembler {
    public static PortDto.PortDetailsDto assemble(Port port) {
        if(port==null) return null;

        return new PortDto.PortDetailsDto(port.getId(),
                port.getName(), port.getCountry());
    }

    public static PortDto.PortIdDto assemble(Long id) {
        return new PortDto.PortIdDto(id);
    }

}
