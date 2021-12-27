package com.springexamples.demo.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "users", itemRelation = "user")
public class User {
  private @Id @GeneratedValue Long id;
  @NotBlank(message = "First name is mandatory")
  @Column(name = "first_name")
  private String firstName;
  @NotBlank(message = "Last name is mandatory")
  @Column(name = "last_name")
  @NotBlank(message = "Email is mandatory")
  private String lastName;
  private String email;

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof User))
      return false;
    User user = (User) o;
    return Objects.equals(this.id, user.id)
      && Objects.equals(this.firstName, user.firstName)
      && Objects.equals(this.lastName, user.lastName)
      && Objects.equals(this.email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.firstName, this.lastName, this.email);
  }
}
