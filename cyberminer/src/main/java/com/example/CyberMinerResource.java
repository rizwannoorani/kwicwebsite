package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("search")
public class CyberMinerResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Post your search queries here!";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String search() {
        //do search work here

        return "{searchResults: \"blah\"}";
    }
}
