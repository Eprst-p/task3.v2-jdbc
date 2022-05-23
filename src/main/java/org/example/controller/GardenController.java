package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.database.DBinteractions;
import org.example.gardenplace.GardenPlace;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/places")
public class GardenController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() throws SQLException, JsonProcessingException {

        return new ObjectMapper().writeValueAsString(DBinteractions.getAllPlaces());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getByID(@PathParam("id") int id) throws JsonProcessingException {

        return new ObjectMapper().writeValueAsString(DBinteractions.getPlaceByID(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewPlace(String newPlaceJSON) throws JsonProcessingException, SQLException {

        DBinteractions.addNewPlace(new ObjectMapper().readValue(newPlaceJSON, GardenPlace.class));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updatePlace(@PathParam("id") int id, String updatedPlaceJSON) throws SQLException, JsonProcessingException {

        DBinteractions.updatePlace(id, new ObjectMapper().readValue(updatedPlaceJSON, GardenPlace.class));
    }

    @DELETE
    @Path("/{id}")
    public void deletePlace(@PathParam("id") int id) throws SQLException {

        DBinteractions.deletePlace(id);
    }
}