package org.demo.soap.xml;

import jakarta.xml.bind.annotation.XmlElement;

public class FullCountryInfoResponseXml {

    @XmlElement(name = "FullCountryInfoResult")
    public FullCountryInfoResultXml fullCountryInfoResultXml;

}