package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String userId;
        try {
            userId = Slack.sendPOST();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Slack.sendGET(userId, "hello from java");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}