package org.htwdresden.informatik.escqrsdemo.services;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.htwdresden.informatik.escqrsdemo.event.base.IdResponse;
import org.htwdresden.informatik.escqrsdemo.model.Country;

public class PortDto {

    @Getter
    @Builder
    @ToString
    public static class PortDetailsDto {
        private Long portId;
        private String portName;
        private Country country;
    }

    @Getter
    @Builder
    public static class PortIdDto implements IdResponse {
        private Long portId;
    }
}
