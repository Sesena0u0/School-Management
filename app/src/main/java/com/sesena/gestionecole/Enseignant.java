package com.sesena.gestionecole;

public class Enseignant {
    private int id;
    private String nom;
    private String prenom;
    private int id_matiere;

    Enseignant(int id, String nom, String prenom, int id_matiere){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.id_matiere = id_matiere;
    }

    public int getId_matiere() {
        return id_matiere;
    }

    public void setId_matiere(int id_matiere) {
        this.id_matiere = id_matiere;
    }

    public String getNom(){
        return this.nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
