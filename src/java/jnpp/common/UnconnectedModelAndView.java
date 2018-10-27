package jnpp.common;

/**
 * Classe de ModelAndView pour un utilisateur non connecté
 */
public class UnconnectedModelAndView extends JNPPModelAndView {
    /**
     * Constructeur
     * @param viewName le nom de la vue
     * @param viewInfo les informations de la vue
     */
    public UnconnectedModelAndView(String viewName, UnconnectedInfo viewInfo) {
	super(viewName, viewInfo);
    }

}
