package ca.ulaval.ima.mecenapp.models;

import java.util.ArrayList;

public class Users {
    public static ArrayList<User> users_list = new ArrayList<>();

    public static class User {
        private String id;
        private String email;
        private String password;
        private String firstName;
        private String lastName;

        public User(String id, String email, String password, String firstName, String lastName) {
            this.id = id;
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public User(String id, String email, String firstName, String lastName) {
            this.id = id;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getUserInitial(){
            return firstName.substring(0,1).toUpperCase() + "." + lastName;
        }
    }
}
