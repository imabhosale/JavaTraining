package org.demo.soap.xml;


import jakarta.xml.bind.annotation.XmlElement;

public class FullCountryInfoResultXml {

    @XmlElement(name = "sISOCode")
    public String isoCode;

    @XmlElement(name = "sName")
    public String name;

    @XmlElement(name = "sCapitalCity")
    public String capitalCity;

    @XmlElement(name = "sPhoneCode")
    public String phoneCode;

    @XmlElement(name = "sContinentCode")
    public String continentCode;

    @XmlElement(name = "sCurrencyISOCode")
    public String currencyISOCode;

    @XmlElement(name = "sCountryFlag")
    public String countryFlag;

    @XmlElement(name = "Languages")
    public LanguagesXml languagesXml;


}