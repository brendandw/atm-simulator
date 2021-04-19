/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.cashdispenser.factory;

import com.github.brendandw.atm.interfaces.ICashDispenser;
import com.github.brendandw.atm.cashdispenser.strategy.BruteForceCashDispenser;
import com.github.brendandw.atm.cashdispenser.strategy.DPCashDispenser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author brendandw
 */
@Component
public class CashDispenserFactory {

    @Autowired
    BruteForceCashDispenser bruteForceCashDispenser;

    @Autowired
    DPCashDispenser DPCashDispenser;

    public enum DispenserType{
        BRUTE_FORCE,
        DYNAMIC_PROGRAMMING;
    }



    public ICashDispenser getCashDispenser(DispenserType type) throws ClassNotFoundException{

        switch (type) {
            case BRUTE_FORCE:
                return bruteForceCashDispenser;
            case DYNAMIC_PROGRAMMING:
                return DPCashDispenser;
        }
        throw new ClassNotFoundException("Cash Dispenser strategy not found");
    }

}
