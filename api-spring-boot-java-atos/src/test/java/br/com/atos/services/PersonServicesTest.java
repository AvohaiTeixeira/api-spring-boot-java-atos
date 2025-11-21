package br.com.atos.services;

import br.com.atos.data.dto.PersonDTO;
import br.com.atos.exception.RequiredObjectIsNullException;
import br.com.atos.model.Person;
import br.com.atos.repository.PersonRepository;
import br.com.atos.unitTests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;
    @InjectMocks
    PersonServices services;
    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));

        var result = services.findById(1L);

        this.validateResultRequest(result, 1);

    }

    @Test
    void create() {
        Person person = input.mockEntity(1);
        Person persisted = person;

        person.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(repository.save(person)).thenReturn(persisted);

        var result = services.create(dto);

        this.validateResultRequest(result, 1);
    }

    @Test
    void testeCreateWhithNullPerson() {
        Exception e = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                services.create(null);
                });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Person person = input.mockEntity(1);
        Person persisted = person;

        person.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(persisted);

        var result = services.update(dto);

        this.validateResultRequest(result, 1);
    }

    @Test
    void testeUpdateWhithNullPerson() {
        Exception e = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    services.update(null);
                });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));

        services.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        //Mocka um List de Person
        List<Person> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);

        //Simula a listagem no banco
        List<PersonDTO> personList = services.findAll();

        //Valida se o retorno é um List nullo
        assertNotNull(personList);

        //Valida se o List retornado tem um tamanho de 14 psições
        assertEquals(14, personList.size());

        //Escolhe o primeiro registro da lista para realizar os testes
        PersonDTO result1 = personList.get(1);

        //Valida o resultado do objeto retornado na posição 1 da Lista
        validateResultRequest(result1, 1);

        //Escolhe o quarto registro da lista para realizar os testes
        PersonDTO result4 = personList.get(4);

        //Valida o resultado do objeto retornado na posição 4 da Lista
        validateResultRequest(result4, 4);

        //Escolhe setimo registro da lista para realizar os testes
        PersonDTO result7 = personList.get(7);

        //Valida o resultado do objeto retornado na posição 7 da Lista
        validateResultRequest(result7, 7);

    }

    private void validateResultRequest(PersonDTO result, Integer nrReqistro) {

        //Caso não seja especificado o númewro di registro, o padrão será 1
        if (nrReqistro == null ) {
            nrReqistro = 1;
        }

        String hRef = "/api/person/v1";
        String gender = "";

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());


        String hRefFindById = hRef + "/" + nrReqistro;

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith(hRefFindById)
                        && link.getType().equals("GET"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith(hRef)
                        && link.getType().equals("GET"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith(hRef)
                        && link.getType().equals("POST"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith(hRef)
                        && link.getType().equals("PUT"))
        );

        String hRefDelete = hRef + "/" + nrReqistro;
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith(hRefDelete)
                        && link.getType().equals("DELETE"))
        );

        String address = "Address Test" + nrReqistro.toString();
        String firstName = "First Name Test" + nrReqistro.toString();
        String lastName = "Last Name Test" + nrReqistro.toString();

        if (nrReqistro % 2 == 0) {
            gender = "Male";
        } else {
            gender = "Female";
        }

        assertEquals(address, result.getAddress());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(gender, result.getGender());


    }
}