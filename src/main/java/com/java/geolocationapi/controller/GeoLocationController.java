package com.java.geolocationapi.controller;

import com.java.geolocationapi.bean.GeoLocation;
import com.java.geolocationapi.service.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GeoLocationController {

    @Autowired
    GeoLocationService geoLocationService;

    @GetMapping (value="/address")
    public GeoLocation address(@RequestParam String inputAddress) {

        GeoLocation output = new GeoLocation();

        try {
            System.out.println("Given address is " + inputAddress);
            output = geoLocationService.getLatitudeLongitude(inputAddress);

        } catch (Exception e) {
            e.getMessage();
        }
        return output;

    }

}
