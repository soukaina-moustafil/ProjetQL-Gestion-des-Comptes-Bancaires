/**
 * 
 */
package com.aboubakar.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aboubakar.entities.Compte;
import com.aboubakar.entities.Operation;
import com.aboubakar.metier.IBanqueMetier;

/**
 * @author Aboubakar
 *
 */

@Controller
public class BanqueController {

	@Autowired
	private IBanqueMetier banqueMetier;
	
	@RequestMapping(value= {"/","index"},method=RequestMethod.GET)
	public String index() {
		
		return "comptes";
	}
	
	@RequestMapping(value="/consulterCompte")
	public String consulter(Model model, String codeCompte,
			@RequestParam(name="page", defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="5")int size) {
		// On appelle la couche metier pour consulter un compte
		try {
			Compte compte = banqueMetier.consulterCompte(codeCompte);
			model.addAttribute("compte", compte);
			
			Page<Operation> pageOperations = banqueMetier.listOperation(codeCompte, page, size);
			model.addAttribute("listOperations", pageOperations.getContent());
			int[] pages = new int[pageOperations.getTotalPages()];
			model.addAttribute("pages",pages);
			model.addAttribute("pageCourante", page);
			
		}catch(Exception e) {
			model.addAttribute("exception", e);
		}
		
		return "comptes";
	}
	
	@RequestMapping(value="/saveOperation",method=RequestMethod.POST)
	public String saveOperation(Model model,String typeOperation,String codeCompte,double montant,String codeCompte2) {
		try {
			if(typeOperation.equals("versement")) {
				banqueMetier.verser(codeCompte, montant);
			}
			else if(typeOperation.equals("retrait")) {
				banqueMetier.retirer(codeCompte, montant);
			}
			else { // Autrement il s'agit d'un virement. 
				banqueMetier.virement(codeCompte, codeCompte2, montant);
			}
		}catch(Exception e) {
			model.addAttribute("error", e);
			return "redirect:/consulterCompte?codeCompte="+codeCompte+"&error="+e.getMessage();
		}
		
		return "redirect:/consulterCompte?codeCompte="+codeCompte;
	}
}
