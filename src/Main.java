import javax.xml.ws.Endpoint;
import ws.service.*;

public class Main {

    public static void main(String[] args) {
        String url = "http://localhost:8080/ContactWS";
        Endpoint.publish(url, new ContactServiceImpl());
        System.out.println("Service publié à l'adresse : " + url);
    }
}
