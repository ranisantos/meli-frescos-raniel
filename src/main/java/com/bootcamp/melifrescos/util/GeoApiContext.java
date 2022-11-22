package com.bootcamp.melifrescos.util;

import org.springframework.context.expression.EnvironmentAccessor;

public class GeoApiContext {

    private final static com.google.maps.GeoApiContext context = new com.google.maps.GeoApiContext.Builder()
            .apiKey("GOOGLE-API-KEY")
            .build();

    public static com.google.maps.GeoApiContext getContext() {
        return context;
    }
}
