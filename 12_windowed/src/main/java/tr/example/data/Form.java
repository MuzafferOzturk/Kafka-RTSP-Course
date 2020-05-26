package tr.example.data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Form {
    private User user;
    private Date createdTime;

    public Form() {
    }

    public Form(User user) {
        this.user = user;
        this.createdTime = new Date();
    }

    public Form(User user, Date createdTime) {
        this(user);
        this.createdTime = createdTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Form form = (Form) o;
        return Objects.equals(user.getId(), form.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getId());
    }

    @Override
    public String toString() {
        return "Form{" +
                "user=" + user +
                ", createdTime=" + createdTime +
                '}';
    }
}
