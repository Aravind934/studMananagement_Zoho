package SchoolEntity;

import Helper.MergeSort;
import common.IDGenerator;
import Enum.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Management {
    private static HashMap<Integer, Staff> staffDetails = new HashMap<>();
    private static HashMap<Integer, Student> studentsDetails = new HashMap<>();

    private Management() {
    }
    //for making single instance
    private static Management management;

    public static Management getInstance() {
        if (management == null) management = new Management();
        return management;
    }

    //Staff operations

    public Boolean addStaffDetail(String name, Department departmentName, Subject[] subjects) {
        try {
            int staffId = IDGenerator.getStaffId();
            staffDetails.put(staffId, new Staff(name, departmentName, subjects));
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public void getAllStaffDetail() {
        for (int key : staffDetails.keySet()) {
            System.out.println("ID: " + key + "\n" + staffDetails.get(key));
        }
    }

    public Staff getStaffDetail(int staffId) {
        try {
            return staffDetails.get(staffId);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    //Staff operations over

    public Boolean addStudent(String name, Department depName, Marks marks) {
        try {
            this.studentsDetails.put(IDGenerator.getStudentId(), new Student(name, depName, marks));
            System.out.println(studentsDetails);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public void printStudentsRankReport() {
        int i = 0, j = 0;
        Student[] passed = new Student[studentsDetails.size()];
        Student[] failed = new Student[studentsDetails.size()];
        //separate the passed and failed students
        for (int key : studentsDetails.keySet()) {
            Student tempStud = studentsDetails.get(key);
            if (tempStud.getMarks().tamil > 34 && tempStud.getMarks().english > 34 && tempStud.getMarks().maths > 34 && tempStud.getMarks().science > 34 && tempStud.getMarks().socialScience > 34) {
                passed[i++] = tempStud;
                continue;
            }
            failed[j++] = tempStud;
        }

        MergeSort mergeSort = new MergeSort();
        Student[] passedStud = mergeSort.sort(passed);

        System.out.println("Passed students:");
        int rank = 1;
        double prevTotal = 0;
        for (Student item : passedStud) {
            if (item == null) continue;
            Marks mark = item.getMarks();
            if (mark.getTotal() == prevTotal) {
                System.out.println(item.getName() + " " + mark.getTotal() + " " + (--rank) + " PASS");
                rank += 2;
            } else {
                prevTotal = mark.getTotal();
                System.out.println(item.getName() + " " + mark.getTotal() + " " + (rank++) + " PASS");
            }
        }
        System.out.println("Failed students:");
        for (Student item : failed) {
            if (item == null) continue;
            Marks mark = item.getMarks();
            System.out.println(item.getName() + " " + mark.getTotal() + " FAIL");
        }
    }

    public HashMap studentsAverageMarkOfEachSubject() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < 5; i++) {
            map.put("tamil", 0);
            map.put("english", 0);
            map.put("maths", 0);
            map.put("science", 0);
            map.put("social", 0);

        }
        for (Student student : studentsDetails.values()) {
            map.replace("tamil", map.get("tamil") + student.getMarks().tamil);
            map.replace("english", map.get("english") + student.getMarks().english);
            map.replace("maths", map.get("maths") + student.getMarks().maths);
            map.replace("science", map.get("science") + student.getMarks().science);
            map.replace("social", map.get("social") + student.getMarks().socialScience);

        }
        if (studentsDetails.size() > 1) {
            for (String key : map.keySet()) {
                double avg = map.get(key) / studentsDetails.size();
                System.out.println(key + ": " + avg);
            }
        }
        return map;
    }

    public void getAboveAverageMarkGottenStudents() {
        HashMap avgMap = this.studentsAverageMarkOfEachSubject();
        ArrayList<Student> tamilStuds = new ArrayList<>();
        ArrayList<Student> englishStuds = new ArrayList<>();
        ArrayList<Student> mathsStuds = new ArrayList<>();
        ArrayList<Student> scienceStuds = new ArrayList<>();
        ArrayList<Student> socialStuds = new ArrayList<>();
        for (int studId : studentsDetails.keySet()) {
            Student stud = studentsDetails.get(studId);
            Marks mark = stud.getMarks();
            if ((int) avgMap.get("tamil") / studentsDetails.size() <= mark.tamil) {
                tamilStuds.add(stud);
            }
            ;
            if ((int) avgMap.get("english") / studentsDetails.size() <= mark.english) {
                englishStuds.add(stud);
            }
            ;
            if ((int) avgMap.get("maths") / studentsDetails.size() <= mark.maths) {
                mathsStuds.add(stud);
            }
            ;
            if ((int) avgMap.get("science") / studentsDetails.size() <= mark.science) {
                scienceStuds.add(stud);
            }
            ;
            if ((int) avgMap.get("social") / studentsDetails.size() <= mark.socialScience) {
                socialStuds.add(stud);
            }
            ;
        }
        if (tamilStuds.size() > 1) {
            System.out.println("------  Above average in Tamil  ------");
            for (Student stud : tamilStuds) {
                System.out.println(stud.getName() + ": " + stud.getMarks().tamil);
            }
        }
        if (englishStuds.size() > 1) {
            System.out.println("------  Above average in English  ------");
            for (Student stud : englishStuds) {
                System.out.println(stud.getName() + ": " + stud.getMarks().english);
            }
        }
        if (mathsStuds.size() > 1) {
            System.out.println("------  Above average in Maths  ------");
            for (Student stud : mathsStuds) {
                System.out.println(stud.getName() + ": " + stud.getMarks().maths);
            }
        }
        if (scienceStuds.size() > 1) {
            System.out.println("------  Above average in Science  ------");
            for (Student stud : scienceStuds) {
                System.out.println(stud.getName() + ": " + stud.getMarks().science);
            }
        }
        if (socialStuds.size() > 1) {
            System.out.println("------  Above average in Social Science  ------");
            for (Student stud : socialStuds) {
                System.out.println(stud.getName() + ": " + stud.getMarks().socialScience);
            }
        }
    }

    public void getTopScorerStudentsInEachSubject() {
        class Temp {
            public ArrayList<Integer> idList = new ArrayList<>();
            public int mark;

            public Temp(int mark) {
                this.mark = mark;
            }
        }
        HashMap<String, Temp> map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            map.put("tamil", new Temp(0));
            map.put("english", new Temp(0));
            map.put("maths", new Temp(0));
            map.put("science", new Temp(0));
            map.put("social", new Temp(0));

        }
        for (int id : studentsDetails.keySet()) {
            Marks mark = studentsDetails.get(id).getMarks();
            Temp tamil = map.get("tamil");
            Temp english = map.get("english");
            Temp maths = map.get("maths");
            Temp science = map.get("science");
            Temp social = map.get("social");

            if (mark.tamil >= tamil.mark) {
                if (mark.tamil > tamil.mark) {
                    tamil.idList.clear();
                }
                tamil.idList.add(id);
                tamil.mark = mark.tamil;
            }
            if (mark.english >= english.mark) {
                if (mark.english > english.mark) {
                    english.idList.clear();
                }
                english.idList.add(id);
                english.mark = mark.english;
            }
            if (mark.maths >= maths.mark) {
                if (mark.maths > maths.mark) {
                    maths.idList.clear();
                }
                maths.idList.add(id);
                maths.mark = mark.maths;
            }
            if (mark.science >= science.mark) {
                if (mark.science > science.mark) {
                    science.idList.clear();
                }
                science.idList.add(id);
                science.mark = mark.science;
            }
            if (mark.socialScience >= social.mark) {
                if (mark.socialScience > social.mark) {
                    social.idList.clear();
                }
                social.idList.add(id);
                social.mark = mark.socialScience;
            }
        }
        for (String sub : map.keySet()) {
            System.out.println(sub + ": ");
            Temp temp = map.get(sub);
            for (int id : temp.idList) {
                System.out.println(studentsDetails.get(id).getName() + ": " + temp.mark);
            }
        }
    }

    public void getStudentsAcademicDetails() {
        class Academic {
            public Student student;
            public String eduStatus;

            public Academic(Student student, int mark) {
                this.student = student;
                this.eduStatus = (mark > 35) ? "PASS" : "FAIL";
            }
        }
        ArrayList<Academic> tamil = new ArrayList<>();
        ArrayList<Academic> english = new ArrayList<>();
        ArrayList<Academic> maths = new ArrayList<>();
        ArrayList<Academic> science = new ArrayList<>();
        ArrayList<Academic> social = new ArrayList<>();

        for (int key : studentsDetails.keySet()) {
            Student stud = studentsDetails.get(key);
            Marks mark = stud.getMarks();
            tamil.add(new Academic(stud, mark.tamil));
            english.add(new Academic(stud, mark.english));
            maths.add(new Academic(stud, mark.maths));
            science.add(new Academic(stud, mark.science));
            social.add(new Academic(stud, mark.socialScience));
        }

        System.out.println("TAMIL :");
        for (Academic academic : tamil) {
            System.out.println(academic.student.getName() + " " + academic.student.getDepartment() + " " + academic.eduStatus + " " + academic.student.getMarks().tamil);
        }
        System.out.println("ENGLISH :");
        for (Academic academic : english) {
            System.out.println(academic.student.getName() + " " + academic.student.getDepartment() + " " + academic.eduStatus + " " + academic.student.getMarks().english);
        }
        System.out.println("MATHS :");
        for (Academic academic : maths) {
            System.out.println(academic.student.getName() + " " + academic.student.getDepartment() + " " + academic.eduStatus + " " + academic.student.getMarks().maths);
        }
        System.out.println("SCIENCE :");
        for (Academic academic : science) {
            System.out.println(academic.student.getName() + " " + academic.student.getDepartment() + " " + academic.eduStatus + " " + academic.student.getMarks().science);
        }
        System.out.println("SOCIAL SCIENCE :");
        for (Academic academic : social) {
            System.out.println(academic.student.getName() + " " + academic.student.getDepartment() + " " + academic.eduStatus + " " + academic.student.getMarks().socialScience);
        }
    }

    public void getStudentsAboveAverageDetails() {
        HashMap avg = this.studentsAverageMarkOfEachSubject();
        class Detail {
            public String name;
            public ArrayList<Subject> subjects = new ArrayList<>();
        }
        ArrayList<Detail> list = new ArrayList<Detail>();
        for (int key : studentsDetails.keySet()) {
            Student stud = studentsDetails.get(key);
            Marks mark = stud.getMarks();
            Detail detail = new Detail();
            detail.name = stud.getName();
            if (mark.tamil >= (int) avg.get("tamil") / studentsDetails.size()) {
                detail.subjects.add(Subject.TAMIL);
            }
            if (mark.english >= (int) avg.get("english") / studentsDetails.size()) {
                detail.subjects.add(Subject.ENGLISH);
            }
            if (mark.maths >= (int) avg.get("maths") / studentsDetails.size()) {
                detail.subjects.add(Subject.MATHS);
            }
            if (mark.science >= (int) avg.get("science") / studentsDetails.size()) {
                detail.subjects.add(Subject.SCIENCE);
            }
            if (mark.socialScience >= (int) avg.get("social") / studentsDetails.size()) {
                detail.subjects.add(Subject.SOCIAL_SCIENCE);
            }
            list.add(detail);
        }
        for (Detail detail : list) {
            System.out.println(detail.name + " " + detail.subjects.toString() + " " + detail.subjects.size());
        }
    }

    public Boolean removeStaff(int staffId) {
        try {
            staffDetails.remove(staffId);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Boolean removeStudent(int staffId) {
        try {
            studentsDetails.remove(staffId);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
