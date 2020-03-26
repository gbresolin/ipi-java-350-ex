package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    //@AfterEach
    void setUp(){
        employeRepository.deleteAll();
    }


    @Test
    void findLastMatricule0Employe() {
        //Given

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isNull();
    }

    @Test
    void findLastMatricule3Employes() {
        //Given
        Employe e1 = new Employe("Doe", "John", "T12345", LocalDate.now(), 2000d, 1,1.0);
        Employe e2 = new Employe("Doe", "Jane", "C67890", LocalDate.now(), 2000d, 1,1.0);
        Employe e3 = new Employe("Doe", "John", "M45678", LocalDate.now(), 2000d, 1,1.0);

        employeRepository.save(e1);
        employeRepository.save(e2);
        employeRepository.save(e3);

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("67890");
    }

    @Test
    void avgPerformanceWhereMatriculeStartsWith() {
    }
}