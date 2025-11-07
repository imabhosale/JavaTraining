package org.demo.soap.service;


import io.quarkus.logging.Log;
import io.quarkus.qute.Template;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.demo.soap.restclient.CountryServiceRestClient;

@ApplicationScoped
public class CountrySoapService {

    @Inject
    Template fullCountryInfoRequestTemplate;

    @Inject
    @RestClient
    CountryServiceRestClient countryServiceRestClient;

    public String  callFullCountryInfoSoapOperation(String countryISOCode) {

        String response = "";
        try {
            String request = fullCountryInfoRequestTemplate.data("countryISOCode", countryISOCode).render();
            Log.info("soap request=" + request);
            response = countryServiceRestClient.callFullCountryInfo(request);
            Log.info("raw soap response=" + response);
            response = response.replace("soap:", "").replace("m:", "").replace("xmlns:", "");
            Log.info("updated soap response=" + response);
            return response;
        }catch(Exception e) {
            Log.error(e);
        }
        return response;

    }
}