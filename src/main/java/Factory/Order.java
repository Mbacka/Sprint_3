package Factory;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    public final String firstName;
    public final String lastName;
    public final String address;
    public final String metroStation;
    public final String phone;
    public final int rentTime;
    public final String deliveryDate;
    public final String comment;
    public final List<String> color;


    public Order(List<String> color) {
        this.color = color;
        this.address = "Ягодная 3";
        this.firstName = "Ичиго";
        this.lastName = "Куросаки";
        this.deliveryDate = LocalDateTime.now().toString();
        this.metroStation = "4";
        this.phone = "88005553535";
        this.rentTime = 3;
        this.comment = "Ку";
    }
}
