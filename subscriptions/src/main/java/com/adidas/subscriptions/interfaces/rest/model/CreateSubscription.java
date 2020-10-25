package com.adidas.subscriptions.interfaces.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * CreateSubscription
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-23T17:28:51.529765700+02:00[Europe/Paris]")

public class CreateSubscription   {
  @JsonProperty("email")
  private String email;

  @JsonProperty("firstName")
  private String firstName;

  /**
   * Gender of the subscriber
   */
  public enum GenderEnum {
    MALE("MALE"),
    
    FEMALE("FEMALE"),
    
    OTHER("OTHER");

    private String value;

    GenderEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static GenderEnum fromValue(String value) {
      for (GenderEnum b : GenderEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("gender")
  private GenderEnum gender;

  @JsonProperty("dateOfBirth")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
  private LocalDate dateOfBirth;

  @JsonProperty("consentAccepted")
  private Boolean consentAccepted;

  @JsonProperty("newsletterId")
  private UUID newsletterId;

  public CreateSubscription email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Email address to receive the communications
   * @return email
  */
  @ApiModelProperty(example = "john@adidas.com", required = true, value = "Email address to receive the communications")
  @NotNull

@Pattern(regexp="^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$") 
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public CreateSubscription firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Name of the subscriber
   * @return firstName
  */
  @ApiModelProperty(example = "John", value = "Name of the subscriber")


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public CreateSubscription gender(GenderEnum gender) {
    this.gender = gender;
    return this;
  }

  /**
   * Gender of the subscriber
   * @return gender
  */
  @ApiModelProperty(value = "Gender of the subscriber")


  public GenderEnum getGender() {
    return gender;
  }

  public void setGender(GenderEnum gender) {
    this.gender = gender;
  }

  public CreateSubscription dateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  /**
   * Date of birth of the subscriber
   * @return dateOfBirth
  */
  @ApiModelProperty(required = true, value = "Date of birth of the subscriber")
  @NotNull

  @Valid

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public CreateSubscription consentAccepted(Boolean consentAccepted) {
    this.consentAccepted = consentAccepted;
    return this;
  }

  /**
   * Flag to inform if the user has given consent
   * @return consentAccepted
  */
  @ApiModelProperty(required = true, value = "Flag to inform if the user has given consent")
  @NotNull


  public Boolean getConsentAccepted() {
    return consentAccepted;
  }

  public void setConsentAccepted(Boolean consentAccepted) {
    this.consentAccepted = consentAccepted;
  }

  public CreateSubscription newsletterId(UUID newsletterId) {
    this.newsletterId = newsletterId;
    return this;
  }

  /**
   * Id of the newsletter to subscribe
   * @return newsletterId
  */
  @ApiModelProperty(required = true, value = "Id of the newsletter to subscribe")
  @NotNull

  @Valid

  public UUID getNewsletterId() {
    return newsletterId;
  }

  public void setNewsletterId(UUID newsletterId) {
    this.newsletterId = newsletterId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateSubscription createSubscription = (CreateSubscription) o;
    return Objects.equals(this.email, createSubscription.email) &&
        Objects.equals(this.firstName, createSubscription.firstName) &&
        Objects.equals(this.gender, createSubscription.gender) &&
        Objects.equals(this.dateOfBirth, createSubscription.dateOfBirth) &&
        Objects.equals(this.consentAccepted, createSubscription.consentAccepted) &&
        Objects.equals(this.newsletterId, createSubscription.newsletterId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, firstName, gender, dateOfBirth, consentAccepted, newsletterId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateSubscription {\n");
    
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
    sb.append("    consentAccepted: ").append(toIndentedString(consentAccepted)).append("\n");
    sb.append("    newsletterId: ").append(toIndentedString(newsletterId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

