package org.example;


import com.fasterxml.jackson.annotation.JsonProperty;

public class SlackRresponse{
    @JsonProperty("ok")
    public boolean getOk() {
        return this.ok; }
    public void setOk(boolean ok) {
        this.ok = ok; }
    boolean ok;
    @JsonProperty("user")
    public User getUser() {
        return this.user; }
    public void setUser(User user) {
        this.user = user; }
    User user;
}
