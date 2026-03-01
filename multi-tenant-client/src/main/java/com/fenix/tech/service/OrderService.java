package com.fenix.tech.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fenix.tech.request.OrderCreateRequest;
import com.fenix.tech.request.OrderPatchRequest;
import com.fenix.tech.response.OrderResponse;
import com.fenix.tech.response.PagedOrder;

public interface OrderService {

	OrderResponse createOrUpsert(OrderCreateRequest request);

	PagedOrder search(UUID orgId, UUID websiteId, LocalDateTime from, LocalDateTime to, String sort, int page, int size,
			String status, String financialStatus, String fulfillmentStatus);

	PagedOrder searchByExternal(UUID orgId, UUID websiteId, String externalOrderId, String externalOrderNumber,
			int page, int size);

	OrderResponse getById(UUID orderId);

	OrderResponse update(UUID orderId, OrderCreateRequest request);

	OrderResponse patch(UUID orderId, OrderPatchRequest request);

	void delete(UUID orderId);
}