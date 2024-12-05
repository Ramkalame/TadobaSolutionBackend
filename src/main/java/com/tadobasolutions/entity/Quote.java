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

    public Long getQuoteId() {
        return quoteId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerMobileNumber() {
        return customerMobileNumber;
    }

    public String getQueryDescription() {
        return QueryDescription;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setCustomerMobileNumber(String customerMobileNumber) {
        this.customerMobileNumber = customerMobileNumber;
    }

    public void setQueryDescription(String queryDescription) {
        QueryDescription = queryDescription;
    }

    public Quote(Long quoteId, String customerName, String customerEmail, String customerMobileNumber, String queryDescription) {
        this.quoteId = quoteId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerMobileNumber = customerMobileNumber;
        QueryDescription = queryDescription;
    }

    public Quote(){};

}
