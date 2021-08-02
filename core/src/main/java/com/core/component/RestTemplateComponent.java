package com.core.component;

import com.core.common.dto.RestDto;
import com.core.ecos.dto.EcosDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
public class RestTemplateComponent {

    @Autowired    Gson gson;
    @Autowired    RestTemplate restTemplate;

    public final JsonObject getJsonFromAPI(RestDto restDto) {
        String response =
            restTemplate.getForEntity(getUrlString(restDto), String.class).getBody();
        return gson.fromJson(response, JsonObject.class);
    }


    public final URI getUrlString(RestDto restDto) {
        return UriComponentsBuilder.fromUriString(restDto.getUri())
                .buildAndExpand(new ObjectMapper().convertValue(restDto.getValueMap(), Map.class))
                .toUri();
    }
}
