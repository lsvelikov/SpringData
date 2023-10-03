package com.example.mappingexe.repository;

import com.example.mappingexe.model.entity.Game;
import com.example.mappingexe.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);

    @Query("SELECT u.games from User u where u.id = :id")
    List<Game> findAllByUser(Long id);
}
