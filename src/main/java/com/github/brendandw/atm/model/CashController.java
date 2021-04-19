/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brendandw.atm.dto.CashList;
import com.github.brendandw.atm.dto.CashNotes;
import com.github.brendandw.atm.exceptions.AtmException;
import com.github.brendandw.atm.util.CashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author brendandw
 */
@Component
public class CashController implements CashObservable {

    @Autowired
    AtmModel atmModel;
    @Autowired
    ObjectMapper objectMapper;


    private List<WebSocketSession> observers = new ArrayList<>();

    public synchronized boolean  withdraw(Map<Integer,Integer> cash) throws AtmException {
      Map<Integer,Integer> cashInAtm = atmModel.getNotesAvailableModel();
      Map<Integer,Integer> cashAfterWithdrawal = new HashMap<>();
      boolean hasSufficientCash = true;
      for (Map.Entry<Integer,Integer> entry: cashInAtm.entrySet()) {
          int currentDenomination = entry.getKey();
          int amountInAtm = entry.getValue();
          Integer amount = cash.get(currentDenomination);
          amount=amount==null?0:amount;

          if (amountInAtm<amount) {
              hasSufficientCash=false;
              break;
          } else {
              cashAfterWithdrawal.put(currentDenomination, (amountInAtm-amount));
          }
      }

      if (hasSufficientCash) {
          atmModel.setNotesAvailableModel(cashAfterWithdrawal);
          try {
            notifyObservers();
          } catch (AtmException e) {
                throw new AtmException("Cash Successfully withdrawn, but error encountered in returning the current cash model", e);
          }
      }
      return hasSufficientCash;
    }

    public Map<Integer,Integer> addCash(Map<Integer,Integer> cash) throws AtmException {

        for (Map.Entry<Integer,Integer> entry: cash.entrySet()) {
            int currentDenomination = entry.getKey();
            Integer currentAmount = atmModel.getNotesAvailableModel().get(currentDenomination);
            currentAmount=currentAmount==null?0:currentAmount;
            int newAmount=currentAmount+entry.getValue();
            atmModel.getNotesAvailableModel().put(currentDenomination, newAmount);
        }
        try {
            notifyObservers();
        } catch (AtmException e) {
            throw new AtmException("Cash Successfully Added, but error encountered in returning the current cash model", e);
        }
        return atmModel.getNotesAvailableModel();
    }

    public Map<Integer,Integer> getCashInAtm() {
        return atmModel.getNotesAvailableModel();
    }

    @Override
    public void registerObserver(WebSocketSession webSocketSession) {
        observers.add(webSocketSession);
    }

    @Override
    public void removeObserver(WebSocketSession webSocketSession) {
        observers.remove(webSocketSession);
    }

    @Override
    public void notifyObservers() throws AtmException{
        List<CashNotes> listOfNotes = CashUtil.convertMapToListOfCashNotes(atmModel.getNotesAvailableModel());
        try {
            String jsonTextResponse = objectMapper.writeValueAsString(listOfNotes);
            for (WebSocketSession observer: observers) {

                    observer.sendMessage(new TextMessage(jsonTextResponse));

            }
        } catch (JsonProcessingException e) {
            throw new AtmException("Could not serialise", e);
        }
        catch (IOException e) {
            throw new AtmException("Could not generate notification message upon update of ATM model", e);
        }
    }




}
