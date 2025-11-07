package org.demo.soap.xml;

import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;

public class LanguagesXml {

    @XmlElement(name = "tLanguage")
    public List<TLanguageXml> languageList;

}