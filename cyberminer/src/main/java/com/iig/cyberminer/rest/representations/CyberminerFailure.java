package com.iig.cyberminer.rest.representations;

import java.util.Map;

public class CyberminerFailure implements CyberminerResponse {
    private final long id;
    private final String content;

    public CyberminerFailure( long id, String content ) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}