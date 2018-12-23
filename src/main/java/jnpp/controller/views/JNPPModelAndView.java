package jnpp.controller.views;

import jnpp.controller.views.info.ViewInfo;
import org.springframework.web.servlet.ModelAndView;

/**
 * Classe de ModelAndView de l'application
 */
public class JNPPModelAndView extends ModelAndView {

    /**
     * les informations de la vue
     */
    private final ViewInfo info;

    /**
     * Constructeur
     *
     * @param viewName le nom de la vue
     * @param viewInfo les informations de la vue
     */
    public JNPPModelAndView(String viewName, ViewInfo viewInfo) {
        super(viewName);
        this.info = viewInfo;
        this.addObject("info", this.info);
    }
}
