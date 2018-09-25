package com.pairing4good.petclinic.message;

import java.util.Objects;

public class Message {

    private Level level;
    private String message;

    public Message(Level level, String message) {
        this.level = level;
        this.message = message;
    }

    public String getLevel() {
        return level.toString();
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return level == message1.level &&
                Objects.equals(message, message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, message);
    }
}
