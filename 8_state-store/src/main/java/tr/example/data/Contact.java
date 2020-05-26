package tr.example.data;

public class Contact {
    private String type;
    private String contact_info;

    public Contact() {
    }

    public Contact(String type, String contact_info) {
        this.type = type;
        this.contact_info = contact_info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContact_info() {
        return contact_info;
    }

    public void setContact_info(String contact_info) {
        this.contact_info = contact_info;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "type='" + type + '\'' +
                ", contact_info='" + contact_info + '\'' +
                '}';
    }
}
