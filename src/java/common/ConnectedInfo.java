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
public class ConnectedInfo extends ViewInfo {
  private final String firstName;

  private final String lastName;
  
  public ConnectedInfo(String fName, String lName) {
    super(true);
    firstName = fName;
    lastName = lName;
  }
  
  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}
