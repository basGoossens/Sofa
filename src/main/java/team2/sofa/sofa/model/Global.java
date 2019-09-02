package team2.sofa.sofa.model;

public class Global {

    public static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Global.currentUser = currentUser;
    }
}
