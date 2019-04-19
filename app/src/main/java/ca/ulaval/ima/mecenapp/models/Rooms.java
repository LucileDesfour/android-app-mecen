package ca.ulaval.ima.mecenapp.models;

import java.util.ArrayList;

public class Rooms {
    public static ArrayList<Room> rooms = new ArrayList<>();
    public Room current_room = null;

    public Rooms() {
        this.rooms = new ArrayList<>();
    }


    public static class Room {
        public String id;
        public ArrayList<Users.User> members;
        public Users.User manager;
        public String name ="";

        public Room() {
        }

        public Room(String id, ArrayList<Users.User> members, Users.User manager, String name) {
            this.id = id;
            this.members = members;
            this.manager = manager;
            this.name = name;
        }

        public Room(String id, Users.User manager) {
            this.id = id;
            this.manager = manager;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public ArrayList<Users.User> getMembers() {
            return members;
        }

        public void setMembers(ArrayList<Users.User> members) {
            this.members = members;
        }

        public Users.User getManager() {
            return manager;
        }

        public void setManager(Users.User manager) {
            this.manager = manager;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
