package com.evojam.time4jokes;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Comedian {

    @Id
    @GeneratedValue
    Long id;

    String name;

    // LocalDate retired;
    //
    // Comedian retire() {
    //     this.retired = LocalDate.now();
    //     return this;
    // }
    //
    // TODO
}

interface ComedianRepository extends JpaRepository<Comedian, Long> {

    Comedian findByName(String name);
}