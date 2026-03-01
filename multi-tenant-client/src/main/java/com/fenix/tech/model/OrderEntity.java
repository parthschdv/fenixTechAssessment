package com.fenix.tech.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fenix.tech.request.FinancialStatus;
import com.fenix.tech.request.FulfillmentOverallStatus;
import com.fenix.tech.request.OrderStatus;

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
@Table(name = "orders", uniqueConstraints = @UniqueConstraint(name = "uk_order_external", columnNames = { "tenant_id",
		"store_id", "external_order_id" }))
@Getter
@Setter
public class OrderEntity extends BaseEntity {

	@Id
	@Column(name = "order_id", columnDefinition = "BINARY(16)")
	private UUID id;

	@Column(name = "tenant_id", nullable = false, columnDefinition = "BINARY(16)")
	private UUID orgId;

	@Column(name = "store_id", nullable = false, columnDefinition = "BINARY(16)")
	private UUID websiteId;

	@Column(name = "external_order_id", nullable = false)
	private String externalOrderId;

	@Column(name = "external_order_number")
	private String externalOrderNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status", nullable = false)
	private OrderStatus status;

	@Enumerated(EnumType.STRING)
	@Column(name = "financial_status", nullable = false)
	private FinancialStatus financialStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "fulfillment_status", nullable = false)
	private FulfillmentOverallStatus fulfillmentStatus;

	@Column(name = "customer_email")
	private String customerEmail;

	@Column(name = "order_total_amount", nullable = false)
	private BigDecimal orderTotal;

	private String currency;

	@Column(name = "order_created_at")
	private LocalDateTime orderCreatedAt;

	@Column(name = "order_updated_at")
	private LocalDateTime orderUpdatedAt;

	@Column(name = "ingested_at", nullable = false)
	private Instant ingestedAt;

	@PrePersist
	public void prePersist() {
		this.createdAt = Instant.now();
		this.updatedAt = Instant.now();
		this.ingestedAt = Instant.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = Instant.now();
	}
}