package ca.ulaval.ima.mecenapp.models;

import java.util.Date;

public class Message {
        private String id;
        private String content;
        private Users.User user;
        private Date createdDate;

    public Message(String id, String content, Users.User user, Date createdDate) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Users.User getUser() {
        return user;
    }

    public void setUser(Users.User user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
