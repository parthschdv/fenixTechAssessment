package com.fenix.tech.mapper;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fenix.tech.model.FulfillmentEntity;
import com.fenix.tech.model.OrderEntity;
import com.fenix.tech.response.FulfillmentResponse;
import com.fenix.tech.response.OrderResponse;
import com.fenix.tech.response.PagedFulfillment;
import com.fenix.tech.response.PagedOrder;

public class PageMapper {

	public static PagedOrder toPagedOrder(Page<OrderEntity> page) {

		List<OrderResponse> data = page.getContent().stream().map(OrderMapper::toResponse).toList();

		return PagedOrder.builder().data(data).page(page.getNumber()).size(page.getSize())
				.totalElements(page.getTotalElements()).totalPages(page.getTotalPages()).hasNext(page.hasNext())
				.build();
	}

	public static PagedFulfillment toPagedFulfillment(Page<FulfillmentEntity> page) {

		List<FulfillmentResponse> data = page.getContent().stream().map(FulfillmentMapper::toResponse).toList();

		return PagedFulfillment.builder().data(data).page(page.getNumber()).size(page.getSize())
				.totalElements(page.getTotalElements()).totalPages(page.getTotalPages()).hasNext(page.hasNext())
				.build();
	}
}