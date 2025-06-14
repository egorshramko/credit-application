package com.example.credit.data;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PassportScan {

	@Id
	@SequenceGenerator(name = "passport_scan_seq", sequenceName = "passport_scan_pkey_sequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passport_scan_seq")
	private Long id;

	private final LocalDateTime createdDate = LocalDateTime.now();

	@OneToOne(cascade = CascadeType.ALL)
	private BinaryContent scanFile;

}
