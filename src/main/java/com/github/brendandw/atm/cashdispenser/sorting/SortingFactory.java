/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.cashdispenser.sorting;

import com.github.brendandw.atm.exceptions.TechnicalException;
import org.springframework.stereotype.Component;

/**
 *
 * @author brendandw
 */
@Component
public class SortingFactory {

    public enum SortingType {
        HIGHEST_DENOMINATION_SORTER
    }

    public ISortingStrategy getSorter(SortingFactory.SortingType type) throws TechnicalException{

        switch (type) {
            case HIGHEST_DENOMINATION_SORTER:
                return new HighestDenominationSorter();
        }
        throw new TechnicalException("Cash Dispenser strategy not found");
    }

}
