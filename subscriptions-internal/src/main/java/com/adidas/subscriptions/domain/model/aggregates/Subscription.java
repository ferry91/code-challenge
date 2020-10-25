package com.adidas.subscriptions.domain.model.aggregates;

import com.adidas.subscriptions.domain.model.vo.Gender;
import com.adidas.subscriptions.domain.model.vo.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscription",
		indexes = {@Index(name = "i_subscription", columnList = "email, newsletter_id")})
public class Subscription {

	@Id
	@Column(name = "id", nullable = false)
	private UUID id;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;

	@Column(name = "consent_accepted", nullable = false)
	private boolean consentAccepted;

	@Column(name = "newsletter_id", nullable = false)
	private UUID newsletterId;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private SubscriptionStatus status;

	@Column(name = "date_of_subscription", nullable = false)
	private OffsetDateTime dateOfSubscription;
}
