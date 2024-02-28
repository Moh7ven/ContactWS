package ws.service;

import javax.jws.WebService;

import java.sql.*;

import javax.jws.WebService;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

/*import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;*/


@WebService(endpointInterface = "ws.service.ContactService")
public class ContactServiceImpl implements ContactService {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/contactdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @Override
    public String ajouterContact(String nom, String prenom, String tel, String email)  {
    	
    	// Vérification des champs vides
        if (nom == null || nom.isEmpty() || prenom == null || prenom.isEmpty() || tel == null || tel.isEmpty() || email == null || email.isEmpty()) {
            return "Veuillez remplir tous les champs.";
        }
    	
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO contacts (nom, prenom, tel, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nom);
                statement.setString(2, prenom);
                statement.setString(3, tel);
                statement.setString(4, email);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println(prenom + " a été enregistré avec succès");
                    return prenom + " a été ajouté avec succès.";
                } else {
                    return "Aucun contact ajouté. Veuillez vérifier les données fournies.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erreur lors de l'ajout du contact. Vérifiez la connexion à la base de données.";
        }
    }


    @Override
    public String rechercherContact(String critere) {
    	int count = 0; // Variable de comptage
    	
    	if (critere == null || critere.isEmpty()) {
            return "Veuillez remplir le champs.";
        }
    	
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM contacts WHERE nom LIKE ? OR prenom LIKE ? OR tel LIKE ? OR email LIKE ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "%" + critere + "%");
                statement.setString(2, "%" + critere + "%");
                statement.setString(3, "%" + critere + "%");
                statement.setString(4, "%" + critere + "%");

                ResultSet resultSet = statement.executeQuery();

                
                StringBuilder result = new StringBuilder("Résultat de la recherche :\n");
                
             // Vérifier s'il y a des résultats
                if (!resultSet.isBeforeFirst()) {
                    return critere +" est introuvable !";
                }
                
                while (resultSet.next()) {
                	result.append("Nom: ").append(resultSet.getString("nom"))
            		.append("\n")
                    .append("Prenom: ").append(resultSet.getString("prenom"))
                    .append("\n")
                    .append("Tel: ").append(resultSet.getString("tel"))
                    .append("\n")
                    .append("Email: ").append(resultSet.getString("email"))
                    .append("\n")
                    .append("-----------------------------------------------------------------------")
                    .append("\n")
                    .append("\n");
                	
                    count++; // Incrémente le compteur à chaque itération
                }
                
                result.append("Nombre total de contacts trouvé(s): ").append(count);
                
                System.out.println(result.toString());

                return result.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erreur lors de la recherche du contact.";
        }
    }

    private static final String EMAIL_SUBJECT_DEFAULT = "No subject";
    
    @Override
    public String envoyerEmail(String destinataire, String sujet,String contenu) {
    	// Vérification des données
        if (destinataire == null || destinataire.isEmpty() || contenu == null || contenu.isEmpty()) {
            return "Les données d'envoi d'e-mail ne sont pas correctes.";
        }

        // Utilisation du sujet fourni ou du sujet par défaut
        String emailSubject = (sujet != null && !sujet.isEmpty()) ? sujet : EMAIL_SUBJECT_DEFAULT;

        // Configuration de la session JavaMail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Création de la session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("mohasangare7@gmail.com", "ddcwwqtiwyepqsux");
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mohsangare7@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(emailSubject);
            message.setText(contenu);

            // Envoi du message
            Transport.send(message);

            return "E-mail envoyé avec succès à " + destinataire;
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Erreur lors de l'envoi de l'e-mail.";
        }
    }
    

    @Override
    public String listerContacts() {
    	int count = 0; // Variable de comptage
    	
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM contacts";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);

                StringBuilder result = new StringBuilder("Liste des contacts :\n");
                while (resultSet.next()) {
                    result.append("Nom: ").append(resultSet.getString("nom"))
                    		.append("\n")
                            .append("Prenom: ").append(resultSet.getString("prenom"))
                            .append("\n")
                            .append("Tel: ").append(resultSet.getString("tel"))
                            .append("\n")
                            .append("Email: ").append(resultSet.getString("email"))
                            .append("\n")
                            .append("-----------------------------------------------------------------------")
                            .append("\n")
                            .append("\n");
                    
                    count++; // Incrémente le compteur à chaque itération
                }
                
                result.append("Nombre total de contacts : ").append(count);
                
                System.out.println(result.toString());
                
                return result.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erreur lors de la récupération de la liste des contacts.";
        }
    }


	@Override
	public String envoyerSms(String destinataireSms, String message) {
		// TODO Auto-generated method stub
		return null;
	}

}
