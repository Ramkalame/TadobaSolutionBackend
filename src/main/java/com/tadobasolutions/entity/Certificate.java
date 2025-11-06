package com.tadobasolutions.entity;

import com.tadobasolutions.entity.enums.CertificateStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique reference ID assigned to the student
    @Column(nullable = false, unique = true)
    private String referenceId;

    // Date of birth used for verification
    @Column(nullable = false)
    private LocalDate dob;

    // Original uploaded file name (for download/display)
    @Column(nullable = false)
    private String fileName;

    // Path where the certificate PDF is stored (absolute or relative)
    @Column(nullable = false, length = 1024)
    private String filePath;

    // Optional field to mark if certificate is valid or revoked
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CertificateStatus status = CertificateStatus.ACTIVE;

    // Optional: admin who uploaded it
    private String uploadedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
