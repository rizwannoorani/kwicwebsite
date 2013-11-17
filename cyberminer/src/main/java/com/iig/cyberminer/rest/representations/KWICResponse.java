package com.iig.cyberminer.rest.representations;

public class KWICResponse {
    private final long id;
    private final String content;

    public KWICResponse(long id, String content) {
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