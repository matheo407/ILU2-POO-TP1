package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMarche) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtalsMarche);
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
	
    
    public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
        int etalLibre = marche.trouverEtalLibre();
        if (etalLibre != -1) {
            marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
            return vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ". Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (etalLibre + 1) + ".\n";
        } else {
            return "Il n'y a plus d'étal libre au marché.\n";
        }
    }
    
    public String rechercherVendeursProduit(String produit) {
        if (produit == null) {
            return "Aucun produit spécifié pour la recherche.\n";
        }
        Etal[] etals = marche.trouverEtals(produit);
        StringBuilder sb = new StringBuilder();
        if (etals.length == 0) {
            sb.append("Il n'y a pas de vendeur qui propose des ").append(produit).append(" au marché.\n");
        } else if (etals.length == 1) {
            sb.append("Seul le vendeur ").append(etals[0].getVendeur().getNom()).append(" propose des ").append(produit).append(" au marché.\n");
        } else {
            sb.append("Les vendeurs qui proposent des ").append(produit).append(" sont :\n");
            for (Etal etal : etals) {
                sb.append("- ").append(etal.getVendeur().getNom()).append("\n");
            }
        }
        return sb.toString();
    }
    
    public String partirVendeur(Gaulois vendeur) {
        Etal etal = marche.trouverVendeur(vendeur);
        if (etal != null) {
            // Appelle libererEtal et utilise la chaîne de caractères retournée
            String message = etal.libererEtal();
            return message; // Renvoie le message retourné par libererEtal
        } else {
            return "Le vendeur " + vendeur.getNom() + " ne peut quitter un étal car il n'en occupe pas.\n";
        }
    }
    
    public String afficherMarche() {
        return marche.afficherMarche();
    }
   
    public Etal rechercherEtal(Gaulois vendeur) {
        Etal etal = marche.trouverVendeur(vendeur);
        if (etal == null) {
            // Si le vendeur n'occupe pas d'étal, affiche un message indiquant cette situation.
            System.out.println("Le vendeur " + vendeur.getNom() + " n'occupe aucun étal au marché.\n");
        }
        return etal; // Retourne l'instance de Etal trouvée ou null si aucune n'est trouvée
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
	        if (produit == null) {
	            return new Etal[0];
	        }
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