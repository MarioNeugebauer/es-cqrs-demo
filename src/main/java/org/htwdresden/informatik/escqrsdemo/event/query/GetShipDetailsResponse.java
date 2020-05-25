package org.htwdresden.informatik.escqrsdemo.event.query;

import lombok.Getter;
import lombok.Setter;
import org.htwdresden.informatik.escqrsdemo.event.base.QueryResponse;
import org.htwdresden.informatik.escqrsdemo.services.ShipDto;

@Getter @Setter
public class GetShipDetailsResponse implements QueryResponse {
    private ShipDto.ShipDetailsDto shipDetailsDto;
}
