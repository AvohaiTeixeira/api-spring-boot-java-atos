package br.com.atos.controllers;

import br.com.atos.data.dto.PersonDTO;
import br.com.atos.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

   @Autowired
   private PersonServices service;
   // private PersonServices service = new PersonServices();

   @GetMapping(produces = {
           MediaType.APPLICATION_JSON_VALUE,
           MediaType.APPLICATION_XML_VALUE,
           MediaType.APPLICATION_YAML_VALUE})
   public List<PersonDTO> findAll() {
      return service.findAll();
   }


   @CrossOrigin(origins = "http://localhost:8081")
   @GetMapping(value = "/{id}",
           produces = {
                   MediaType.APPLICATION_JSON_VALUE,
                   MediaType.APPLICATION_XML_VALUE,
                   MediaType.APPLICATION_YAML_VALUE}
   )
   public PersonDTO findById(@PathVariable("id") Long id) {
      return service.findById(id);
   }

   @CrossOrigin(origins = {"http://localhost:8082", "http://www.erudio.com.br"})
   @PostMapping(
           consumes = {
                   MediaType.APPLICATION_JSON_VALUE,
                   MediaType.APPLICATION_XML_VALUE,
                   MediaType.APPLICATION_YAML_VALUE},
           produces = {
                   MediaType.APPLICATION_JSON_VALUE,
                   MediaType.APPLICATION_XML_VALUE,
                   MediaType.APPLICATION_YAML_VALUE}
   )
   public PersonDTO create(@RequestBody PersonDTO person) {
      return service.create(person);
   }

   @PutMapping(
           consumes = {
                   MediaType.APPLICATION_JSON_VALUE,
                   MediaType.APPLICATION_XML_VALUE,
                   MediaType.APPLICATION_YAML_VALUE},
           produces = {
                   MediaType.APPLICATION_JSON_VALUE,
                   MediaType.APPLICATION_XML_VALUE,
                   MediaType.APPLICATION_YAML_VALUE}
   )
   public PersonDTO update(@RequestBody PersonDTO person) {
      return service.update(person);
   }


   @DeleteMapping(value = "/{id}")
   public ResponseEntity<?> delete(@PathVariable("id") Long id) {
      service.delete(id);
      return ResponseEntity.noContent().build();
   }
}