/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.controller;

import java.io.IOException;
import javax.websocket.Session;

public class Client {
  private final String id;
  private final Session session;
  
  public Client(Session session) {
    this.id = this.toString();
    this.session = session;
  }
  
  public void sendText(String text) throws IOException {
    this.session.getBasicRemote().sendText(text);
  }
  
  public String getId() {
    return id;
  }
  
  // hashCode() and equals()
  
}