package com.prova.repository;

import com.prova.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>
{
    // Con Optional mi restituisce o l'oggetto User se viene trovato oppure, nel caso contrario, null
    public Optional<User> findByUsername(String username);
}
