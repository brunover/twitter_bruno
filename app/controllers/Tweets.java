package controllers;

/**
 * Created by bruno_000 on 04/03/2017.
 */
public class Tweets {
    String name;
    String text;

    public Tweets(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
