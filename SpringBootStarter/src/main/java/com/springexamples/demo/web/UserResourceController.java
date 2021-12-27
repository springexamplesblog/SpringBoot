package com.springexamples.demo.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springexamples.demo.common.RecordNotFoundException;
import com.springexamples.demo.common.UserModelAssembler;
import com.springexamples.demo.data.UserRepository;
import com.springexamples.demo.entity.User;

import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/users")
@ApiResponses(value = {
    @io.swagger.annotations.ApiResponse(code = 400, message = "This is a bad request, please follow the API documentation for the proper request format"),
    @io.swagger.annotations.ApiResponse(code = 401, message = "Due to security constraints, your access request cannot be authorized"),
    @io.swagger.annotations.ApiResponse(code = 500, message = "The server is down. Please bear with us."), })
public class UserResourceController {
  @Autowired
  private UserRepository repository;
  @Autowired
  private UserModelAssembler assembler;

  @GetMapping
  public CollectionModel<EntityModel<User>> getAll() {
    List<EntityModel<User>> users = repository.findAll()
      .stream()
      .map(assembler::toModel)
      .collect(Collectors.toList());

    return CollectionModel.of(users,
      linkTo(methodOn(UserResourceController.class).getAll()).withSelfRel());
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody User newUser) {
    User createdUser = repository.save(newUser);

    EntityModel<User> userModel = assembler
      .toModel(repository.save(createdUser));

    return ResponseEntity
      .created(userModel.getRequiredLink(IanaLinkRelations.SELF)
        .toUri())
      .body(userModel);
  }

  @GetMapping("/{id}")
  public EntityModel<User> getById(@PathVariable Long id) {

    User user = repository.findById(id)
      .orElseThrow(() -> new RecordNotFoundException(id));

    return assembler.toModel(user);
  }

  @PutMapping("/{id}")
  public EntityModel<User> update(@Valid @RequestBody User newUser, @PathVariable Long id) {

    User updatedUser = repository.findById(id)
      .map(user -> {
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        return repository.save(newUser);
      })
      .orElseGet(() -> { newUser.setId(id); return repository.save(newUser); });
    
    EntityModel<User> entityModel = assembler.toModel(updatedUser);
    
    return entityModel;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
