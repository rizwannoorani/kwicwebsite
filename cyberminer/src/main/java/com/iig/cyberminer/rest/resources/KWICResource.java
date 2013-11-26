package com.iig.cyberminer.rest.resources;

import com.iig.cyberminer.kwic.KwicComponent;
import com.iig.cyberminer.rest.representations.KWICResponse;
import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/index")
@Produces(MediaType.APPLICATION_JSON)
public class KWICResource {
    private final AtomicLong counter;
    private final KwicComponent kwicComponent;

    public KWICResource() {
        this.counter = new AtomicLong();
        kwicComponent = new KwicComponent(5);
    }

    @GET
    @Timed
    public KWICResponse sayHello(@QueryParam("name") Optional<String> name) {
        return new KWICResponse(
            counter.incrementAndGet(),
            "Post to this endpoint to add entries. ( {url: \"\", content: \"\"} )");
    }

    @POST
    public KWICResponse addToIndex(@FormParam("url") String url, @FormParam("contents") String contents ) {
        try {
            System.out.println( "Received post to indexer w/ url =" + url + " and contents=" + contents );
            kwicComponent.processData( url, contents );
            return new KWICResponse( counter.incrementAndGet(), "Successfully added to KWIC index." );
        } catch( Exception e ) {
            return new KWICResponse( counter.incrementAndGet(), "Unable to add to KWIC index." );
        }
    }
}