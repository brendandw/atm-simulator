/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brendandw.atm.dto.CashNotes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketMessage;
import com.github.brendandw.atm.dto.CashList;

import java.util.List;


public class WebSocketReturnMessage implements WebSocketMessage<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketReturnMessage.class);
    private static final String JSON_PROCESSING_ERROR = "Error serialising websocket payload to JSON String";
    
    private List<CashNotes> cashList;

    public WebSocketReturnMessage(List<CashNotes> cashList) {
        this.cashList=cashList;
    }
            

    @Override
    public Object getPayload() {
        String jsonResponse = null;
        try{
            jsonResponse = new ObjectMapper().writeValueAsString(cashList);
        } catch (JsonProcessingException jpe) {
            LOGGER.error(JSON_PROCESSING_ERROR,jpe);
        }
        return jsonResponse;
    }

    @Override
    public int getPayloadLength() {
        return cashList.size();
    }

    @Override
    public boolean isLast() {
        return true;
    }

   
    
}
