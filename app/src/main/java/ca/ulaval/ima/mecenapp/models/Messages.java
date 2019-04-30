package ca.ulaval.ima.mecenapp.models;

import java.util.ArrayList;

public class Messages {

    public static ArrayList<Message> messages_list = new ArrayList<>();

    public static class Message {
        private String id;
        private String content;
        private Users.User user;

        public Message(String id, String content, Users.User user) {
            this.id = id;
            this.content = content;
            this.user = user;
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

    }
}