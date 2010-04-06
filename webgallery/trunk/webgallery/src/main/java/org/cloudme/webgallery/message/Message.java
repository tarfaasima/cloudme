package org.cloudme.webgallery.message;

public class Message {
    private final String text;
    private final Object[] params;

    public Message(String text, Object... params) {
        this.text = text;
        this.params = params;
    }

    public String getText() {
        return text;
    }

    public Object[] getParams() {
        return params;
    }
}
