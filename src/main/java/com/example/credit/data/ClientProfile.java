package com.example.credit.data;

import com.example.credit.data.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientProfile {

	@Id
	@SequenceGenerator(name = "client_profile_seq", sequenceName = "client_profile_pkey_sequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_profile_seq")
	private Long id;

	private final LocalDateTime createdDate = LocalDateTime.now();

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

	@Enumerated(EnumType.STRING)
	@NotNull
	@NotEmpty
	private Sex sex;

	@NotNull
	@NotEmpty
	private Boolean consentPersonalData;

	private String comment;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "profile")
	private List<Contact> contacts;

	@OneToOne
	private BinaryContent photo;

}
