package org.demo.soap.xml;

import jakarta.xml.bind.annotation.XmlElement;

public class TLanguageXml {

    @XmlElement(name = "sISOCode")
    public String isoCode;

    @XmlElement(name = "sName")
    public String name;

}