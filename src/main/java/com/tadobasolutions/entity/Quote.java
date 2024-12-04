package com.tadobasolutions.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long quoteId;

    @NotBlank(message="customer name should not be blank.")
    private String customerName;

    @NotBlank(message="customer email should not be blank.")
    @Email(message = "Email Invalid format.")
    private String customerEmail;

    @NotBlank(message="customer mobile number should not be blank.")
    @Pattern(regexp = "[0-9]{10}$", message = "Phone number must be 10 digits.")
    private String customerMobileNumber;

    @NotBlank(message="Description should not be blank.")
    private String QueryDescription;
}
