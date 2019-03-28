package ca.ulaval.ima.mecenapp.models;

import java.util.ArrayList;

public class Rooms {

    public class Room {
        public String id;
        public ArrayList<Users.User> members;
        public Users.User manager;
        public String name;
    }
}
