package com.moj.digital.laa.repository.person;

import com.moj.digital.laa.entity.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    Person findByUfn(String ufn);
}
