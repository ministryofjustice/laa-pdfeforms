package com.moj.digital.laa.repository.client.registration;

import com.moj.digital.laa.entity.client.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRegistrationRepository extends JpaRepository<Person,Long> {
    Person findByUfn(String ufn);
    List<Person> findByUfnContaining(String ufn);
}
