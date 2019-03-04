/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.parser.nbp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author mariusz
 */
public class NbpCurrencyProvider implements CurrencyProvider {

    private final String URL_PREFIX = "http://www.nbp.pl/kursy/xml/";
    private final String PATTERN1 = "c[0-9]{3}z[0-9]{6}";

    @Override
    public List<CurrencyTable> getCurrencyTablesForDates(LocalDate startDate, LocalDate endDate) {
        List<CurrencyTable> tables = new ArrayList<>();

        List<String> tableNames = prepareDirs(startDate, endDate);
        tableNames.stream().forEach(t -> {
            CurrencyTable table = getTable(t);
            if (table != null) {
                tables.add(table);
            }
        });

        return tables;
    }

    private List<String> prepareDirs(LocalDate startDate, LocalDate endDate) {

        List<Integer> years = getYears(startDate, endDate);
        List<String> days = getDates(startDate, endDate);
        List<String> tables = new ArrayList<>();
        years.stream().forEach(y -> {
            tables.addAll(getDir(y).stream()
                    .filter(y1 -> (y1.matches(PATTERN1) && days.contains(y1.substring(y1.length() - 6, y1.length()))))
                    .collect(Collectors.toList()));
        });

        return tables;

    }

    private CurrencyTable getTable(String name) {
        CurrencyTable table = null;
        String link = URL_PREFIX + name + ".xml";
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CurrencyTable.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            URL url = new URL(link);
            table = (CurrencyTable) jaxbUnmarshaller.unmarshal(url);
        } catch (JAXBException | MalformedURLException e) {
            Logger.getLogger(NbpCurrencyProvider.class.getName()).log(Level.SEVERE, null, e);
        }

        return table;
    }

    private List<String> getDir(int year) {

        String dirYear = null;
        String dirLink;

        LocalDate localDate = LocalDate.now();
        if (localDate.getYear() == year) {
            dirYear = "dir.txt";
        } else {
            dirYear = "dir" + year + ".txt";
        }

        dirLink = URL_PREFIX + dirYear;

        List<String> list = new ArrayList<>();
        try {
            URLConnection conn = new URL(dirLink).openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            list = reader.lines().collect(Collectors.toList());

        } catch (MalformedURLException ex) {
            Logger.getLogger(NbpCurrencyProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NbpCurrencyProvider.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    private List<String> getDates(LocalDate startDate, LocalDate endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        numOfDaysBetween++;
        List<String> dates = IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(i -> startDate.plusDays(i).format(formatter))
                .collect(Collectors.toList());
        return dates;

    }

    private List<Integer> getYears(LocalDate startDate, LocalDate endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");

        LocalDate startDate2 = startDate.withMonth(1).withDayOfMonth(1);
        LocalDate endDate2 = endDate.withMonth(12).withDayOfMonth(31);

        long numOfYearsBetween = ChronoUnit.YEARS.between(startDate2, endDate2);
        numOfYearsBetween++;
        List<Integer> dates;
        dates = IntStream.iterate(0, i -> i + 1)
                .limit(numOfYearsBetween)
                .mapToObj(i -> {
                    return Integer.parseInt(startDate.plusYears(i).format(formatter));
                })
                .collect(Collectors.toList());
        return dates;

    }

}
