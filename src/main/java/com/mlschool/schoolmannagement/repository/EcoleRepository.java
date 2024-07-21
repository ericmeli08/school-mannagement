package com.mlschool.schoolmannagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mlschool.schoolmannagement.model.ecole.Ecole;

@Repository
public interface EcoleRepository extends JpaRepository<Ecole,Integer> {

}
