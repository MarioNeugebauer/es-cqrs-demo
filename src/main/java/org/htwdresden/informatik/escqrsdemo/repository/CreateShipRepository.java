package org.htwdresden.informatik.escqrsdemo.repository;

import org.htwdresden.informatik.escqrsdemo.event.model.CreateShipCommand;
import org.htwdresden.informatik.escqrsdemo.model.Ship;
import org.springframework.data.repository.CrudRepository;

public interface CreateShipRepository extends CrudRepository<CreateShipCommand, Long> {
}
