package SchoolEntity.controllers;

import SchoolEntity.*;
import common.Response;
import Enum.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class StaffController {

    private Connection con;

    Properties properties = new Properties();

    public StaffController() throws IOException {
        Management management = Management.getInstance();
        this.con = management.con;
        Reader reader = new FileReader("/home/roosevelt/studManagement/src/common/queries.properties");
        this.properties.load(reader);
    }

    public Response addStaffDetail(String name, Department departmentName, Subject[] subjects) {
        try {
            //variable declarations

            Status status = Status.OK;
            int depId;
            PreparedStatement preparedStatement;
            Statement st = con.createStatement();
            String query, msg = "Staff details added successfully!";
            ResultSet rs;

            //To get department ID
            query = properties.getProperty("GET_DEPARTMENT_ID_QUERY");
            preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, departmentName + "");
            rs = preparedStatement.executeQuery();
            rs.next();
            depId = rs.getInt("id");

            //Insert into staff table

            query = properties.getProperty("INSERT_STAFF_QUERY");
            preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, depId);
            preparedStatement.execute();
            rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int staffId = rs.getInt(1);

            //Map staff id and subject ID

            for (Subject sub : subjects) {
                query = properties.getProperty("GET_SUBJECT_ID_QUERY");
                preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, sub + "");
                rs = preparedStatement.executeQuery();
                rs.next();
                query = properties.getProperty("INSERT_STAFF_AND_SUBJECT_MAPPER_QUERY");
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, staffId);
                preparedStatement.setInt(2, rs.getInt(1));
                preparedStatement.execute();
            }

            return new Response(status.getCode(), msg, null, null);
        } catch (Exception e) {
            System.out.println(e);
            return new Response(Status.BAD_REQUEST.getCode(), e.getMessage(), null, null);
        }
    }

    public Response getAllStaffDetail() {
        try {
            //varialbe declarations
            Status status = Status.OK;
            String msg = "Successfully fetched!";
            ArrayList<Staff> data = new ArrayList<>();
            PreparedStatement preparedStatement;
            String query;
            ResultSet rs;

            //fetch staff details
            query = properties.getProperty("FETCH_ALL_STAFF_QUERY");
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setId(rs.getInt("id"));
                staff.setName(rs.getString("name"));
                data.add(staff);
            }
            return new Response(status.getCode(), msg, data, null);
        } catch (Exception ex) {
            return new Response(Status.BAD_REQUEST.getCode(), ex.getMessage(), null, null);
        }
    }

    public Response getStaffDetail(int staffId) {
        try {
            //variable declarations
            Status status = Status.OK;
            String msg = "Successfully fetched!";
            PreparedStatement preparedStatement;
            String query;
            ResultSet rs;

            //fetch staff detail
            query = properties.getProperty("FETCH_STAFF_DETAIL");
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, staffId);
            rs = preparedStatement.executeQuery();
            rs.next();

            //making Stff object
            Staff staff = new Staff();
            staff.setId(rs.getInt("id"));
            staff.setName(rs.getString("name"));
            staff.setDepartment(Department.valueOf(rs.getString("departmentName")));

            //fetch subject details based on the staff
            query = properties.getProperty("FETCH_STAFF_SUBJECTS_DETAIL");
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, staff.getId());
            rs = preparedStatement.executeQuery();
            ArrayList<Subject> subjects = new ArrayList<>();
            while (rs.next()) {
                subjects.add(Subject.valueOf(rs.getString("name")));
            }
            staff.setSubjects(subjects);

            return new Response(status.getCode(), msg, null, staff);
        } catch (Exception e) {
            return new Response(Status.BAD_REQUEST.getCode(), e.getMessage(), null, null);
        }
    }

    public Response removeStaff(int staffId) {
        try {
            //variables
            String query;
            PreparedStatement preparedStatement;
            //delete the recored in staff table
            query = properties.getProperty("REMOVE_STAFF_QUERY");
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, staffId);
            preparedStatement.executeUpdate();
            return new Response(Status.OK.getCode(), "Staff removed successfully!", null, null);
        } catch (Exception e) {
            return new Response(Status.BAD_REQUEST.getCode(), e.getMessage(), null, null);
        }
    }

}
