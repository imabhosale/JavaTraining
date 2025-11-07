package org.demo.soap.restclient;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@Produces(MediaType.TEXT_XML)
@Consumes(MediaType.TEXT_XML)
@RegisterRestClient(configKey = "country-soap-service")
public interface CountryServiceRestClient {

    @POST
    String callFullCountryInfo(String request);

}