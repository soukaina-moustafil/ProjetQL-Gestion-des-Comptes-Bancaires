/**
 * 
 */
package com.aboubakar.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aboubakar.entities.Client;

/**
 * @author Aboubakar
 *
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

}
