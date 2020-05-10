/**
 * 
 */
package com.aboubakar.metier;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aboubakar.dao.CompteRepository;
import com.aboubakar.dao.OperationRepository;
import com.aboubakar.entities.Compte;
import com.aboubakar.entities.CompteCourant;
import com.aboubakar.entities.Operation;
import com.aboubakar.entities.Retrait;
import com.aboubakar.entities.Versement;

/**
 * @author Aboubakar
 *
 */

/*================================================================================
 * 
 * Dans cette classe, nous implementons les méthodes de l'interface IBanqueMetier
 * qui présentent le code meter de l'application à savoir :
 * 
 * 1. Consulter un compte
 * 2. Effectuer un versemnt
 * 3. Effectuer un retrait
 * 4. Effectuer un virement d'un compte à un autre. 
 * 5. Consulter la liste des opérations d'un compte. 
 * 
 */

// Pour que Spring instancie cette classe au démarrage on utilise l'annotation @Service
// Pour rendre transactional les méthodes de cette classe on utilise l'annotation @Transactional

@Service
@Transactional
public class BanqueMetierImpl implements IBanqueMetier {

	// On injecte cette objet dans cette classe à l'aide de l'annotation @Autowired
	
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	
	@Override
	public Compte consulterCompte(String codeCompte) {
		Optional<Compte> compte = compteRepository.findById(codeCompte);
		if(compte==null) {
			throw new RuntimeException("Le compte que vous avez saisi est introuvable.");
		}
		return compte.get();
	}

	/*
	 * Pour effectuer un versement on dot d'abord consulter le compte
	 * Puis réaliser l'opération l'opération
	 * Ensuite mettre à jour le solde
	 */
	@Override
	public void verser(String codeCompte, double montant) {
		Compte compte = compteRepository.getOne(codeCompte);
		Versement versement = new Versement(new Date(), montant, compte);
		operationRepository.save(versement);
		compte.setSolde(compte.getSolde() + montant);
		compteRepository.save(compte);
		
	}

	/*
	 * Pour effectuer un retrait on doit verifier le type de compte.
	 * Si c'est un compte courant, on doit s'assurer que le motant à retirer
	 * ne depasse pas le solde présent dans le compte plus la facilte de caisse.
	 */
	@Override
	public void retirer(String codeCompte, double montant) {
		
		Compte compte = compteRepository.getOne(codeCompte);
		double faciliteCaisse = 0;
		if(compte instanceof CompteCourant) {
			faciliteCaisse = ((CompteCourant) compte).getDecouvert();
		}
		if(faciliteCaisse + compte.getSolde()<montant) {
			throw new RuntimeException("Votre sold est insuffisant pour effectuer cette opération");
		}
		Retrait retrait = new Retrait(new Date(), montant, compte);
		operationRepository.save(retrait);
		compte.setSolde(compte.getSolde() - montant);
		compteRepository.save(compte);
		
	}

	// Un virement consiste à retier un montant d'un compte et le verser dans un autre compte
	@Override
	public void virement(String codeCompte1, String codeCompte2, double montant) {
		if(codeCompte1.equals(codeCompte2)) {
			throw new RuntimeException("Le virement d'un compte vers ce même compte n'est pas autorisé!");
		}
		retirer(codeCompte1,montant);
		verser(codeCompte2,montant);
	}

	@Override
	public Page<Operation> listOperation(String codeCompte, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		return operationRepository.listOperation(codeCompte, paging) ;
	}

}
