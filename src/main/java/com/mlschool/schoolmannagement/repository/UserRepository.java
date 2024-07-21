package com.mlschool.schoolmannagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mlschool.schoolmannagement.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password ")
    User findByEmailAndPassword(String email,String password);
    
    User findByEmail(String email);
    
    User findByUsername(String username);
}
