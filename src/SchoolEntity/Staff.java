package SchoolEntity;

import Enum.*;

import java.util.ArrayList;

public class Staff {

    private int id;
    private String name;
    private Department department;
    private ArrayList<Subject> handlingSubjects = new ArrayList<Subject>();

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDepartment(Department depName) {
        this.department = depName;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        for (Subject sub : subjects) {
            this.handlingSubjects.add(sub);
        }
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<Subject> getHandlingSubject() {
        return this.handlingSubjects;
    }

    public String getName() {
        return this.name;
    }

    public Department getDepartment() {
        return this.department;
    }


    public String toString() {
        return "Staff Name: " + this.name + " Department name:" + this.department;
    }
}
