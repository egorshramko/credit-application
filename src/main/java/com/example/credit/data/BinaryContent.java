package com.example.credit.data;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BinaryContent {

	@Id
	@SequenceGenerator(name = "binary_content_seq", sequenceName = "binary_content_pkey_sequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "binary_content_seq")
	private Long id;

	private final LocalDateTime createdDate = LocalDateTime.now();

	@NotNull
	@Size(max = 2000)
	private String name;

	@NotNull
	private File content;
	
	@Column(unique = true)
	private UUID uuid;

}
