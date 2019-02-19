package jnpp.controller.client;

import javax.xml.bind.DatatypeConverter;

/**
 * Classe permettant de gérer les HttpSession
 */
public class SessionController {

    /**
     * Constructeur private
     */
    private SessionController() {
    }
    
    public static String decodeLogin(String basicallyEncoded) {
        String decoded = new String(DatatypeConverter.parseBase64Binary(basicallyEncoded));
        String[] splitted = decoded.split(":");
        return splitted[0].split(" ")[1];
    }
    
    public static String decodePassword(String basicallyEncoded) {
        String decoded = new String(DatatypeConverter.parseBase64Binary(basicallyEncoded));
        String[] splitted = decoded.split(":");
        return splitted[1];
    }
    
    public static String encodeAuthData(String login, String password) {
        return DatatypeConverter.printBase64Binary(("Basic "+login+":"+password).getBytes());
    }
}
