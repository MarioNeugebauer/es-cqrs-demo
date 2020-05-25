package org.htwdresden.informatik.escqrsdemo.services;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.htwdresden.informatik.escqrsdemo.event.base.IdResponse;

import javax.persistence.Id;

public class ShipDto {

    @Getter
    @Builder
    @ToString
    public static class ShipDetailsDto {
        private Long shipId;
        private String shipName;
    }

    @Getter
    @Builder
    public static class ShipIdDto implements IdResponse {
        private Long shipId;
    }

    @Getter
    @Builder
    @ToString
    public static class ShipAtPortDto {
        private Long shipId;
        private String shipName;
        private Long portId;
        private String portName;
    }

}
