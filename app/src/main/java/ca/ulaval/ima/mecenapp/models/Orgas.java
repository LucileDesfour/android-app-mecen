package ca.ulaval.ima.mecenapp.models;

import java.util.ArrayList;

public class Orgas {
    public static ArrayList<Orga> orgas_list = new ArrayList<>();
    public static ArrayList<String> orgas_names = new ArrayList<>();

    public static class Orga {

        public Orga(String name_params, String id_params) {
            this.name = name_params;
            this.id = id_params;
        }

        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
