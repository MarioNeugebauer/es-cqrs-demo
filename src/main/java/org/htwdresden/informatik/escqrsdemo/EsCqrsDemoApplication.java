package org.htwdresden.informatik.escqrsdemo;

import org.htwdresden.informatik.escqrsdemo.event.ArrivalEvent;
import org.htwdresden.informatik.escqrsdemo.event.DepartureEvent;
import org.htwdresden.informatik.escqrsdemo.event.model.CreatePortCommand;
import org.htwdresden.informatik.escqrsdemo.event.model.CreateShipCommand;
import org.htwdresden.informatik.escqrsdemo.event.query.*;
import org.htwdresden.informatik.escqrsdemo.eventbus.impl.SimpleEventBusImpl;
import org.htwdresden.informatik.escqrsdemo.eventhandler.impl.SimpleCommandHandlerImpl;
import org.htwdresden.informatik.escqrsdemo.eventhandler.impl.SimpleQueryHandlerImpl;
import org.htwdresden.informatik.escqrsdemo.model.Country;
import org.htwdresden.informatik.escqrsdemo.model.Port;
import org.htwdresden.informatik.escqrsdemo.model.Ship;
import org.htwdresden.informatik.escqrsdemo.repository.*;
import org.htwdresden.informatik.escqrsdemo.services.PortDto;
import org.htwdresden.informatik.escqrsdemo.services.PortDtoAssembler;
import org.htwdresden.informatik.escqrsdemo.services.ShipDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class EsCqrsDemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EsCqrsDemoApplication.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger(EsCqrsDemoApplication.class);

	@Autowired
	private SimpleEventBusImpl eventBus;

	@Override
	public void run(String... args) throws Exception {
		eventBus.start();

		// create some example objects
		Port hamburg = new Port("Hamburg", Country.DE);
		Port rotterdam = new Port("Rotterdam", Country.NL);
		Port london = new Port("London", Country.UK);
		Ship alexandra = new Ship("Alexandra");

		// persist port and ship
		PortDto.PortIdDto hamburgIdDto = (PortDto.PortIdDto) eventBus.processEventSync(new CreatePortCommand(LocalDateTime.now(), hamburg));
		log.debug("port created: "+hamburgIdDto.toString());
		PortDto.PortIdDto rotterdamIdDto = (PortDto.PortIdDto) eventBus.processEventSync(new CreatePortCommand(LocalDateTime.now(), rotterdam));
		log.debug("port created: "+rotterdamIdDto.toString());
		PortDto.PortIdDto londonIdDto = (PortDto.PortIdDto) eventBus.processEventSync(new CreatePortCommand(LocalDateTime.now(), london));
		log.debug("port created: "+londonIdDto.toString());
		ShipDto.ShipIdDto alexandraIdDto = (ShipDto.ShipIdDto) eventBus.processEventSync(new CreateShipCommand(LocalDateTime.now(), alexandra));
		log.debug("ship created: "+alexandraIdDto.toString());

		LocalDateTime date_20200101 = LocalDateTime.of(2020,1,1,10,0,0);
		LocalDateTime date_20200103 = LocalDateTime.of(2020,1,3,10,0,0);
		LocalDateTime date_20200110 = LocalDateTime.of(2020,1,10,10,0,0);

		eventBus.processEvent(new ArrivalEvent(date_20200101, hamburg, alexandra));
		showSnapshotState();

		eventBus.processEvent(new DepartureEvent(date_20200103, hamburg, alexandra));
		showSnapshotState();

		eventBus.processEvent(new ArrivalEvent(date_20200110, rotterdam, alexandra));
		showSnapshotState();

		// TODO create events for some more movements



	}

	private void logAllShipsWithPorts() {
		List<ShipDto.ShipAtPortDto> ships = ((GetAllShipsWithPortResponse)eventBus.executeQuery(new GetAllShipsWithPort(LocalDateTime.now()))).getShipAtPortDtoList();
		for (ShipDto.ShipAtPortDto shipAtPortDto: ships) {
			log.debug(shipAtPortDto.toString());
		}
	}

	private void waitForProcessing() throws InterruptedException {
		Object o = new Object();
		synchronized (o) {
			// wait until all events where processed
			o.wait(2000);
		}
	}

	private void showSnapshotState() throws InterruptedException {
		log.debug("============= snapshot at time "+ LocalDateTime.now());
		waitForProcessing();
		logAllShipsWithPorts();
	}

	// TODO implement GetShipsInPort GetAllPorts GetAllShips

}


