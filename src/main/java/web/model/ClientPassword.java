package web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientPassword {

    @NotNull
    @Size(min = 8, max = 20)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
