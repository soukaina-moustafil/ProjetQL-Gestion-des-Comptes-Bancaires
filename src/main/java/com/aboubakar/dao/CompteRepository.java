package com.aboubakar.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aboubakar.entities.Compte;

public interface CompteRepository extends JpaRepository<Compte, String> {

}
