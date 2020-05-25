package org.htwdresden.informatik.escqrsdemo.eventhandler;

import org.htwdresden.informatik.escqrsdemo.event.base.Query;
import org.htwdresden.informatik.escqrsdemo.event.base.QueryResponse;

import java.util.List;

public interface QueryHandler<R extends QueryResponse, Q extends Query> {

    R executeQuery(Q query);

}
