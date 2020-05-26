package tr.example.data;

import java.util.List;

public class Form {
    private User user;
    private String city;
    private String street;
    private String addressType;
    private List<String> phoneList;

    public Form() {
    }

    public Form(User user, String city, String street, String addressType, List<String> phoneList) {
        this.user = user;
        this.city = city;
        this.street = street;
        this.addressType = addressType;
        this.phoneList = phoneList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public String toString() {
        return "Form{" +
                "user=" + user +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", addressType='" + addressType + '\'' +
                ", phoneList=" + phoneList +
                '}';
    }
}
