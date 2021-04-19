/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.websockets;

import com.github.brendandw.atm.model.CashController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;



public class AtmWebSocketHandler extends AbstractWebSocketHandler {
 
  @Autowired
  private CashController cashController;
  
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
      super.afterConnectionEstablished(session);
      cashController.registerObserver(session);   
  } 
  
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
      super.afterConnectionClosed(session,status);
      cashController.removeObserver(session); 
  }
}