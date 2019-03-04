/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.parser.nbp;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mariusz
 */
@XmlRootElement(name = "tabela_kursow")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CurrencyTable {

    String type;
    String uid;
    String tableNumber;
    String publishedDate;
    String listingDate;

    
    List<CurrencyEntry> entries = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }    

    public List<CurrencyEntry> getEntries() {
        return entries;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    @XmlElement(name = "numer_tabeli")
    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    @XmlElement(name = "data_publikacji")
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getListingDate() {
        return listingDate;
    }

    @XmlElement(name = "data_notowania")
    public void setListingDate(String listingDate) {
        this.listingDate = listingDate;
    }
    
    @XmlElement(name = "pozycja")
    public void setEntries(List<CurrencyEntry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "CurrencyTable{" + "type=" + type + ", uid=" + uid + ", tableNumber=" + tableNumber + ", publishedDate=" + publishedDate + ", listingDate=" + listingDate + '}';
    }

    

}
