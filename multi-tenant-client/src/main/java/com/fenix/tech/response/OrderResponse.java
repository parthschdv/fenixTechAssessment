package com.fenix.tech.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fenix.tech.request.FinancialStatus;
import com.fenix.tech.request.FulfillmentOverallStatus;
import com.fenix.tech.request.OrderStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {

	private UUID id;
	private UUID orgId;
	private UUID websiteId;
	private String externalOrderId;
	private String externalOrderNumber;
	private OrderStatus status;
	private FinancialStatus financialStatus;
	private FulfillmentOverallStatus fulfillmentStatus;
	private String customerEmail;
	private BigDecimal orderTotal;
	private String currency;
	private LocalDateTime orderCreatedAt;
	private LocalDateTime orderUpdatedAt;
	private Instant ingestedAt;
	private Instant createdAt;
	private Instant updatedAt;
}