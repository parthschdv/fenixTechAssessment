package com.fenix.tech.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fenix.tech.request.FulfillmentCreateRequest;
import com.fenix.tech.request.FulfillmentPatchRequest;
import com.fenix.tech.response.FulfillmentResponse;
import com.fenix.tech.response.PagedFulfillment;

public interface FulfillmentService {

	FulfillmentResponse create(UUID orderId, FulfillmentCreateRequest request);

	PagedFulfillment search(UUID orderId, LocalDateTime from, LocalDateTime to, String status, String carrier, int page,
			int size);

	PagedFulfillment searchByExternal(UUID orderId, String externalFulfillmentId, int page, int size);

	FulfillmentResponse getById(UUID orderId, UUID fulfillmentId);

	FulfillmentResponse update(UUID orderId, UUID fulfillmentId, FulfillmentCreateRequest request);

	FulfillmentResponse patch(UUID orderId, UUID fulfillmentId, FulfillmentPatchRequest request);

	void delete(UUID orderId, UUID fulfillmentId);
}