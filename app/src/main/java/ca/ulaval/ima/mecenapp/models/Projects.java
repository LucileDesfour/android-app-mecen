package ca.ulaval.ima.mecenapp.models;

import java.util.ArrayList;
import java.util.Date;

import ca.ulaval.ima.mecenapp.models.network.OrgaNetwork;

public class Projects {

    public static ArrayList<Project> project_list = new ArrayList<>();
    public static Project currentProject = null;

    public static class Project {
        private String id;
        private String name;
        private boolean isPulic;
        private Orgas.Orga orga;
        private String description;
        private ArrayList<String> ressources;
        private ArrayList<String> domaine;

        public Project(String id_param, String name_params, String isPublic_params, String orgaId, String description_params, ArrayList<String> ressources_params, ArrayList<String> domains_params) {
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
            this.ressources = ressources_params;
            this.domaine = domains_params;
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

        public ArrayList<String> getRessources() {
            return ressources;
        }

        public void setRessources(ArrayList<String> ressources) {
            this.ressources = ressources;
        }

        public ArrayList<String> getDomaine() {
            return domaine;
        }

        public void setDomaine(ArrayList<String> domaine) {
            this.domaine = domaine;
        }
    }
}
