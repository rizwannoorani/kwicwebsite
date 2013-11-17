package com.iig.cyberminer.resources;

import com.iig.cyberminer.representations.KWICResponse;
import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/kwic")
@Produces(MediaType.APPLICATION_JSON)
public class KWICResource {
    private final AtomicLong counter;

    public KWICResource() {
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public KWICResponse sayHello(@QueryParam("name") Optional<String> name) {
        return new KWICResponse(counter.incrementAndGet(),
                          "Hello KWIC Indexer!");
    }
}