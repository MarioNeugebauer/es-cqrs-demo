package org.htwdresden.informatik.escqrsdemo.repository;

import org.htwdresden.informatik.escqrsdemo.event.DepartureEvent;
import org.springframework.data.repository.CrudRepository;

public interface DepartureEventRepository extends CrudRepository<DepartureEvent, Long> {
}
