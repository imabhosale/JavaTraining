package org.demo.soap.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FullCountryInfoResult {

    public String isoCode;
    public String name;
    public String capitalCity;
    public String phoneCode;
    public String continentCode;
    public String currencyISOCode;
    public String countryFlag;
    public List<Language> languages;
}