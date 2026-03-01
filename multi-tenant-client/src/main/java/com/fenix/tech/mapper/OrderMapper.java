package com.fenix.tech.mapper;

import com.fenix.tech.model.OrderEntity;
import com.fenix.tech.response.OrderResponse;

public class OrderMapper {

	public static OrderResponse toResponse(OrderEntity entity) {

		if (entity == null)
			return null;

		return OrderResponse.builder().id(entity.getId()).orgId(entity.getOrgId()).websiteId(entity.getWebsiteId())
				.externalOrderId(entity.getExternalOrderId()).externalOrderNumber(entity.getExternalOrderNumber())
				.status(entity.getStatus()).financialStatus(entity.getFinancialStatus())
				.fulfillmentStatus(entity.getFulfillmentStatus()).customerEmail(entity.getCustomerEmail())
				.orderTotal(entity.getOrderTotal()).currency(entity.getCurrency())
				.orderCreatedAt(entity.getOrderCreatedAt()).orderUpdatedAt(entity.getOrderUpdatedAt())
				.ingestedAt(entity.getIngestedAt()).createdAt(entity.getCreatedAt()).updatedAt(entity.getUpdatedAt())
				.build();
	}
}