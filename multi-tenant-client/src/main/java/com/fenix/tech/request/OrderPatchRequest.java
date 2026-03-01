package com.fenix.tech.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderPatchRequest {

	private String externalOrderNumber;
	private OrderStatus status;
	private FinancialStatus financialStatus;
	private FulfillmentOverallStatus fulfillmentStatus;
	private String customerEmail;
	private BigDecimal orderTotal;
	private String currency;
	private LocalDateTime orderCreatedAt;
	private LocalDateTime orderUpdatedAt;
}