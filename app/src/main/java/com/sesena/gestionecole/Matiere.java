package com.sesena.gestionecole;

public class Matiere {
    int id;
    String nom;
    Matiere(int id, String nom){
        this.id = id;
        this.nom = nom;

    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}
