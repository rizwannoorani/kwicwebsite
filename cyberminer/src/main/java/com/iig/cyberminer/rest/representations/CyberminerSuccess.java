package com.iig.cyberminer.rest.representations;

import java.util.Map;

public class CyberminerSuccess implements CyberminerResponse {
    private final long id;
    private final Map<String, String> content;

    public CyberminerSuccess(long id, Map<String, String> content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public Map<String, String> getContent() {
        return content;
    }
}