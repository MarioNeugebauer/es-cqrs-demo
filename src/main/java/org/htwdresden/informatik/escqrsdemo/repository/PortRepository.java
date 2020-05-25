package org.htwdresden.informatik.escqrsdemo.repository;

import org.htwdresden.informatik.escqrsdemo.model.Country;
import org.htwdresden.informatik.escqrsdemo.model.Port;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PortRepository extends CrudRepository<Port, Long> {

    List<Port> findByNameAndCountry(String name, Country country);

}
