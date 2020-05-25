package org.htwdresden.informatik.escqrsdemo.eventbus;

import org.htwdresden.informatik.escqrsdemo.event.base.*;

public interface EventBus<R extends QueryResponse, C extends Command, S extends CommandSynchronous, Q extends Query, P extends IdResponse> {

    void processEvent(C command);

    P processEventSync(S commandSynchronous);

    R executeQuery(Q query);

}
