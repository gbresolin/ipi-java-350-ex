package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;

    @Mock
    EmployeRepository employeRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this.getClass());
    }

    @Test
    void embaucheEmploye0Employe() throws EmployeException {
        //Given
        //Quand la méthode findLastmatricule va être appelée, on veut qu'elle renvoie null
        //pour simuler une base employé vide
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);

        //Quand on va chercher si l'employé avec le matricule calculé existe, on veut que la méthode
        //envoie null
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);

        //When
        employeService.embaucheEmploye("Doe", "john", Poste.COMMERCIAL, NiveauEtude.LICENCE, 1.0);

        //Then
    }

    @Test
    void embaucheEmployeXEmployes() throws EmployeException {
        //Given
        //Quand la méthode findLastmatricule va être appelée, on veut qu'elle renvoie une valeur comme s'il
        //y avait plusieurs employés dont le matricule le plus élévé est C45678
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("45678");

        //Quand on va chercher si l'employé avec le matricule calculé existe, on veut que la méthode
        //envoie null
        //Mockito.when(employeRepository.findByMatricule("M45679")).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule(Mockito.anyString())).thenReturn(null);
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        //Employe employe = employeRepository.findByMatricule("M456789");
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        //On vérifie que la méthode save a bien été appelée sur employeRepository, et on capture le paramètre
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("M45679");
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        //1521.22 * 1.2 * 1.0 = 1825.46

        //Vérifications...
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
    }

    @Test
    void testEmbaucheEmployeLimiteMatricule(){

    }


    void testExceptionNormal(){
        //Given
        try {
            //When
            employeService.embaucheEmploye(null, null, null, null, null);
            Assertions.fail("Aurait du lancer une exception");
        } catch (Exception e){
            //Then
            //Vérifie que l'exception levée est de type EmployeException
            Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            //Vérifie le contenu du message
            Assertions.assertThat(e.getMessage()).isEqualTo("Message de l'erreur");
        }
    }

    void testExceptionJava8(){
        //Given
        Assertions.assertThatThrownBy(() -> {
            //When
            employeService.embaucheEmploye(null, null, null, null, null);
        })//Then
                .isInstanceOf(EmployeException.class).hasMessage("Message de l'erreur");
    }


}