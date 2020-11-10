package socialmediaapp.twitterinspiredapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import socialmediaapp.twitterinspiredapp.enums.ACCOUNT_STATUS;
import socialmediaapp.twitterinspiredapp.enums.ACCOUNT_TYPE;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @Email
    @NotEmpty(message = "Email is required")
    private String email;

    private Timestamp created;
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private ACCOUNT_TYPE accountType;

    @Enumerated(EnumType.STRING)
    private ACCOUNT_STATUS accountStatus;

}
