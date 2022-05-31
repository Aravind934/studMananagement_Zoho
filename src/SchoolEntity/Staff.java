package SchoolEntity;

import Enum.*;

import java.util.ArrayList;

public class Staff {
    private final String name;
    private final Department department;
    private ArrayList<Subject> handlingsubjects = new ArrayList<Subject>();

    public Staff(String name, Department department, Subject[] subjects) {
        this.name = name;
        this.department = department;
        for (Subject sub : subjects) {
            this.handlingsubjects.add(sub);
        }
    }

    public ArrayList<Subject> getHabndlingubjects() {
        return this.handlingsubjects;
    }

    public String toString() {
        return "Staff Name: " + this.name;
    }
}
