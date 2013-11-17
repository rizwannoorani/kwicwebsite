package com.iig.cyberminer.rest.resources;

import com.iig.cyberminer.kwic.KwicService;
import com.iig.cyberminer.rest.representations.KWICResponse;
import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/kwic")
@Produces(MediaType.APPLICATION_JSON)
public class KWICResource {
    private final AtomicLong counter;
    private final KwicService kwic;

    public KWICResource() {
        this.counter = new AtomicLong();
        kwic = new KwicService(); //pass in db info here?
    }

    @GET
    @Timed
    public KWICResponse sayHello(@QueryParam("name") Optional<String> name) {
        return new KWICResponse(
            counter.incrementAndGet(),
            "Post to this endpoint to add entries. ( {url: \"\", content: \"\"} )");
    }

    @POST
    public KWICResponse addToIndex( ) {
        return new KWICResponse(
            counter.incrementAndGet(),
            kwic.processData( "url", "content" ));
    }
}