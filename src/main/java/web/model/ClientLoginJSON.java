package web.model;

import javax.validation.constraints.NotNull;

public class ClientLoginJSON {

    @NotNull
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
