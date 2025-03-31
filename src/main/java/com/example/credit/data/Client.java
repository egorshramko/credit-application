package com.example.credit.data;

import com.example.credit.data.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private final LocalDateTime createdDate = LocalDateTime.now();

    @NotNull
    @NotEmpty
    @Size(max=2000)
    private String lastname;

    @NotNull
    @NotEmpty
    @Size(max=2000)
    private String firstname;
    private String middlename;

    @NotNull
    private LocalDate birthdate;

    @Pattern(regexp = "^\\d{12}$")
    private String tin;

    @Size(max=2000)
    private String citizenship;

    private Sex sex;

    @OneToOne
    private Passport passport;

}
