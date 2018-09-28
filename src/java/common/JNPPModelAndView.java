/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author damien
 */
public class JNPPModelAndView extends ModelAndView {
  private final ViewInfo info;
  
  public JNPPModelAndView(String viewName, ViewInfo viewInfo) {
    super(viewName);
    info = viewInfo;
    this.addObject("isConnected", info.isConnected());
  }
  
}
