package api;

import java.util.ArrayList;

public class OrderCreationData {
    public String firstName;
    public String lastName;
    public String address;
    public Integer metroStation;
    public String phone;
    public Integer rentTime;
    public String deliveryDate;
    public String comment;
    public ArrayList<String> color;

    public OrderCreationData(String firstName, String lastName, String address, Integer metroStation, String phone, Integer rentTime, String deliveryDate, String comment, ArrayList<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public Integer getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public ArrayList<String> getColor() {
        return color;
    }
}

