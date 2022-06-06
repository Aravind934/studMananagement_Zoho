package SchoolEntity.controllers;

import SchoolEntity.Management;
import SchoolEntity.Marks;
import common.Response;
import Enum.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class StudentController {

    private Connection con;
    Properties properties = new Properties();

    public StudentController() throws IOException {
        Management management = Management.getInstance();
        this.con = management.con;
        Reader reader = new FileReader("/home/roosevelt/studManagement/src/common/queries.properties");
        this.properties.load(reader);
    }

    public Response addStudent(String name, Department depName, Marks marks) {
        try {
            //variable declarations

            String query;
            PreparedStatement preparedStatement;
            ResultSet rs;

            //Add student data into student table
            //1.To get department id

            query = properties.getProperty("GET_DEPARTMENT_ID_QUERY");
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, depName + "");
            rs = preparedStatement.executeQuery();
            rs.next();
            int depId = rs.getInt("id");

            //2.To add marks and get marks id

            query = properties.getProperty("INSERT_MARKS_DETAIL_QUERY");
            preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, marks.tamil);
            preparedStatement.setInt(2, marks.english);
            preparedStatement.setInt(3, marks.maths);
            preparedStatement.setInt(4, marks.science);
            preparedStatement.setInt(5, marks.socialScience);
            preparedStatement.setInt(6, (int) marks.getTotal());
            preparedStatement.execute();
            rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int marksId = rs.getInt(1);

            //Store student data

            query = properties.getProperty("INSERT_STUDENT_DETAIL_QUERY");
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, depId);
            preparedStatement.setInt(3, marksId);
            preparedStatement.execute();

            return new Response(Status.OK.getCode(), "Student added successfully!", null, null);
        } catch (Exception e) {
            return new Response(Status.BAD_REQUEST.getCode(), e.getMessage(), null, null);
        }
    }

    public Response removeStudent(int studId) {
        try {
            //variable declarations

            String query;
            PreparedStatement preparedStatement;
            ResultSet rs;

            //remove the student record
            query = properties.getProperty("DELETE_STUDENT_QUERY");
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, studId);
            preparedStatement.execute();
            return new Response(Status.OK.getCode(), "Student removed successfully!", null, null);
        } catch (Exception e) {
            System.out.println(e);
            return new Response(Status.BAD_REQUEST.getCode(), e.getMessage(), null, null);
        }
    }

    public void printStudentsRankReport() {
        try {
            //variables declarations
            String query;
            ResultSet rs;
            int prevTotal = 0;
            int rank = 1;
            PreparedStatement preparedStatement;
            // fetch students report from student table
            query = properties.getProperty("FETCH_STUDENT_RANK_DETAIL");
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            System.out.println("STUDENTS RANKING REPORT >>");
            while (rs.next()) {
                String name = rs.getString("name");
                int total = rs.getInt("total");
                String report = rs.getString("report");
                if (total == prevTotal && report.equals("PASS")) {
                    System.out.println(name + " " + total + " " + (--rank) + " " + report);
                    rank += 2;
                    continue;
                }
                if (report.equals("FAIL")) {
                    System.out.println(name + " " + total + " " + null + " " + report);
                    continue;
                } else {
                    System.out.println(name + " " + total + " " + (rank++) + " " + report);
                    prevTotal = total;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public HashMap getStudentsAverageMarkOfEachSubject() {
        try {
            //Variable declarations
            String query;
            PreparedStatement preparedStatement;
            ResultSet rs;
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            //Fetch avg mark details from mark table
            query = properties.getProperty("FETCH_AVG_MARK_DETAILS");
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            rs.next();
            map.put("TAMIL", rs.getInt("TAMIL"));
            map.put("ENGLISH", rs.getInt("ENGLISH"));
            map.put("MATHS", rs.getInt("MATHS"));
            map.put("SCIENCE", rs.getInt("SCIENCE"));
            map.put("SOCIAL_SCIENCE", rs.getInt("SOCIAL_SCIENCE"));
            return map;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public void printAboveAverageMarkGottenStudents() {
        try {
            String[] subjects = {"tamil", "english", "maths", "science", "socialScience"};
            for (String sub : subjects) {
                this.printAboveAvgStudentDetail(sub);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void printAboveAvgStudentDetail(String subject) {
        try {
            //variable declarations

            String query;
            PreparedStatement preparedStatement;
            ResultSet rs;

            //To get avg mark of subject

            query = "SELECT AVG(" + subject + ")as avg FROM marks";
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            rs.next();
            int avg = rs.getInt("avg");

            //get those students

            query = "select name," + subject + " from student join marks on student.marksId=marks.id where " + subject + " >= " + avg;
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            //print the data

            System.out.println("------  Above average in  " + subject + "  ------");
            while (rs.next()) {
                System.out.println(rs.getString(1));
                System.out.println(rs.getInt(2));
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void printTopScorerStudentsInEachSubject() {
        try {
            String[] subjects = {"tamil", "english", "maths", "science", "socialScience"};
            for (String sub : subjects) {
                this.printTopScorer(sub);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void printTopScorer(String subject) {
        try {
            //variable declarations

            String query;
            PreparedStatement preparedStatement;
            ResultSet rs;

            //get the top score

            query = "select max(" + subject + ") as score from marks";
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            rs.next();
            int score = rs.getInt(1);

            //Get the students detail and print it
            query = "select name from student join marks on student.marksId=marks.id where " + subject + " >=" + score;
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            System.out.println(subject.toUpperCase());
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + score);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void printStudentsAcademicDetails() {
        try {
            String[] subjects = {"tamil", "english", "maths", "science", "socialScience"};
            for (String sub : subjects) {
                this.printSubjectWiseAcademicDetails(sub);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void printSubjectWiseAcademicDetails(String subject) {
        try {
            //variable declarations

            String query;
            PreparedStatement preparedStatement;
            ResultSet rs;

            //fetch details

            query = "SELECT name," + subject + ",if(" + subject + ">=35 ,\"PASS\",\"FAIL\")as report from student as s join marks as m on s.marksId=m.id";
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            //print the details
            System.out.println(subject.toUpperCase());
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getInt(2) + " " + rs.getString(3));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void printStudentsAboveAverageDetails() {
        try {
            //variable declarations
            String query;
            PreparedStatement preparedStatement;
            ResultSet rs;
            String name = "";
            ArrayList<String> subjects = new ArrayList<>();
            HashMap<String, Integer> map = this.getStudentsAverageMarkOfEachSubject();
            //fetch details
            query = properties.getProperty("FET_STUDENT_ABOVE_AVG_DETAIL");
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, map.get("TAMIL"));
            preparedStatement.setInt(2, map.get("ENGLISH"));
            preparedStatement.setInt(3, map.get("MATHS"));
            preparedStatement.setInt(4, map.get("SCIENCE"));
            preparedStatement.setInt(5, map.get("SOCIAL_SCIENCE"));
            System.out.println(preparedStatement.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                name = rs.getString(1);
                if (rs.getString(2).equals("true")) subjects.add("TAMIL");
                if (rs.getString(3).equals("true")) subjects.add("ENGLISH");
                if (rs.getString(4).equals("true")) subjects.add("MATHS");
                if (rs.getString(5).equals("true")) subjects.add("SCIENCE");
                if (rs.getString(6).equals("true")) subjects.add("SOCIAL_SCIENCE");
                System.out.println(name + " " + subjects + " " + subjects.size());
                subjects.clear();
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
