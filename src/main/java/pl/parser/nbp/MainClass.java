/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.parser.nbp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author mariusz
 */
public class MainClass {

    public static void main(String[] args) {

        if (args == null || args.length < 3) {
            System.out.println("Uzycie: pl.parser.nbp.MainClass waluta data_od data_do");
            System.exit(-1);
        }

        new MainClass().start(args);

    }

    public void start(String[] args) {
        ParserArgument arguments = argumentsOK(args);
        if (arguments.isResult()) {
            CurrencyParser cp = new CurrencyParser();
            cp.start(arguments);
        } else {
            System.out.println(arguments.getDescription().toString());
            System.exit(-3);
        }
    }

    private ParserArgument argumentsOK(String[] args) {

        ParserArgument pa = new ParserArgument();
        pa.setResult(true);
        List<String> currencies = Arrays.asList("USD", "EUR", "CHF", "GBP");

        pa.setCurrency(args[0]);
        
        String dateFrom = args[1];
        String dateTo = args[2];

        if (!currencies.contains(pa.getCurrency())) {
            System.out.println("Waluta " + pa.getCurrency() + " nie jest obsługiwana.");
            System.exit(-2);
        }

        pa.setDateFrom(getLocalDate(dateFrom));
        pa.setDateTo( getLocalDate(dateTo));
        

        if (pa.getDateFrom() == null) {
            pa.getDescription().append("Niepoprawny format daty początkowej: ").append(dateFrom).append(" (format: yyyy-MM-dd)").append("\n");
            pa.setResult(false);
        }

        if (pa.getDateTo() == null) {
            pa.getDescription().append("Niepoprawny format daty końcowej: ").append(dateTo).append(" (format: yyyy-MM-dd)").append("\n");
            pa.setResult(false);
        }

        if (pa.isResult() && pa.getDateFrom().isAfter(pa.getDateTo())) {
            pa.getDescription().append("Niepoprawny zakres dat, data początkowa: ").append(pa.getDateFrom()).append(" data końcowa:").append(pa.getDateTo()).append("\n");
            pa.setResult(false);
        }

        return pa;
    }

    private static LocalDate getLocalDate(String date) {

        DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").parseStrict().toFormatter();
        try {
            return LocalDate.parse(date, dtf);
        } catch (Exception e) {
            return null;
        }

    }

}
