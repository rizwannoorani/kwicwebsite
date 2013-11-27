package com.iig.cyberminer.rest.resources;

import com.iig.cyberminer.rest.representations.CyberminerFailure;
import com.iig.cyberminer.rest.representations.CyberminerResponse;
import com.iig.cyberminer.rest.representations.CyberminerSuccess;
import com.iig.cyberminer.search.CyberminerService;

import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class CyberminerResource {
    private final AtomicLong counter;
    private final CyberminerService cyberminer;

    public CyberminerResource() {
        this.counter = new AtomicLong();
        this.cyberminer = new CyberminerService();
    }

    @GET
    @Timed
    public CyberminerResponse sayHello(@QueryParam("name") Optional<String> name) {
        return new CyberminerFailure(
            counter.incrementAndGet(),
            "Post to this endpoint with search query.");
    }

    @POST
    public CyberminerResponse getSearchResults(@FormParam("queryType") String type, @FormParam("queryString") String query ) {
        try {
            System.out.println( "Received post to search w/ type =" + type + " and query=" + query );
            return new CyberminerSuccess( counter.incrementAndGet(), cyberminer.getSearchResults( type, query ) );
        } catch( Exception e ) {
            return new CyberminerFailure( counter.incrementAndGet(), "Unable to process search." );
        }
    }
}