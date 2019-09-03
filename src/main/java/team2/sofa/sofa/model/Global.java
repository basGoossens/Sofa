package team2.sofa.sofa.model;

public class Global {

    public static User currentUser;

    public static Account currentAccount;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Global.currentUser = currentUser;
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Account currentAccount) {
        Global.currentAccount = currentAccount;
    }
}
