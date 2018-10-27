package jnpp.common;

/**
 * Classe de ModelAndView pour un utilisateur connecté
 */
public class ConnectedModelAndView extends JNPPModelAndView {
    /**
     * Constructeur
     * @param viewName le nom de la vue
     * @param viewInfo les informations de la vue
     */
    public ConnectedModelAndView(String viewName, ConnectedInfo viewInfo) {
	super(viewName, viewInfo);
	this.addObject("firstName", viewInfo.getFirstName());
	this.addObject("lastName", viewInfo.getLastName());
    }

}
