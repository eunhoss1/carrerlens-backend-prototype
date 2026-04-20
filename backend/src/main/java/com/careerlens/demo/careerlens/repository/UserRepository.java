package com.careerlens.demo.careerlens.repository;

import com.careerlens.demo.careerlens.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
}
