package fr.carlens.murasaki.logic.api.models;

import java.util.List;

public class Chapter {
    private String id;
    private String type;
    private ChapterAttributes attributes;
    private List<Relationship> relationships;

    public Chapter() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ChapterAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ChapterAttributes attributes) {
        this.attributes = attributes;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }
}
