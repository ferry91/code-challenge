package com.adidas.subscriptions.domain.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "firstName",
        "newsletterId"
})
public class EmailData implements Serializable
{

    @JsonProperty("email")
    private String email;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("newsletterId")
    private UUID newsletterId;
    private final static long serialVersionUID = -5990995357630594959L;

    /**
     * No args constructor for use in serialization
     *
     */
    public EmailData() {
    }

    /**
     *
     * @param firstName
     * @param newsletterId
     * @param email
     */
    public EmailData(String email, String firstName, UUID newsletterId) {
        super();
        this.email = email;
        this.firstName = firstName;
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

    @JsonProperty("newsletterId")
    public UUID getNewsletterId() {
        return newsletterId;
    }

    @JsonProperty("newsletterId")
    public void setNewsletterId(UUID newsletterId) {
        this.newsletterId = newsletterId;
    }

}