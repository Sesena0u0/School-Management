package com.sesena.gestionecole;

public class Etudiant {

    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String nie;
    private int id_classe;

    public Etudiant(int id, String nom, String prenom, int age, String nie, int id_classe){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.nie = nie;
        this.id_classe = id_classe;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNie() {
        return nie;
    }

    public void setNie(String nie) {
        this.nie = nie;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setId_classe(int id_classe) {
        this.id_classe = id_classe;
    }

    public int getId_classe() {
        return id_classe;
    }
}
