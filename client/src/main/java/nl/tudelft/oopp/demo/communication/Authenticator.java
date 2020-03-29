package nl.tudelft.oopp.demo.communication;

public abstract class Authenticator {
    static String SESSION_COOKIE = "";
    static String ROLE = "";
    static long ID = 0;
    static String USERNAME = "";

    public static boolean isAdmin() {
        return ROLE.contains("ADMIN");
    }
}
