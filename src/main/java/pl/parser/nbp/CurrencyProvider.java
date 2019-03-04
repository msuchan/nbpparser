/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.parser.nbp;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author mariusz
 */
public interface CurrencyProvider {
    
        public List<CurrencyTable> getCurrencyTablesForDates(LocalDate startDate, LocalDate endDate);
}
