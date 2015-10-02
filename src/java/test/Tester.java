/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import facades.CountryFacade;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author simon
 */
public class Tester {

    public static void main(String[] args) {
        new Tester().test();
    }

    public void test() {
        CountryFacade facade = new CountryFacade(Persistence.createEntityManagerFactory("REST_and_REST_APIsPU"));

//        List<String> results = facade.getCitiesInCountry("AFG");
//        
//        for (String s : results) {
//            System.out.println(s);
//        }
//        
//        System.out.println("");
//        
//        facade.createNewCityForCountry("AFG", "TestBy", "TestDistrict", 10);
//        
//        
//        List<String> results2 = facade.getCitiesInCountry("AFG");
//        
//        for (String s : results2) {
//            System.out.println(s);
//        }
        System.out.println(facade.getCountriesCodeNameContCapital());
    }
    
}
