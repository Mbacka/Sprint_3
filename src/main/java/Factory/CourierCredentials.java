package Factory;

public class CourierCredentials {
    public final String login;
    public final String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials getCourierCredentials(Courier courier){
        return new CourierCredentials(courier.login, courier.password);
    }

    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
}
