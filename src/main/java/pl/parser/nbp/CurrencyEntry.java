/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.parser.nbp;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mariusz
 */
@XmlRootElement(name = "pozycja")
public class CurrencyEntry {
    
    String name;
    int converter;
    String code;
    String buy;
    String sell;

    public String getName() {
        return name;
    }
    
    public BigDecimal getBuyBigDecimal(){
        return new BigDecimal(buy.replace(",", "."));
    }
    
    public BigDecimal getSellBigDecimal(){
        return new BigDecimal(sell.replace(",", "."));
    }

    @XmlElement(name="nazwa_waluty")
    public void setName(String name) {
        this.name = name;
    }

    public int getConverter() {
        return converter;
    }

    @XmlElement(name="przelicznik")
    public void setConverter(int converter) {
        this.converter = converter;
    }

    public String getCode() {
        return code;
    }

    @XmlElement(name="kod_waluty")
    public void setCode(String code) {
        this.code = code;
    }

    public String getBuy() {
        return buy;
    }

    @XmlElement(name="kurs_kupna")
    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    @XmlElement(name="kurs_sprzedazy")
    public void setSell(String sell) {
        this.sell = sell;
    }

    @Override
    public String toString() {
        return "CurrencyEntry{" + "name=" + name + ", converter=" + converter + ", code=" + code + ", buy=" + buy + ", sell=" + sell + '}';
    }
    
    
}
