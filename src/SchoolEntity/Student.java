package SchoolEntity;

import Enum.*;

public class Student {
    private final String name;
    private final Marks marks;
    private final Department department;

    public Student(String name, Department department, Marks marks) {
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "\n" + "Name: " + this.name + "\n" + "Department Name: " + this.department + "\n" + "Marks: " + this.marks + "\n";
    }

    public Marks getMarks() {
        return this.marks;
    }

    public String getName() {
        return this.name;
    }

    public Department getDepartment() {
        return this.department;
    }
}
