package api;

public class RegData {
    private String login;
    private String password;
    private String firstName;

    public RegData(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }
}
