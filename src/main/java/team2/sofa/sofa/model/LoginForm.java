package team2.sofa.sofa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class LoginForm {


    private int loginId;
    @NotNull (message = "oops")
    private String username1;
    @NotNull (message = "owjee")
    private String password1;

    public LoginForm(int loginId, String username, String password) {
        this.loginId = loginId;
        this.username1 = username;
        this.password1 = password;
    }

    public LoginForm(){
        super();
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username) {
        this.username1 = username;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password) {
        this.password1 = password;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }
}
