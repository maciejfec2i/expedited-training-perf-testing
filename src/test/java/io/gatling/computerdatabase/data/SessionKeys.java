package io.gatling.computerdatabase.data;

public enum SessionKeys {

    HTML_RESPONSE("a3dac7ea-1893-4f7f-a5bb-abe66f60f98a"),
    COMPUTER_URL("310e0d59-7286-4a31-b2d9-8754aa0a6f5d");

    private String key;

    SessionKeys(String key) {
        this.key = key;
    }

    public String key() {
        return this.key;
    }
}
