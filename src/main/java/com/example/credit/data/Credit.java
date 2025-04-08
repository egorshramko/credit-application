package com.example.credit.data;

import com.example.credit.data.enums.CreditStage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credit {

    @Id
    @SequenceGenerator(name = "credit_seq",
            sequenceName = "credit_pkey_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_seq")
    private Long id;

    private final LocalDateTime createdDate = LocalDateTime.now();

    @NotNull
    @NotEmpty
    @ManyToOne
    private Client borrower;

    @Enumerated(EnumType.STRING)
    @NotNull
    @NotEmpty
    private CreditStage stage;

    @OneToOne
    private ClientProfile profile;

}
