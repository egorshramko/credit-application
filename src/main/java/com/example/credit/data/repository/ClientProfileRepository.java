package com.example.credit.data.repository;

import com.example.credit.data.ClientProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProfileRepository extends CrudRepository<ClientProfile, Long> {
	
	
	
}
