package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public static class Marche {
		private Etal[] etals;
		
		public void initiateEtal(int nbEtal) {
			this.etals= new Etal[nbEtal];
		}
	    public Marche(int nombreEtals) {
	        etals = new Etal[nombreEtals];
	        for (int i = 0; i < nombreEtals; i++) {
	            etals[i] = new Etal();
	        }
	    }
		
	    public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
	        if (indiceEtal >= 0 && indiceEtal < etals.length) {
	            etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
	        } else {
	            System.out.println("L'indice de l'étal est invalide.");
	        }
	    }
	    
	    public int trouverEtalLibre() {
	        for (int i = 0; i < etals.length; i++) {
	            if (!etals[i].isEtalOccupe()) {
	                return i; // Retourne l'indice de l'étal libre
	            }
	        }
	        return -1; // Aucun étal libre trouvé
	    }
	    
	    public Etal[] trouverEtals(String produit) {
	    	int count =0;
	    	for(Etal etal : etals) {
	    		if(etal.contientProduit(produit)) {
	    			count ++;
	    		}
	    	}
	    	Etal[] etalsVendantProduit = new Etal[count];
	    	count=0;
	    	for(Etal etal : etals) {
	    		if(etal.contientProduit(produit)) {
	    			etalsVendantProduit[count++]= etal;
	    		}
	    	}
	    	return etalsVendantProduit;

	    }
	    
	    public Etal trouverVendeur(Gaulois gaulois) {
	        for (Etal etal : etals) {
	            if (etal.isEtalOccupe() && etal.getVendeur().equals(gaulois)) {
	                return etal;
	            }
	        }
	        return null;
	    }
	    
	    public String afficherMarche() {
	        StringBuilder result = new StringBuilder();
	        int nbEtalVide = 0;

	        for (Etal etal : etals) {
	            if (etal.isEtalOccupe()) {
	                result.append(etal.afficherEtal()); // Ajouter l'affichage de l'étal occupé
	            } else {
	                nbEtalVide++; // Compter les étals vides
	            }
	        }
	        
	        if (nbEtalVide > 0) {
	            result.append("Il reste ").append(nbEtalVide).append(" étals non utilisés dans le marché.\n");
	        }

	        return result.toString();
	    }
	}
	
}