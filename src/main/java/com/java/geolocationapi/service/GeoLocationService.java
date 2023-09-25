package com.java.geolocationapi.service;

import com.java.geolocationapi.bean.GeoLocation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Service
public class GeoLocationService {

    @Value("${spring.application.google.geoCodeUrl}")
    public String geoCodeUrl;

    @Value("${spring.application.google.key}")
    public String key;

    public GeoLocation getLatitudeLongitude(String inputAddress) {

        RestTemplate restTemplate = new RestTemplate();

        String latLongUrl = geoCodeUrl + inputAddress + "&" + key;

        GeoLocation geoLocation = new GeoLocation();
        geoLocation.setAddress(inputAddress);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromHttpUrl(geoCodeUrl)
                .queryParam("address", inputAddress)
                .queryParam("key", key)
                .build()
                .toUri();

        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()){
            JSONObject responseBody = new JSONObject(responseEntity.getBody());

            JSONArray resultsArray = responseBody.getJSONArray("results");
            JSONObject locationJson = resultsArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            geoLocation.setLatitude(locationJson.get("lat").toString());
            geoLocation.setLongitude(locationJson.get("lng").toString());

        }
        return geoLocation;
    }

}
