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
public class ViewInfo {
  private final boolean connected;
  
  public ViewInfo(boolean isConnected) {
    connected = isConnected;
  }
  
  public boolean isConnected() {
    return connected;
  }
}
