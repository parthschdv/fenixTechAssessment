package com.fenix.tech.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FulfillmentPatchRequest {

	private FulfillmentStatus status;
	private String carrier;
	private String serviceLevel;
	private LocalDateTime shippedAt;
	private LocalDateTime deliveredAt;
}