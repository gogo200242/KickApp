package com.hugo.kick;

public class UserHelperClass {

    String prenom, nom, pseudo, email, password;

    public UserHelperClass() {
    }

    public UserHelperClass(String prenom, String nom, String pseudo, String email, String password) {
        this.prenom = prenom;
        this.nom = nom;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
