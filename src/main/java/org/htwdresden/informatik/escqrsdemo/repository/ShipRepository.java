package org.htwdresden.informatik.escqrsdemo.repository;

import org.htwdresden.informatik.escqrsdemo.model.Ship;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShipRepository extends CrudRepository<Ship, Long> {

    List<Ship> findByName(String name);

}
