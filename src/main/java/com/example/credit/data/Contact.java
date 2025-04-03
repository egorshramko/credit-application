package com.example.credit.data;

import com.example.credit.data.enums.ContactType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private final LocalDate createdDate = LocalDate.now();

    @NotNull
    @NotEmpty
    private ContactType contactType;

    @Pattern(regexp = "^+7\\d{10}$")
    private String phoneNumber;

    private String comment;

    @ManyToOne
    private ClientProfile profile;

}
