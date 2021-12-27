package com.springexamples.demo.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springexamples.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
