package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void testaugmenterSalaireXPourcent() throws EmployeException {
        //Given
        Employe employe = new Employe("Doe", "John", "T12345", LocalDate.now(), 2000d, 1,1.0);
        Double pourcentage = 5d; // Augmenter salaire de 5%

        //When
        employe.augmenterSalaire(pourcentage);

        //Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(2100.0);
    }

    @Test
    public void testaugmenterSalaireNull() throws EmployeException {
        //Given
        Employe employe = new Employe("Doe", "John", "T12345", LocalDate.now(), null, 1,1.0);
        Double pourcentage = 10d;

        Assertions.assertThatThrownBy(() -> {
            //When
            employe.augmenterSalaire(pourcentage);
        })  //Then
            .isInstanceOf(EmployeException.class).hasMessage("Le salaire ne peut être null !");
    }

    @Test
    public void testaugmenterSalairePourcentNegatif() throws EmployeException {
        //Given
        Employe employe = new Employe("Doe", "John", "T12345", LocalDate.now(), 2000d, 1,1.0);
        Double pourcentage = -5d; // Diminuer salaire de 5%

        Assertions.assertThatThrownBy(() -> {
            //When
            employe.augmenterSalaire(pourcentage);
        })  //Then
            .isInstanceOf(EmployeException.class).hasMessage("On veut augmenter le salaire, pas le diminuer !");
    }




    @Test
    public void testNbAnneeAncienneteNow() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    public void testNbAnneeAncienneteNowMoins2() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(2);
    }

    @Test
    public void testNbAnneeAncienneteNowPlus3() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(3));

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    public void testNbAnneeAncienneteNull() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    public void testPrimeAnnuelleNull() {
        //Given
        Employe employe = new Employe();
        employe.setMatricule(null);

        //When
        Double matExist  = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(matExist).isNotNull();
    }

    @Test
    public void testPrimeAnnuelleUnitaire() {
        //Given
        Employe employe = new Employe();
        employe.setMatricule("C12345");
        employe.setTempsPartiel(1.0);
        employe.setDateEmbauche(LocalDate.now());
        employe.setPerformance(Entreprise.PERFORMANCE_BASE);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(prime).isEqualTo(1000d);
    }


    @ParameterizedTest
    @CsvSource({
            "'C12345', 1.0, 0, 1, 1000.0",
            "'M12345', 1.0, 0, 1, 1700.0",
            "'C12345', 1.0, 0, 2, 2300.0"
    })
    public void testGetPrimeAnnuelle(String matricule, Double tempsPartiel, Integer nbAnneeAnciennete, Integer performance, Double primeCalculee) {
        //Given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setTempsPartiel(tempsPartiel);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneeAnciennete));
        employe.setPerformance(performance);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(prime).isEqualTo(primeCalculee);
    }

}
