package ca.ulaval.ima.mecenapp.models;

import java.util.Date;

public class Projects {

    public class Project {
        private String id;
        private Orgas.Orga orga;
        private boolean isPulic;
        private String name;
        private String description;
        private Date startDate;
        private Date endDate;
        private String localisation;
    }
}
