package org.htwdresden.informatik.escqrsdemo.repository;

import org.htwdresden.informatik.escqrsdemo.event.model.CreatePortCommand;
import org.htwdresden.informatik.escqrsdemo.model.Port;
import org.springframework.data.repository.CrudRepository;

public interface CreatePortRepository extends CrudRepository<CreatePortCommand, Long> {
}
