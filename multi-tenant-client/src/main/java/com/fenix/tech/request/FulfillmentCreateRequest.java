package com.fenix.tech.request;
	
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FulfillmentCreateRequest {

	@NotBlank
	private String externalFulfillmentId;

	private FulfillmentStatus status = FulfillmentStatus.UNKNOWN;

	private String carrier;
	private String serviceLevel;

	private LocalDateTime shippedAt;
	private LocalDateTime deliveredAt;
}