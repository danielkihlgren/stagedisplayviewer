package se.pingstteknik.propresenter.stagedisplayviewer.model;

public class Field {

    private final String identifier;
    private final String data;

    public Field(String identifier, String data) {
        this.identifier = identifier;
        this.data = data;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getData() {
        return data;
    }
}
