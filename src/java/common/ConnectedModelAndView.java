/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author damien
 */
public class ConnectedModelAndView extends JNPPModelAndView {
  
  public ConnectedModelAndView(String viewName, ConnectedInfo viewInfo) {
    super(viewName, viewInfo);
    this.addObject("firstName", viewInfo.getFirstName());
    this.addObject("lastName", viewInfo.getLastName());
  }
  
}
