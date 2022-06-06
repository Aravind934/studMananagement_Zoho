package Helper;

import SchoolEntity.*;
import Enum.*;
import SchoolEntity.controllers.StaffController;
import SchoolEntity.controllers.StudentController;
import common.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Helper {
    Scanner input = new Scanner(System.in);

    //initialize the controllers
    StudentController studentController = new StudentController();
    StaffController staffController = new StaffController();

    public Helper() throws IOException {
    }

    public void addStaffDetail() {
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
            Response res = staffController.addStaffDetail(staffName, department, subjects);
            System.out.println("Status : " + res.status + "\nMessage :" + res.message);
        } catch (Exception e) {
            System.out.println("Status : " + 400 + "\nMessage :" + e.getMessage());
        }
    }

    public void printAllStaffDetail() {
        Response res = staffController.getAllStaffDetail();
        System.out.println("Status : " + res.status + "\nMessage :" + res.message);
        if (res.status == 200) {
            for (Object item : res.data) {
                Staff staff = (Staff) item;
                System.out.println("Staff ID :" + staff.getId() + " Staff Name:" + staff.getName());
            }
        }
    }

    public void printStaffDetail() {
        System.out.println("Enter the staff ID");
        Response res = staffController.getStaffDetail(input.nextInt());
        System.out.println("Status : " + res.status + "\nMessage :" + res.message);
        if (res.status == 200) {
            Staff staff = ((Staff) res.singleData);
            System.out.println("Staff Name: ");
            System.out.println(staff.getName());
            System.out.println("Staff Department");
            System.out.println(staff.getDepartment());
            System.out.println("Handling subjects details:-");
            for (Subject item : staff.getHandlingSubject()) {
                System.out.println(item);
            }
        }
    }

    public void removeStaff() {
        System.out.println("Enter the staff ID");
        int id = input.nextInt();
        Response res = staffController.removeStaff(id);
        System.out.println("Status : " + res.status + "\nMessage :" + res.message);
    }

    public void addStudents() {
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
            } else {
                Marks marks = new Marks(tamil, english, maths, science, socialScience);
                Response res = studentController.addStudent(name, department, marks);
                System.out.println("Status : " + res.status + "\nMessage :" + res.message);
            }
        }
    }

    public void removeStudent() {
        System.out.println("Enter the student ID");
        int id = input.nextInt();
        Response res = studentController.removeStudent(id);
        System.out.println("Status : " + res.status + "\nMessage :" + res.message);
    }

    public void PrintRankReport() {
        studentController.printStudentsRankReport();
    }

    public void printAverageOfEachSub() {
        HashMap<String, Integer> map = studentController.getStudentsAverageMarkOfEachSubject();
        for (String item : map.keySet()) {
            System.out.println(item + " : " + map.get(item));
        }
    }

    public void printAboveAvg() {
        studentController.printAboveAverageMarkGottenStudents();
    }

    public void printTopScorers() {
        studentController.printTopScorerStudentsInEachSubject();
    }

    public void printStudentAcademicDetails() {
        studentController.printStudentsAcademicDetails();
    }

    public void printStudentsAboveAverageDetails() {
        studentController.printStudentsAboveAverageDetails();
    }

}
