/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.model;

import com.github.brendandw.atm.exceptions.AtmException;
import com.github.brendandw.atm.exceptions.AtmException;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author brendandw
 */
public interface CashObservable {
    
    void registerObserver(WebSocketSession webSocketSession);
    void removeObserver(WebSocketSession webSocketSession);
    void notifyObservers() throws AtmException;
    
    
}
