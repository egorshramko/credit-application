package com.example.credit.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passport {

    @Id
    @SequenceGenerator(name = "passport_seq",
            sequenceName = "passport_pkey_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passport_seq")
    private Long id;

    private final LocalDateTime createdDate = LocalDateTime.now();

    @NotNull
    @NotEmpty
    @Pattern(regexp="^\\d{2}\\s\\d{2}$")
    private String series;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^\\d{6}$")
    private String number;

    @Past
    private LocalDate issueDate;

    @Pattern(regexp = "^\\d{3}-\\d{3}$")
    private String departmentCode;

    @Size(max=2000)
    private String issuePlace;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "passport")
    private List<PassportScan> scans = new ArrayList<>();

    public void addScan(PassportScan scan) {
        scans.add(scan);
    }

}
