package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.StringJoiner;

public class User {
    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
            .add("id='" + id + "'")
            .toString();
    }
}