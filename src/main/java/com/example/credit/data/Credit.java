package com.example.credit.data;

import com.example.credit.data.enums.CreditStage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private final LocalDate createdDate = LocalDate.now();

    @NotNull
    @NotEmpty
    @ManyToOne
    private Client borrower;

    @NotNull
    @NotEmpty
    private CreditStage stage;

    @OneToOne
    private ClientProfile profile;

}
