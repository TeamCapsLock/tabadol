package com.example.tabadol.repository;

import com.example.tabadol.model.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {

  UserApplication findByUsername(String username);
  @Query(value = "SELECT user_id FROM followers  WHERE following_user = ?1", nativeQuery = true)
  List<Long> findAllByFollowing_user(long userID);

}
