package com.fenix.tech.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fenix.tech.request.FulfillmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fulfillments", uniqueConstraints = @UniqueConstraint(name = "uk_fulfillment_external", columnNames = {
		"tenant_id", "order_id", "external_fulfillment_id" }))
@Getter
@Setter
public class FulfillmentEntity extends BaseEntity {

	@Id
	@Column(name = "fulfillment_id", columnDefinition = "BINARY(16)")
	private UUID id;

	@Column(name = "tenant_id", nullable = false, columnDefinition = "BINARY(16)")
	private UUID orgId;

	@Column(name = "order_id", nullable = false, columnDefinition = "BINARY(16)")
	private UUID orderId;

	@Column(name = "external_fulfillment_id", nullable = false)
	private String externalFulfillmentId;

	@Enumerated(EnumType.STRING)
	@Column(name = "fulfillment_status", nullable = false)
	private FulfillmentStatus status;

	private String carrier;

	@Column(name = "service_level")
	private String serviceLevel;

	@Column(name = "shipped_at")
	private LocalDateTime shippedAt;

	@Column(name = "delivered_at")
	private LocalDateTime deliveredAt;

	@PrePersist
	public void prePersist() {
		this.createdAt = Instant.now();
		this.updatedAt = Instant.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = Instant.now();
	}
}