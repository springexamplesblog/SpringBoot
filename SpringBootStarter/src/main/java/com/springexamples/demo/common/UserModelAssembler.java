package com.springexamples.demo.common;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.springexamples.demo.entity.User;
import com.springexamples.demo.web.UserResourceController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

  @Override
  public EntityModel<User> toModel(User user) {

    return EntityModel.of(user, //
        linkTo(methodOn(UserResourceController.class).getById(user.getId())).withSelfRel(),
        linkTo(methodOn(UserResourceController.class).getAll()).withRel("users"));
  }
}
