/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.brendandw.atm.config.AtmConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author brendandw
 *
 * The Model class that represents the cash that is in the ATM at any paticular point in time.
 */
@Component
public class AtmModel {



    private Map<Integer, Integer> notesAvailableModel = new LinkedHashMap<>();

    public AtmModel(AtmConfig atmConfig) {
        // iterate through seed-cash config, and add to the notesAvailableModel
        atmConfig.getSeedcash().values().forEach(denomination -> {
            notesAvailableModel.put(denomination.get(atmConfig.VALUE_KEY), denomination.get(atmConfig.AMOUNT_KEY));
        });
    }


    public Map<Integer, Integer> getNotesAvailableModel() {
        return notesAvailableModel;
    }

    protected void setNotesAvailableModel(Map<Integer, Integer> notesAvailableModel) {
        this.notesAvailableModel = notesAvailableModel;
    }



}
