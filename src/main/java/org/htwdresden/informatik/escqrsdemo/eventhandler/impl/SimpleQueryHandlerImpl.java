package org.htwdresden.informatik.escqrsdemo.eventhandler.impl;

import lombok.RequiredArgsConstructor;
import org.htwdresden.informatik.escqrsdemo.event.base.QueryResponse;
import org.htwdresden.informatik.escqrsdemo.event.query.*;
import org.htwdresden.informatik.escqrsdemo.event.base.Query;
import org.htwdresden.informatik.escqrsdemo.eventhandler.QueryHandler;
import org.htwdresden.informatik.escqrsdemo.model.Port;
import org.htwdresden.informatik.escqrsdemo.model.Ship;
import org.htwdresden.informatik.escqrsdemo.repository.PortRepository;
import org.htwdresden.informatik.escqrsdemo.repository.ShipRepository;
import org.htwdresden.informatik.escqrsdemo.services.PortDto;
import org.htwdresden.informatik.escqrsdemo.services.PortDtoAssembler;
import org.htwdresden.informatik.escqrsdemo.services.ShipDto;
import org.htwdresden.informatik.escqrsdemo.services.ShipDtoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("simpleQueryHandlerImpl")
@RequiredArgsConstructor
public class SimpleQueryHandlerImpl implements QueryHandler {

    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private PortRepository portRepository;

    @Override
    public QueryResponse executeQuery(Query query) {
        if(query instanceof GetShipDetails) {
            return handleGetShipDetailsQuery((GetShipDetails) query);
        } else if(query instanceof GetPortDetails) {
            return handleGetPortDetailsQuery((GetPortDetails)query);
        } else if(query instanceof GetAllShipsWithPort) {
            return handleGetAllShipsWithPortQuery((GetAllShipsWithPort)query);
        } else if(query instanceof GetAllPorts) {
            return handleGetAllPortsQuery((GetAllPorts)query);
        }
        return null;
    }

    private GetShipDetailsResponse handleGetShipDetailsQuery(GetShipDetails query) {
        Optional<Ship> oneShipOptional = shipRepository.findById(query.getShipId());
        GetShipDetailsResponse response = new GetShipDetailsResponse();
        if(oneShipOptional.isPresent()) {
            response.setShipDetailsDto(ShipDtoAssembler.assemble(oneShipOptional.get()));
            return response;
        } else {
            return null;
        }
    }

    private GetPortDetailsResponse handleGetPortDetailsQuery(GetPortDetails query) {
        Optional<Port> onePortOptional = portRepository.findById(query.getPortId());
        GetPortDetailsResponse response = new GetPortDetailsResponse();
        if(onePortOptional.isPresent()) {
            response.setPortDtoList(PortDtoAssembler.assemble(onePortOptional.get()));
            return response;
        } else {
            return null;
        }
    }

    private GetAllShipsWithPortResponse handleGetAllShipsWithPortQuery(GetAllShipsWithPort query) {
        List<Ship> ships = (List<Ship>) shipRepository.findAll();
        List<ShipDto.ShipAtPortDto> resultDto = new ArrayList<ShipDto.ShipAtPortDto>();
        for (Ship ship : ships) {
            resultDto.add(ShipDtoAssembler.assemble(ship, ship.getPort()));
        }
        GetAllShipsWithPortResponse result = new GetAllShipsWithPortResponse();
        result.setShipAtPortDtoList(resultDto);
        return result;
    }

    private GetAllPortsResponse handleGetAllPortsQuery(GetAllPorts query) {
        List<Port> ports = (List<Port>) portRepository.findAll();
        List<PortDto.PortDetailsDto> resultDto = new ArrayList<PortDto.PortDetailsDto>();
        for(Port port:ports) {
            resultDto.add(PortDtoAssembler.assemble(port));
        }
        GetAllPortsResponse result = new GetAllPortsResponse();
        result.setPortDetailsDtoList(resultDto);
        return result;
    }

}
