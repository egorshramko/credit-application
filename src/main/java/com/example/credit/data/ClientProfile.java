package com.example.credit.data;

import com.example.credit.data.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ClientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private final LocalDate createdDate = LocalDate.now();

    @NotNull
    @NotEmpty
    @Size(max = 2000)
    private String lastname;

    @NotNull
    @NotEmpty
    @Size(max = 2000)
    private String firstname;

    @Size(max = 2000)
    private String middlename;

    @NotNull
    private LocalDate birthdate;

    @ManyToOne
    private Passport passport;

    @Pattern(regexp = "^\\d{12}$")
    private String tin;

    @NotNull
    @NotEmpty
    @Size(max = 2000)
    private String citizenship;

    @NotNull
    @NotEmpty
    private Sex sex;

    //private File? photo;

    @NotNull
    @NotEmpty
    private Boolean consentPersonalData;

    private String comment;

}
