package ws.model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="contact")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContactEntity {
    private Long id;
    private String nom;
    private String prenom;
    private String tel;
    private String email;

    // Constructeurs
    public ContactEntity() {
        // Constructeur par défaut
    }

    public ContactEntity(String nom, String prenom, String tel, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
