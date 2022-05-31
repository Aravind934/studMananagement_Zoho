package Helper;

import SchoolEntity.*;
import Enum.*;

import java.util.Scanner;

public class Helper {
    Scanner input = new Scanner(System.in);
    Management management = Management.getInstance();

    public Boolean addStudents() {
        System.out.println("Enter the number of students");
        int count = input.nextInt();
        for (int i = 0; i < count; i++) {
            System.out.println("Enter the student " + (i + 1) + " name:");
            String name = input.next();
            System.out.println("Enter the department name:");
            Department department = Department.valueOf(input.next().toUpperCase());
            System.out.println("Enter the Tamil mark:");
            int tamil = input.nextInt();
            System.out.println("Enter the English mark:");
            int english = input.nextInt();
            System.out.println("Enter the Maths mark:");
            int maths = input.nextInt();
            System.out.println("Enter the Science mark:");
            int science = input.nextInt();
            System.out.println("Enter the Social Science mark:");
            int socialScience = input.nextInt();
            if (tamil < 1 || tamil > 100 || english < 1 || english > 100 || maths < 1 || maths > 100 || science < 1 || science > 100 || socialScience < 1 || socialScience > 100) {
                System.out.println("invalid Marks!");
                return false;
            }
            Marks marks = new Marks(tamil, english, maths, science, socialScience);
            management.addStudent(name, department, marks);
        }
        return true;
    }

    public void PrintRankReport() {
        management.printStudentsRankReport();
    }

    public Boolean addStaffDetail() {
        try {
            System.out.println("Enter staff name");
            String staffName = input.next();
            System.out.println("Enter his/her department name");
            Department department = Department.valueOf(input.next().toUpperCase());
            System.out.println("How many subjects he/she will handle?");
            int count = input.nextInt();
            Subject[] subjects = new Subject[count];
            for (int i = 0; i < count; i++) {
                System.out.println("Subject " + (i + 1) + " name");
                subjects[i] = Subject.valueOf(input.next().toUpperCase());
            }
            return management.addStaffDetail(staffName, department, subjects);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public void printAllStaffDetail() {
        management.getAllStaffDetail();
    }

    public void printStaffDetail() {
        System.out.println("Enter the staff ID");
        Staff staff = management.getStaffDetail(input.nextInt());
        System.out.println(staff);
        System.out.println("Handling subjects details:-");
        for (Subject sub : staff.getHabndlingubjects()) {
            System.out.println(sub);
        }
    }

    public void printAverageOfEachSub() {
        management.studentsAverageMarkOfEachSubject();
    }

    public void printAboveAvg() {
        management.getAboveAverageMarkGottenStudents();
    }

    public void printTopScorers() {
        management.getTopScorerStudentsInEachSubject();
    }

    public void printStudentAcademicDetails() {
        management.getStudentsAcademicDetails();
    }

    public void printStudentsAboveAverageDetails() {
        management.getStudentsAboveAverageDetails();
    }

    public void removeStudent() {
        System.out.println("Enter the student ID");
        int id = input.nextInt();
        management.removeStudent(id);
    }

    public void removeStaff() {
        System.out.println("Enter the staff ID");
        int id = input.nextInt();
        management.removeStaff(id);
    }
}
