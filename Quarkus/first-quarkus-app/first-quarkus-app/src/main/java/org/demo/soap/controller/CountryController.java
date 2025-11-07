package org.demo.soap.controller;


import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.demo.soap.dto.FullCountryInfoRequest;
import org.demo.soap.dto.FullCountryInfoResponse;
import org.demo.soap.service.CountryService;

@Path("/")
public class CountryController {

    @Inject
    CountryService countryService;

    @Path("/fullcountryinfo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public FullCountryInfoResponse fullCountryInfo(FullCountryInfoRequest fullCountryInfoRequest) {
        return countryService.processRequest(fullCountryInfoRequest);
    }

}