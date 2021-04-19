/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.delegate;

import com.github.brendandw.atm.cashdispenser.CashDispenser;
import com.github.brendandw.atm.dto.CashList;
import com.github.brendandw.atm.dto.CashNotes;
import com.github.brendandw.atm.dto.Withdrawal;
import com.github.brendandw.atm.exceptions.AtmException;
import com.github.brendandw.atm.exceptions.TechnicalException;
import com.github.brendandw.atm.model.AtmModel;
import com.github.brendandw.atm.model.CashController;
import com.github.brendandw.atm.util.CashUtil;
import com.github.brendandw.atm.dto.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.brendandw.atm.cashdispenser.CashDispenser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author brendandw
 */

@Component
public class AtmDelegate {

@Autowired
CashDispenser cashDispenser;

@Autowired
AtmModel atmModel;

@Autowired
CashController cashController;

private static final String NOT_AVAILABLE_ERROR_MSG = "The amount requested is not available";
private static final String MULTIPLE_RETURN_VALUES = "More than one withdrawal amount returned";

public Withdrawal withdrawCash(Integer amountRequested) throws TechnicalException, AtmException {

      Map<Integer,Integer> cashInAtm = atmModel.getNotesAvailableModel();
      Map<Integer,Map<Integer,Integer>> combination = cashDispenser.getCashCombination(atmModel.getNotesAvailableModel(),amountRequested);
      Error error = null;
      boolean bWithdrawalSuccessful = false;

      if (combination!=null && combination.size()>1) {
          throw new TechnicalException(MULTIPLE_RETURN_VALUES);
      }

      if (combination!=null && !combination.containsKey(amountRequested)) {
          error = Error.builder()
                  .Description(NOT_AVAILABLE_ERROR_MSG)
                  .build();
      }
      else {
          bWithdrawalSuccessful = cashController.withdraw(combination.get(amountRequested));
          if (!bWithdrawalSuccessful) {
                      error = Error.builder()
                              .Description(NOT_AVAILABLE_ERROR_MSG)
                              .build();

          }
      }

    return Withdrawal.builder()
            .error(error)
            .successful(bWithdrawalSuccessful)
            .amount(combination.keySet().iterator().next())
            .cashList(CashUtil.convertMapToListOfCashNotes(combination.values().iterator().next()))
            .build();



}

public List<CashNotes>  addCashToAtm(Map<Integer,Integer> cashToAdd) throws AtmException{
    return CashUtil.convertMapToListOfCashNotes(cashController.addCash(cashToAdd));
}

public boolean  withdrawCashCombination(Map<Integer,Integer> cashToWithdraw) throws AtmException{
    boolean success = cashController.withdraw(cashToWithdraw);
    return success;
}

public  List<CashNotes> getNotesAvailableModel() {
    return CashUtil.convertMapToListOfCashNotes(cashController.getCashInAtm());
}










}


