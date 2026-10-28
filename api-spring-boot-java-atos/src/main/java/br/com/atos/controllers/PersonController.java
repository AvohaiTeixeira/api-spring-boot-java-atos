package br.com.atos.controllers;

import br.com.atos.controllers.docs.PersonControllerDocs;
import br.com.atos.data.dto.PersonDTO;
import br.com.atos.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/person/v1")
public class PersonController implements PersonControllerDocs {

   @Autowired
   private PersonServices service;
   // private PersonServices service = new PersonServices();

   @GetMapping(produces = {
           MediaType.APPLICATION_JSON_VALUE,
           MediaType.APPLICATION_XML_VALUE,
           MediaType.APPLICATION_YAML_VALUE})
   public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
           @RequestParam(value = "page", defaultValue = "0") Integer page,
           @RequestParam(value = "size", defaultValue = "12") Integer size,
           @RequestParam(value = "direction", defaultValue = "asc") String direction

   ) {

      var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
      Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
      return ResponseEntity.ok(service.findAll(pageable));
   }


   //@CrossOrigin(origins = "http://localhost:8081")
   @GetMapping(value = "/{id}",
           produces = {
                   MediaType.APPLICATION_JSON_VALUE,
                   MediaType.APPLICATION_XML_VALUE,
                   MediaType.APPLICATION_YAML_VALUE}
   )
   public PersonDTO findById(@PathVariable("id") Long id) {
      return service.findById(id);
   }

   //@CrossOrigin(origins = {"http://localhost:8082", "http://www.erudio.com.br"})
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

   @PatchMapping(value = "/{id}",
           produces = {
                   MediaType.APPLICATION_JSON_VALUE,
                   MediaType.APPLICATION_XML_VALUE,
                   MediaType.APPLICATION_YAML_VALUE}
   )
   @Override
   public PersonDTO disablePerson(@PathVariable("id") Long id) {
      return service.disablePerson(id);
   }

   @DeleteMapping(value = "/{id}")
   public ResponseEntity<?> delete(@PathVariable("id") Long id) {
      service.delete(id);
      return ResponseEntity.noContent().build();
   }
}