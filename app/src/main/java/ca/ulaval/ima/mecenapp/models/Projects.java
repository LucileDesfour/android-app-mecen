package ca.ulaval.ima.mecenapp.models;

import java.util.ArrayList;
import java.util.Date;

import ca.ulaval.ima.mecenapp.models.network.OrgaNetwork;

public class Projects {

    public static ArrayList<Project> project_list = new ArrayList<>();

    public static class Project {
        private String id;
        private String name;
        private boolean isPulic;
        private Orgas.Orga orga;
        private String localisation;
        private String description;

        public Project(String id_param, String name_params, String isPublic_params, String orgaId, String description_params) {
            this.id = id_param;
            this.name = name_params;
            if (isPublic_params.equals("true")) {
                this.isPulic = true;
            }
            else {
                this.isPulic = false;
            }
            this.orga = OrgaNetwork.getOrga(orgaId);
            this.description = description_params;

        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Orgas.Orga getOrga() {
            return orga;
        }

        public void setOrga(Orgas.Orga orga) {
            this.orga = orga;
        }

        public boolean isPulic() {
            return isPulic;
        }

        public void setPulic(boolean pulic) {
            isPulic = pulic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }



        public String getLocalisation() {
            return localisation;
        }

        public void setLocalisation(String localisation) {
            this.localisation = localisation;
        }
    }
}
