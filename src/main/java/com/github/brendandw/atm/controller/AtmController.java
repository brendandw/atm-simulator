/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.controller;

import com.github.brendandw.atm.delegate.AtmDelegate;
import com.github.brendandw.atm.dto.CashList;
import com.github.brendandw.atm.dto.CashNotes;
import com.github.brendandw.atm.dto.Withdrawal;
import com.github.brendandw.atm.exceptions.AtmException;
import com.github.brendandw.atm.exceptions.TechnicalException;
import com.github.brendandw.atm.util.CashUtil;
import com.github.brendandw.atm.delegate.AtmDelegate;
import com.github.brendandw.atm.exceptions.AtmException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** 
 * @author brendandw
 */
@RestController
@RequestMapping("/atm")
public class AtmController {

    @Autowired
    AtmDelegate atmDelegate;

    @ApiOperation(value = "getCashInAtm", nickname = "getCashInAtm")    
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = CashList.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
    @RequestMapping(method = RequestMethod.GET,value = "/getCashInAtm")
      public List<CashNotes> getCashInAtm() {
            return atmDelegate.getNotesAvailableModel();
      }
      
    @ApiOperation(value = "addCashToAtm", nickname = "addCashToAtm")       
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = CashList.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
    @RequestMapping(method = RequestMethod.POST,value = "/addCashToAtm")
      public List<CashNotes> addCashToAtm(@RequestBody List<CashNotes> cashToAdd) throws AtmException {
            return atmDelegate.addCashToAtm(CashUtil.convertListOfCashNotesToMap(cashToAdd));
      }
      
    @ApiOperation(value = "withdrawCashCombination", nickname = "withdrawCashCombination")       
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = CashList.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
    @RequestMapping(method = RequestMethod.POST,value = "/withdrawCashCombination")
      public boolean withdrawCashCombination(@RequestBody List<CashNotes> cashToAdd) throws AtmException{
            return atmDelegate.withdrawCashCombination(CashUtil.convertListOfCashNotesToMap(cashToAdd));
      }

    @ApiOperation(value = "withdraw", nickname = "withdraw")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "amount", value = "Amount to withdraw", required = true, dataType = "integer", paramType = "path", defaultValue="0")
      })
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Withdrawal.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
      @RequestMapping(method = RequestMethod.GET, value = "withdraw/{amount}", produces = "application/json")
      public Withdrawal withdraw(@PathVariable("amount") String amount) throws TechnicalException,AtmException{
          try {
              Integer amountRequested = Integer.parseInt(amount);
              return atmDelegate.withdrawCash(amountRequested);
          }
          catch (NumberFormatException e) {
              throw new AtmException("Requested amount not in the correct format - Has to be a standard integer number");
          }
          
      }
  
  
}
