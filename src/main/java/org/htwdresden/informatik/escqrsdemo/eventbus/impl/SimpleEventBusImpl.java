package org.htwdresden.informatik.escqrsdemo.eventbus.impl;

import org.htwdresden.informatik.escqrsdemo.event.base.*;
import org.htwdresden.informatik.escqrsdemo.eventbus.EventBus;
import org.htwdresden.informatik.escqrsdemo.eventhandler.CommandHandler;
import org.htwdresden.informatik.escqrsdemo.eventhandler.QueryHandler;
import org.htwdresden.informatik.escqrsdemo.eventhandler.impl.SimpleCommandHandlerImpl;
import org.htwdresden.informatik.escqrsdemo.eventhandler.impl.SimpleQueryHandlerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class SimpleEventBusImpl<R extends QueryResponse> implements EventBus {

    private static final Logger log = LoggerFactory.getLogger(SimpleEventBusImpl.class);

    @Autowired
    private SimpleCommandHandlerImpl commandHandler;

    @Autowired
    private SimpleQueryHandlerImpl queryHandler;

    private static BlockingQueue<Command> commandQueue = new LinkedBlockingQueue<Command>();

    private CommandProcessor commandProcessor;

    public SimpleEventBusImpl() {
        log.debug("... SimpleEventBusImpl was initialized with no-args-constructor.");
    }

    public SimpleEventBusImpl(CommandHandler commandHandler, QueryHandler queryHandler) {
        this.commandHandler = (SimpleCommandHandlerImpl) commandHandler;
        this.queryHandler = (SimpleQueryHandlerImpl)queryHandler;
        start();
        log.debug("... SimpleEventBusImpl was initialized with constructor.");
    }

    public void start() {
        if(commandHandler!=null && queryHandler!=null&&commandProcessor==null) {
            commandProcessor  = new CommandProcessor();
            commandProcessor.start();
            log.debug("... CommandProcessor was started.");
            return;
        } else if(commandHandler!=null && queryHandler!=null&&commandProcessor!=null) {
            if(!commandProcessor.isAlive()) {
                commandProcessor.start();
                log.debug("... CommandProcessor was started.");
                return;
            } else {
                log.debug("... CommandProcessor is already running.");
                return;
            }
        }
        log.debug("CommandProcessor cannot be started yet.");
    }

    @Override
    public void processEvent(Command command) {
        commandQueue.add(command);
        log.debug("command "+ command.toString()+" added to commandQueue");
    }

    @Override
    public IdResponse processEventSync(CommandSynchronous command) {
        return commandHandler.processCommandSync(command);
    }

    @Override
    public QueryResponse executeQuery(Query query) {
        return queryHandler.executeQuery(query);
    }

    private class CommandProcessor extends Thread {
        private final Logger log = LoggerFactory.getLogger(CommandProcessor.class);

        @Override
        public void run() {
            log.debug("CommandProcessor started.");
            if(commandQueue ==null) {
                log.error("commandQueue must not be NULL. Cancelling CommandProcessor.");
            }

            for (;;) {
                Command command = null;
                try {
                    command = commandQueue.take();
                    log.debug("pushing: " + command.hashCode() + " to commandHandler ...");
                    commandHandler.processCommandAsync(command);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public boolean isBusy() {
        return commandQueue.isEmpty() ? false : true;
    }

}
