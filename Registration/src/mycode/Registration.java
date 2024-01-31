package mycode;
import java.sql.Date;

public class Registration {
    private int id;
    private String name;
    private String email;
    private Date dateOfBirth;

    // Add additional fields as needed

    public Registration(int id, String name, String email, Date dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        // Initialize additional fields as needed
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    // Add getter and setter methods for additional fields as needed
}
