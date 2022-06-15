package fr.carlens.murasaki.back.api.wrapper.models;

import java.util.Map;

public class LocalizedString {

    private Map<String, String> values;

    public LocalizedString() {

    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }
}
