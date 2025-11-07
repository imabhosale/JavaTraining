package org.demo.soap.xml;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Envelope")
public class EnvelopeXml {

    @XmlElement(name = "Body")
    public BodyXml bodyXml;

}