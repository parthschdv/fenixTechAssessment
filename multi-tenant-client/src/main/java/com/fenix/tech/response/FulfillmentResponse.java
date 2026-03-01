package com.fenix.tech.response;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fenix.tech.request.FulfillmentStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FulfillmentResponse {

	private UUID id;
	private UUID orderId;
	private String externalFulfillmentId;
	private FulfillmentStatus status;
	private String carrier;
	private String serviceLevel;
	private LocalDateTime shippedAt;
	private LocalDateTime deliveredAt;
	private Instant createdAt;
	private Instant updatedAt;
}