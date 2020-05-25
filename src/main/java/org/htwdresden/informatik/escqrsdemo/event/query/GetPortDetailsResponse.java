package org.htwdresden.informatik.escqrsdemo.event.query;

import lombok.Getter;
import lombok.Setter;
import org.htwdresden.informatik.escqrsdemo.event.base.QueryResponse;
import org.htwdresden.informatik.escqrsdemo.services.PortDto;

@Getter @Setter
public class GetPortDetailsResponse implements QueryResponse {
    private PortDto.PortDetailsDto portDtoList;
}
