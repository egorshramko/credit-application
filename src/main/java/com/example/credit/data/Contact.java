package com.example.credit.data;

import com.example.credit.data.enums.ContactType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

	@Id
	@SequenceGenerator(name = "contact_seq", sequenceName = "contact_pkey_sequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_seq")
	private Long id;

	private final LocalDateTime createdDate = LocalDateTime.now();

	@Enumerated(EnumType.STRING)
	@NotNull
	@NotEmpty
	private ContactType contactType;

	@Pattern(regexp = "^+7\\d{10}$")
	private String phoneNumber;

	private String comment;

}
