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
import java.util.List;

@Slf4j
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

    @Id
    @SequenceGenerator(name = "client_seq",
            sequenceName = "client_pkey_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
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

    private LocalDate birthdate;

    @Pattern(regexp = "^\\d{12}$")
    private String tin;

    @Size(max=2000)
    private String citizenship;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToOne
    private Passport passport;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "client")
    private List<Contact> contacts;

    //private File? photo;

}
