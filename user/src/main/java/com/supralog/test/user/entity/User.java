package com.supralog.test.user.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Document(collection = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "lastName is mandatory")
    private String lastName;  // Mandatory

    @NotBlank(message = "firstName is mandatory")
    private String firstName; // Mandatory

    @NotBlank(message = "username is mandatory")
    @Indexed(unique = true) // to enforce uniqueness
    private String username; // Mandatory
    @NotBlank(message = "password is mandatory")
    private String password; // Mandatory

    @NotNull(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Indexed(unique = true) // to enforce uniqueness
    private String email;    // Mandatory

    @Min(value = 18, message = "Age must be at least 18")
    private int age;          // Mandatory

    @NotNull(message = "Date of Birth is mandatory")
    @Past(message = "Date of Birth should be in the past")
    private String dateOfBirth;   // Mandatory

    private Address address;    // Optional, only the country is mandatory
    private String phoneNumber; // Optional
    private Date createDate;   // Mandatory, field for the creation date
    private Date expireDate;    // Optional, field for the expiration date of the user, default : two years from the current date

    public User() {
        this.createDate = new Date();
        this.createDate = new Date(new Date().getTime() +  2L * 365L * 24L * 60L * 60L * 1000L);
    }
}
