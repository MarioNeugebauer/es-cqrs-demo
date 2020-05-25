package org.htwdresden.informatik.escqrsdemo;

import lombok.Getter;
import org.htwdresden.informatik.escqrsdemo.event.ArrivalEvent;
import org.htwdresden.informatik.escqrsdemo.event.base.Command;
import org.htwdresden.informatik.escqrsdemo.event.DepartureEvent;
import org.htwdresden.informatik.escqrsdemo.event.base.IdResponse;
import org.htwdresden.informatik.escqrsdemo.event.base.CommandSynchronous;
import org.htwdresden.informatik.escqrsdemo.eventbus.impl.SimpleEventBusImpl;
import org.htwdresden.informatik.escqrsdemo.eventhandler.CommandHandler;
import org.htwdresden.informatik.escqrsdemo.eventhandler.impl.SimpleQueryHandlerImpl;
import org.htwdresden.informatik.escqrsdemo.model.Country;
import org.htwdresden.informatik.escqrsdemo.model.Port;
import org.htwdresden.informatik.escqrsdemo.model.Ship;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

@SpringBootTest
public class EventBusTest {

    private static final Logger log = LoggerFactory.getLogger(SimpleEventBusImpl.class);

    @Test
    void testCommandRouting() {

        MockCommandHandler mockedCommandHandler = new MockCommandHandler();

        SimpleEventBusImpl simpleEventBus = new SimpleEventBusImpl(mockedCommandHandler, new SimpleQueryHandlerImpl());
        simpleEventBus.start();

        LocalDateTime LocalDateTime1 = LocalDateTime.now();
        LocalDateTime LocalDateTime2 = LocalDateTime1.plusDays(1);
        Port hamburg = new Port("Hamburg", Country.DE);
        Ship alexandra = new Ship("Alexandra");
        ArrivalEvent arrivalEvent = new ArrivalEvent(LocalDateTime1, hamburg, alexandra);
        DepartureEvent departureEvent = new DepartureEvent(LocalDateTime2, hamburg, alexandra);

        log.debug("test setup done.");

        simpleEventBus.processEvent(arrivalEvent);
        simpleEventBus.processEvent(departureEvent);

        try {
            log.debug("size = "+mockedCommandHandler.commandQueue.size());
            Assert.isTrue(mockedCommandHandler.commandQueue.size()==2, "there must be 2 events in the queue");
            ArrivalEvent eventOut1 = (ArrivalEvent)mockedCommandHandler.getCommandQueue().poll();
            Assert.isTrue(eventOut1.equals(arrivalEvent), "in-arrival-event must be equal first out event");
            DepartureEvent eventOut2 = (DepartureEvent)mockedCommandHandler.getCommandQueue().poll();
            Assert.isTrue(eventOut2.equals(departureEvent), "in-departure-event must be equal second out event");
        } catch (ClassCastException cce) {
            log.debug(cce.getMessage());
        }
    }



    private class MockCommandHandler implements CommandHandler {
        private final Logger log = LoggerFactory.getLogger(MockCommandHandler.class);

        @Getter
        private Queue<Command> commandQueue = new LinkedList<Command>();

        @Override
        public void processCommandAsync(Command command) {
            commandQueue.add(command);
        }

        @Override
        public IdResponse processCommandSync(CommandSynchronous commandSynchronous) {
            // TODO
            return null;
        }
    }

}
