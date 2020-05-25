package org.htwdresden.informatik.escqrsdemo.event.query;

import lombok.Getter;
import lombok.Setter;
import org.htwdresden.informatik.escqrsdemo.event.base.QueryResponse;
import org.htwdresden.informatik.escqrsdemo.services.PortDto;

import java.util.List;

@Getter @Setter
public class GetAllPortsResponse implements QueryResponse {
    private List<PortDto.PortDetailsDto> portDetailsDtoList;
}
