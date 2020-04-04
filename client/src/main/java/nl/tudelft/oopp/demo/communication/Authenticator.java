package nl.tudelft.oopp.demo.communication;

public abstract class Authenticator {
    static String SESSION_COOKIE = "";
    static String ROLE = "";
    public static long ID = 0;
    public static String USERNAME = "";

    public static boolean isAdmin() {
        return ROLE.contains("ADMIN");
    }

    public static boolean isUser() {
        return ROLE.contains("USER");
    }

    public static boolean isEmployee() {
        return ROLE.contains("EMPLOYEE");
    }
}
