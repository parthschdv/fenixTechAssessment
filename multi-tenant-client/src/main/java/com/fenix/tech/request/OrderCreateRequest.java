package com.fenix.tech.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class OrderCreateRequest {

	@NotNull
	private UUID orgId;

	@NotNull
	private UUID websiteId;

	@NotBlank
	private String externalOrderId;

	private String externalOrderNumber;

	private OrderStatus status = OrderStatus.CREATED;

	private FinancialStatus financialStatus = FinancialStatus.UNKNOWN;

	private FulfillmentOverallStatus fulfillmentStatus = FulfillmentOverallStatus.UNKNOWN;

	@Email
	private String customerEmail;

	@PositiveOrZero
	private BigDecimal orderTotal = BigDecimal.ZERO;

	private String currency;

	private LocalDateTime orderCreatedAt;

	private LocalDateTime orderUpdatedAt;
}