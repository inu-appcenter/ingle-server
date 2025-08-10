package com.example.ingle;

import com.example.ingle.domain.building.dto.res.BuildingResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonDeserializeTest {

    @Test
    public void testDeserializeBuildingResponse() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());

        String json = "[{\"buildingId\":1,\"buildingName\":\"Administration Building\",\"latitude\":37.376833680573,\"longitude\":126.6347435192162,\"buildingCategory\":\"SCHOOL_BUILDING\"}]";

        List<BuildingResponse> list = objectMapper.readValue(json, new TypeReference<List<BuildingResponse>>() {});

        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals("Administration Building", list.get(0).buildingName());
    }
}