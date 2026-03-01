package com.fenix.tech.mapper;

import com.fenix.tech.model.FulfillmentEntity;
import com.fenix.tech.response.FulfillmentResponse;

public class FulfillmentMapper {

	public static FulfillmentResponse toResponse(FulfillmentEntity entity) {

		if (entity == null)
			return null;

		return FulfillmentResponse.builder().id(entity.getId()).orderId(entity.getOrderId())
				.externalFulfillmentId(entity.getExternalFulfillmentId()).status(entity.getStatus())
				.carrier(entity.getCarrier()).serviceLevel(entity.getServiceLevel()).shippedAt(entity.getShippedAt())
				.deliveredAt(entity.getDeliveredAt()).createdAt(entity.getCreatedAt()).updatedAt(entity.getUpdatedAt())
				.build();
	}
}