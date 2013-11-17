package com.iig.cyberminer.rest.resources;

import com.iig.cyberminer.rest.representations.CyberminerResponse;
import com.iig.cyberminer.search.CyberminerService;

import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/cyberminer")
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
        return new CyberminerResponse(
            counter.incrementAndGet(),
            "Post to this endpoint with search query.");
    }

    @POST
    public CyberminerResponse getSearchResults( ) {
        return new CyberminerResponse(
            counter.incrementAndGet(),
            cyberminer.getSearchResults());
    }
}