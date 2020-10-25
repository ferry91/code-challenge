package com.adidas.subscriptions.domain.model.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "firstName",
        "gender",
        "dateOfBirth",
        "consentAccepted",
        "newsletterId"
})
public class SubscriptionData implements Serializable
{

    @JsonProperty("email")
    private String email;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("gender")
    private Gender gender;
    @JsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;
    @JsonProperty("consentAccepted")
    private boolean consentAccepted;
    @JsonProperty("newsletterId")
    private UUID newsletterId;
    private final static long serialVersionUID = -5990995357630594959L;

    /**
     * No args constructor for use in serialization
     *
     */
    public SubscriptionData() {
    }

    /**
     *
     * @param firstName
     * @param consentAccepted
     * @param gender
     * @param dateOfBirth
     * @param newsletterId
     * @param email
     */
    public SubscriptionData(String email, String firstName, Gender gender, LocalDate dateOfBirth, boolean consentAccepted, UUID newsletterId) {
        super();
        this.email = email;
        this.firstName = firstName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.consentAccepted = consentAccepted;
        this.newsletterId = newsletterId;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("gender")
    public Gender getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @JsonProperty("dateOfBirth")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonProperty("dateOfBirth")
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @JsonProperty("consentAccepted")
    public boolean getConsentAccepted() {
        return consentAccepted;
    }

    @JsonProperty("consentAccepted")
    public void setConsentAccepted(boolean consentAccepted) {
        this.consentAccepted = consentAccepted;
    }

    @JsonProperty("newsletterId")
    public UUID getNewsletterId() {
        return newsletterId;
    }

    @JsonProperty("newsletterId")
    public void setNewsletterId(UUID newsletterId) {
        this.newsletterId = newsletterId;
    }

}