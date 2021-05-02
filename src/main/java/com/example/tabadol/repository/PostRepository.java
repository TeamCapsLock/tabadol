package com.example.tabadol.repository;

import com.example.tabadol.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long>{
    @Query(value = "SELECT * FROM post where user_id = ?1", nativeQuery = true)
    List<Post> findByUserId(Long id);

}
