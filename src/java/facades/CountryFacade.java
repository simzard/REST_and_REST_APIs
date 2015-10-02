/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.City;
import entities.Country;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author simon
 */
public class CountryFacade {

    private EntityManagerFactory emf;

    public CountryFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Get a list of all countries,
    // 1 with code, 2 name, 3 continent and 4 the name of the capital
    public String getCountriesCodeNameContCapital() {
        JsonArray ja = new JsonArray();

        EntityManager em = getEntityManager();
        try {

            // Use the entity manager  
            Query query
                    = em.createQuery("Select c.code, c.name, c.continent, c.capital.name"
                            + " FROM Country c");

            List<Object[]> objects = query.getResultList();

            for (int i = 0; i < objects.size(); i++) {
                JsonObject quoteOutJSON = new JsonObject();
                quoteOutJSON.addProperty("code", "" + objects.get(i)[0]);
                quoteOutJSON.addProperty("name", "" + objects.get(i)[1]);
                quoteOutJSON.addProperty("continent", "" + objects.get(i)[2]);
                quoteOutJSON.addProperty("captial name", "" + objects.get(i)[3]);

                ja.add(quoteOutJSON);

            }

        } finally {
            em.close();
        }

        return ja.toString();
    }

    public String getCountriesCodeNameContCapitalGT(long min) {
        JsonArray ja = new JsonArray();

        EntityManager em = getEntityManager();
        try {

            // Use the entity manager  
            Query query
                    = em.createQuery("Select c.code, c.name, c.continent, c.capital.name"
                            + " FROM Country c WHERE c.population > :min");
            query.setParameter("min", min);

            List<Object[]> objects = query.getResultList();

            for (int i = 0; i < objects.size(); i++) {
                JsonObject quoteOutJSON = new JsonObject();
                quoteOutJSON.addProperty("code", "" + objects.get(i)[0]);
                quoteOutJSON.addProperty("name", "" + objects.get(i)[1]);
                quoteOutJSON.addProperty("continent", "" + objects.get(i)[2]);
                quoteOutJSON.addProperty("captial name", "" + objects.get(i)[3]);

                ja.add(quoteOutJSON);
            }

        } finally {
            em.close();
        }

        return ja.toString();
    }

    public String getCitiesInCountry(String countryCode) {
        JsonArray ja = new JsonArray();
        

        EntityManager em = getEntityManager();
        try {

            Query query
                    = em.createQuery("Select c.name, c.population"
                            + " FROM City c WHERE c.countryCode.code = :code");
            query.setParameter("code", countryCode);

            List<Object[]> objects = query.getResultList();

            for (int i = 0; i < objects.size(); i++) {
                JsonObject quoteOutJSON = new JsonObject();
                quoteOutJSON.addProperty("name", "" + objects.get(i)[0]);
                quoteOutJSON.addProperty("population", "" + objects.get(i)[1]);
                
                ja.add(quoteOutJSON);
            }

        } finally {
            em.close();
        }

        return ja.toString();
    }

    public City createNewCityForCountry(String countryCode, String name,
            String district, int population) {
        EntityManager em = getEntityManager();
        City newCity = new City(name, district, population);
        Country theCountry = em.find(Country.class, countryCode);
        newCity.setCountryCode(theCountry);

        try {
            em.getTransaction().begin();
            em.persist(newCity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return newCity;
    }

}
