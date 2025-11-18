package br.com.atos.repository;

import br.com.atos.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long > {
}
