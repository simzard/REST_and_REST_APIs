/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import facades.CountryFacade;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author simon
 */
@Path("country")
public class REST_Countries {

    @Context
    private UriInfo context;
    
    private CountryFacade facade = new CountryFacade(Persistence.createEntityManagerFactory("REST_and_REST_APIsPU"));

    /**
     * Creates a new instance of Country_REST
     */
    public REST_Countries() {
    }

    /**
     * Retrieves representation of an instance of rest.Country_REST
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getAllCountries() {
        return facade.getCountriesCodeNameContCapital();
    }

    @GET
    @Path("gt/{number}")
    @Produces("application/json")
    public String getAllCountriesGT(@PathParam("number") long number) {
        return facade.getCountriesCodeNameContCapitalGT(number);
    }
    
    @GET
    @Path("{countryCode}")
    @Produces("application/json")
    public String getAllCitiesInCountry(@PathParam("countryCode") String countryCode) {
        return facade.getCitiesInCountry(countryCode);
    }
    
    @POST
    @Path("{countryCode}")
    @Produces("application/json")
    @Consumes("application/json")
    public String createNewCity(@PathParam("countryCode") String countryCode, String json) {
        //Get the quote text from the provided Json
        JsonObject quoteInJSON = new JsonParser().parse(json).getAsJsonObject();
        String name = quoteInJSON.get("name").getAsString();
        String district = quoteInJSON.get("district").getAsString();
        int population = quoteInJSON.get("population").getAsInt();
        
        facade.createNewCityForCountry(countryCode, name, district, population);
        
        JsonObject quoteOutJSON = new JsonObject();
        quoteOutJSON.addProperty("countryCode", countryCode);
        quoteOutJSON.addProperty("name", name);
        quoteOutJSON.addProperty("district", district);
        quoteOutJSON.addProperty("population", population);
        String jsonResponse = new Gson().toJson(quoteOutJSON);
        
        return jsonResponse;
    }
}
