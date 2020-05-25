package org.htwdresden.informatik.escqrsdemo.eventhandler;

import org.htwdresden.informatik.escqrsdemo.event.base.Command;
import org.htwdresden.informatik.escqrsdemo.event.base.IdResponse;
import org.htwdresden.informatik.escqrsdemo.event.base.CommandSynchronous;

public interface CommandHandler<C extends Command, S extends CommandSynchronous, P extends IdResponse> {

    void processCommandAsync(C command);

    P processCommandSync(S commandSynchronous);

}
