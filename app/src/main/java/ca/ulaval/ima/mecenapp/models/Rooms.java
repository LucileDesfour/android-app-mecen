package ca.ulaval.ima.mecenapp.models;

import java.util.ArrayList;

public class Rooms {
    public static ArrayList<Room> rooms = new ArrayList<>();
    public static Room current_room = null;

    public Rooms() {
        rooms = new ArrayList<>();
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

        public String getMembersInitials(){
            if (members.size() == 1){
                return manager.getUserInitial();
            } else {
                String membersInitials = "";
                for (int i=1;i<members.size();i++){
                    membersInitials = membersInitials.concat(members.get(i).getUserInitial());
                    if (i != members.size()-1) membersInitials = membersInitials.concat(",");
                }
                return membersInitials;
            }
        }
    }
}
