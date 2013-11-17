package com.iig.cyberminer.resources;

import com.iig.cyberminer.representations.CyberminerResponse;
import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/cyberminer")
@Produces(MediaType.APPLICATION_JSON)
public class CyberminerResource {
    private final AtomicLong counter;

    public CyberminerResource() {
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public CyberminerResponse sayHello(@QueryParam("name") Optional<String> name) {
        return new CyberminerResponse(counter.incrementAndGet(),
                          "Hello Cyberminer!");
    }
}