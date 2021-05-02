package com.example.tabadol.repository;

import com.example.tabadol.model.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {

  UserApplication findByUsername(String username);

}
