package ca.ulaval.ima.mecenapp.models;

import java.util.Date;

public class Messages {
    public class Message {
        private String id;
        private String content;
        private Users.User user;
        private Date createdDate;
    }
}
