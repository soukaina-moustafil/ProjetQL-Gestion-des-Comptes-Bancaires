package com.banque.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.banque.metier.IBanqueMetier;

@SpringBootTest
class testCompte {
	
	@Autowired
	IBanqueMetier metier;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	
	
	@Test
	void testVirementNonR() {
		
		
		Throwable e = null;
		
		try {
			metier.virement("12", "12", 200);
			}
			catch (Throwable ex) {
			e = ex;
			}
			
			assertTrue(e instanceof RuntimeException);
		
	}
	
	@Test
	void testVirement() {
		
		metier.virement("123", "12", 200);
		assertTrue(metier.consulterCompte("12").getSolde() == 856.0,"Succes ");
		assertTrue(metier.consulterCompte("123").getSolde() == 256.0,"Succes ");
				
	}
	
	
	@Test
	void testConsulterCompte()  
	{
		
	
		
		assertEquals(456.0,metier.consulterCompte("123").getSolde(),"Succes ");
		
	}
	
	@Test
	void testVerser() {
		metier.verser("12", 200);
		assertEquals(1056.0,metier.consulterCompte("12").getSolde(),"Succes ");
	}
	
	@Test
	void testRetirer() {
		metier.retirer("12", 200);
		
		if(metier.consulterCompte("12").getSolde() !=656.0)
		  fail("Problem dans la fanction");
	}
	
}
