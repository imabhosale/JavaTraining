package org.demo.soap.xml;

import jakarta.xml.bind.annotation.XmlElement;

public class BodyXml {

    @XmlElement(name = "FullCountryInfoResponse")
    public FullCountryInfoResponseXml fullCountryInfoResponseXml;

}