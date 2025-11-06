package com.tadobasolutions.repository;

import com.tadobasolutions.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByReferenceId(String referenceId);

    Optional<Certificate> findByReferenceIdAndDob(String referenceId, LocalDate dob);
}