package common;

public class IDGenerator {
    private static int depId = 0;
    private static int markId = 0;
    private static int staffId = 0;
    private static int studentId = 0;
    private static int subId = 0;

    public static int getDepId() {
        return ++depId;
    }

    public static int getMarkId() {
        return ++markId;
    }

    public static int getStaffId() {
        return ++staffId;
    }

    public static int getStudentId() {
        return ++studentId;
    }

    public static int getSubId() {
        return ++subId;
    }
}
