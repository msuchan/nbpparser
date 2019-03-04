/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.parser.nbp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mariusz
 */
public class CurrencyParser {
    
    
    public void start(ParserArgument pa){
        
        CurrencyProvider provider = new NbpCurrencyProvider();
        List<CurrencyTable> tables = provider.getCurrencyTablesForDates(pa.getDateFrom(), pa.getDateTo());
        
        BigDecimal sumBuyRate = BigDecimal.ZERO;
        List<CurrencyEntry> allEntries = new ArrayList<>();
       
        tables.stream().forEach(t->{
             allEntries.addAll(t.getEntries().stream().filter(e->e.getCode().toLowerCase().equals(pa.getCurrency().toLowerCase())).collect(Collectors.toList()));
        });
       
         if(allEntries.isEmpty()){
            System.out.println("Brak danych");
            System.exit(-4);
        }
        
         sumBuyRate = allEntries
                 .stream()
                 .map(CurrencyEntry::getBuyBigDecimal)
                 .reduce(BigDecimal.ZERO, BigDecimal::add);
        
         BigDecimal avgBuy = sumBuyRate.divide(BigDecimal.valueOf(allEntries.size()), RoundingMode.HALF_UP);
         
        List<BigDecimal> sellList =  allEntries.stream().map(CurrencyEntry::getSellBigDecimal).collect(Collectors.toList());
         BigDecimal avgDev  = MathUtil.calculateStandardDevation(sellList);
         
        // print results
         System.out.println(avgBuy);
         System.out.println(avgDev);
    }
    
}
