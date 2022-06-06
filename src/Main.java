import DB.DbConnection;
import Helper.*;
import SchoolEntity.Management;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        Helper helper = new Helper();
        boolean isClose = true;
        while (isClose) {
            System.out.println(
                    "\n1.Add Staff\n" +
                            "2.Remove Staff\n" +
                            "3.Print All Staff Details\n" +
                            "4.Print Staff Detail\n" +
                            "5.Add Student\n" +
                            "6.Remove Student\n" +
                            "7.Print Rank Based Report\n" +
                            "8.Average Mark Of Each Subject\n" +
                            "9.Print Above Average Mark Of Each Subject\n" +
                            "10.Print Top Scorers Details\n" +
                            "11.Print Student's Academic Details\n" +
                            "12.Print Students Above Average Subject Details With Count\n" +
                            "13.Exit");
            System.out.println("Enter the choice you want");
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    helper.addStaffDetail();
                    break;
                case 2:
                    helper.removeStaff();
                    break;
                case 3:
                    helper.printAllStaffDetail();
                    break;
                case 4:
                    helper.printStaffDetail();
                    break;
                case 5:
                    helper.addStudents();
                    break;
                case 6:
                    helper.removeStudent();
                    break;
                case 7:
                    helper.PrintRankReport();
                    break;
                case 8:
                    helper.printAverageOfEachSub();
                    break;
                case 9:
                    helper.printAboveAvg();
                    break;
                case 10:
                    helper.printTopScorers();
                    break;
                case 11:
                    helper.printStudentAcademicDetails();
                    break;
                case 12:
                    helper.printStudentsAboveAverageDetails();
                    break;
                case 13:
                    isClose = false;
                    break;
                default:
                    isClose = false;
                    break;
            }
        }
    }
}