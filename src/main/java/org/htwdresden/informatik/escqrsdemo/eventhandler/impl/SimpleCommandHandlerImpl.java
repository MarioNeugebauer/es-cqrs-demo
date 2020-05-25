package org.htwdresden.informatik.escqrsdemo.eventhandler.impl;

import org.htwdresden.informatik.escqrsdemo.event.*;
import org.htwdresden.informatik.escqrsdemo.event.base.Command;
import org.htwdresden.informatik.escqrsdemo.event.base.IdResponse;
import org.htwdresden.informatik.escqrsdemo.event.base.CommandSynchronous;
import org.htwdresden.informatik.escqrsdemo.event.model.CreatePortCommand;
import org.htwdresden.informatik.escqrsdemo.event.model.CreateShipCommand;
import org.htwdresden.informatik.escqrsdemo.eventhandler.CommandHandler;
import org.htwdresden.informatik.escqrsdemo.model.Port;
import org.htwdresden.informatik.escqrsdemo.model.Ship;
import org.htwdresden.informatik.escqrsdemo.repository.*;
import org.htwdresden.informatik.escqrsdemo.services.PortDtoAssembler;
import org.htwdresden.informatik.escqrsdemo.services.ShipDtoAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class SimpleCommandHandlerImpl implements CommandHandler {

    private static final Logger log = LoggerFactory.getLogger(SimpleCommandHandlerImpl.class);

    @Autowired
    private ArrivalEventRepository arrivalEventRepository;
    @Autowired
    private DepartureEventRepository departureEventRepository;
    @Autowired
    private CreateShipRepository createShipRepository;
    @Autowired
    private CreatePortRepository createPortRepository;

    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private PortRepository portRepository;

    @Override
    public void processCommandAsync(Command command) {
        if(command instanceof ArrivalEvent) {
            arrivalEventRepository.save((ArrivalEvent) command);
            updateSnapshot((ArrivalEvent) command);
            log.debug("arrivalEvent "+((ArrivalEvent) command).getId()+" was processed.");
        } else if(command instanceof DepartureEvent) {
            departureEventRepository.save((DepartureEvent) command);
            updateSnapshot((DepartureEvent) command);
            log.debug("departureEvent "+((DepartureEvent) command).getId());
        } else {
            log.error("commandType "+ command.getClass()+" not specified in async handler");
        }
    }

    @Override
    public IdResponse processCommandSync(CommandSynchronous command) {
        if(command instanceof CreateShipCommand) {
            return handleCreateShipCommand(command);
        } else if(command instanceof CreatePortCommand) {
            return handleCreatePortCommand(command);
        } else {
            log.error("commandType "+ command.getClass()+" not specified in sync handler");
        }
        return null;
    }

    private void updateSnapshot(ArrivalEvent arrivalEvent) {
        Optional<Port> portOptional = portRepository.findById(arrivalEvent.getPort().getId());
        if(portOptional.isEmpty()) {
            log.error("The port "+arrivalEvent.getPort().toString()+" referenced in ArrivalEvent does not exist.");
        }
        Ship ship = arrivalEvent.getShip();
        ship.setPort(arrivalEvent.getPort());
        shipRepository.save(ship);
    }

    private void updateSnapshot(DepartureEvent departureEvent) {
        Optional<Port> portOptional = portRepository.findById(departureEvent.getPort().getId());
        if(portOptional.isEmpty()) {
            log.error("The port "+departureEvent.getPort().toString()+" referenced in DepartureEvent does not exist.");
        }
        Ship ship = departureEvent.getShip();
        ship.setPort(null);
        shipRepository.save(ship);
    }


    private IdResponse handleCreatePortCommand(Command command) {
        Port port = ((CreatePortCommand) command).getPort();
        if(port==null) {
            log.error("no port in CreatePortCommand, should be provided!");
            return null;
        }
        List<Port> existingPortsWithNameAndCountry = portRepository.findByNameAndCountry(port.getName(), port.getCountry());
        if (existingPortsWithNameAndCountry.size()>0) {
            log.error("Port with Name "+port.getName()+" in country "+port.getCountry()+" already exists with ID "+port.getId());
            return null;
        }
        // persist in event store
        Port createdPort = portRepository.save(port);

        // update model
        createPortRepository.save((CreatePortCommand)command);
        log.debug("Created Port with ID "+createdPort.getId());

        return PortDtoAssembler.assemble(createdPort.getId());
    }

    private IdResponse handleCreateShipCommand(Command command) {
        Ship ship = ((CreateShipCommand) command).getShip();
        if(ship==null) {
            log.error("no ship in CreateShipCommand, should be provided!");
            return null;
        }
        List<Ship> existingShipsWithName = shipRepository.findByName(ship.getName());
        if(existingShipsWithName.size()==1) {
            log.error("Ship with Name "+ship.getName()+" already exists with ID "+existingShipsWithName.get(0).getId());
            return null;
        } else if(existingShipsWithName.size()>0) {
            log.error(existingShipsWithName.size()+" Ships with name "+ship.getName()+" already exist");
            return null;
        }
        // persist in event store
        Ship createdShip = shipRepository.save(ship);

        // update model
        createShipRepository.save((CreateShipCommand)command);
        log.debug("Created Ship with ID "+createdShip.getId());

        return ShipDtoAssembler.assemble(createdShip.getId());
    }


}
