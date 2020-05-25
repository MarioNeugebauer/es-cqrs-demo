package org.htwdresden.informatik.escqrsdemo.event.query;

import lombok.Getter;
import lombok.Setter;
import org.htwdresden.informatik.escqrsdemo.event.base.QueryResponse;
import org.htwdresden.informatik.escqrsdemo.services.ShipDto;

import java.util.List;

@Getter @Setter
public class GetAllShipsWithPortResponse implements QueryResponse {

    private List<ShipDto.ShipAtPortDto> shipAtPortDtoList;

}
