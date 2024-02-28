package ws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.*;

@WebService
public interface ContactService {

    // Ajoute un nouveau contact à la base de données
    @WebMethod
    String ajouterContact(
        @WebParam(name = "nom") @XmlElement(required = true)String nom,
        @WebParam(name = "prenom") @XmlElement(required = true)String prenom,
        @WebParam(name = "tel") @XmlElement(required = true) String tel,
        @WebParam(name = "email") @XmlElement(required = true)String email
    );

    // Recherche des contacts en fonction d'un critère
    @WebMethod
    String rechercherContact(@WebParam(name = "critere") @XmlElement(required = true) String critere);

    // Envoyer d'un email
    @WebMethod
    String envoyerEmail(
        @WebParam(name = "destinataire") @XmlElement(required = true) String destinataire,
        @WebParam(name= "sujet") @XmlElement(required = true) String sujet,
        @WebParam(name = "contenu") @XmlElement(required = true) String contenu
        
    );
    
    // Envoyer un sms
    @WebMethod
    String envoyerSms (
    	@WebParam(name = "destinataireSms") @XmlElement(required = true)String destinataireSms,
    	@WebParam(name = "message") @XmlElement(required = true) String message
    );
    

    // Lister tous les contacts
    @WebMethod
    String listerContacts();
}
