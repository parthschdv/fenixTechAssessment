package com.fenix.tech.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fenix.tech.exception.ResourceNotFoundException;
import com.fenix.tech.mapper.OrderMapper;
import com.fenix.tech.mapper.PageMapper;
import com.fenix.tech.model.OrderEntity;
import com.fenix.tech.repo.OrderRepository;
import com.fenix.tech.request.FinancialStatus;
import com.fenix.tech.request.FulfillmentOverallStatus;
import com.fenix.tech.request.OrderCreateRequest;
import com.fenix.tech.request.OrderPatchRequest;
import com.fenix.tech.request.OrderStatus;
import com.fenix.tech.response.OrderResponse;
import com.fenix.tech.response.PagedOrder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

	private final OrderRepository repository;

	@Override
	public OrderResponse createOrUpsert(OrderCreateRequest request) {

		var existing = repository.findByOrgIdAndWebsiteIdAndExternalOrderId(request.getOrgId(), request.getWebsiteId(),
				request.getExternalOrderId());

		OrderEntity entity = existing.orElseGet(OrderEntity::new);

		if (entity.getId() == null) {
			entity.setId(UUID.randomUUID());
		}

		entity.setOrgId(request.getOrgId());
		entity.setWebsiteId(request.getWebsiteId());
		entity.setExternalOrderId(request.getExternalOrderId());
		entity.setExternalOrderNumber(request.getExternalOrderNumber());
		entity.setStatus(request.getStatus());
		entity.setFinancialStatus(request.getFinancialStatus());
		entity.setFulfillmentStatus(request.getFulfillmentStatus());
		entity.setCustomerEmail(request.getCustomerEmail());
		entity.setOrderTotal(request.getOrderTotal());
		entity.setCurrency(request.getCurrency());
		entity.setOrderCreatedAt(request.getOrderCreatedAt());
		entity.setOrderUpdatedAt(request.getOrderUpdatedAt());

		return OrderMapper.toResponse(repository.save(entity));
	}

	@Override
	@Transactional(readOnly = true)
	public PagedOrder search(UUID orgId, UUID websiteId, LocalDateTime from, LocalDateTime to, String sort, int page,
			int size, String status, String financialStatus, String fulfillmentStatus) {

		Sort sorting = Sort.by("updatedAt").descending();
		Pageable pageable = PageRequest.of(page, size, sorting);

		Page<OrderEntity> result = repository.searchOrders(orgId, websiteId, from, to,
				status != null ? OrderStatus.valueOf(status) : null,
				financialStatus != null ? FinancialStatus.valueOf(financialStatus) : null,
				fulfillmentStatus != null ? FulfillmentOverallStatus.valueOf(fulfillmentStatus) : null, pageable);

		return PageMapper.toPagedOrder(result);
	}

	@Override
	public PagedOrder searchByExternal(UUID orgId, UUID websiteId, String externalOrderId, String externalOrderNumber,
			int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<OrderEntity> result = repository.searchByExternal(orgId, websiteId, externalOrderId, externalOrderNumber,
				pageable);

		return PageMapper.toPagedOrder(result);
	}

	@Override
	@Transactional(readOnly = true)
	public OrderResponse getById(UUID orderId) {

		OrderEntity entity = repository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		return OrderMapper.toResponse(entity);
	}

	@Override
	public OrderResponse update(UUID orderId, OrderCreateRequest request) {

		OrderEntity entity = repository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		entity.setExternalOrderNumber(request.getExternalOrderNumber());
		entity.setStatus(request.getStatus());
		entity.setFinancialStatus(request.getFinancialStatus());
		entity.setFulfillmentStatus(request.getFulfillmentStatus());
		entity.setCustomerEmail(request.getCustomerEmail());
		entity.setOrderTotal(request.getOrderTotal());
		entity.setCurrency(request.getCurrency());
		entity.setOrderCreatedAt(request.getOrderCreatedAt());
		entity.setOrderUpdatedAt(request.getOrderUpdatedAt());

		return OrderMapper.toResponse(repository.save(entity));
	}

	@Override
	public OrderResponse patch(UUID orderId, OrderPatchRequest request) {

		OrderEntity entity = repository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		if (request.getExternalOrderNumber() != null)
			entity.setExternalOrderNumber(request.getExternalOrderNumber());

		if (request.getStatus() != null)
			entity.setStatus(request.getStatus());

		if (request.getFinancialStatus() != null)
			entity.setFinancialStatus(request.getFinancialStatus());

		if (request.getFulfillmentStatus() != null)
			entity.setFulfillmentStatus(request.getFulfillmentStatus());

		if (request.getCustomerEmail() != null)
			entity.setCustomerEmail(request.getCustomerEmail());

		if (request.getOrderTotal() != null)
			entity.setOrderTotal(request.getOrderTotal());

		if (request.getCurrency() != null)
			entity.setCurrency(request.getCurrency());

		if (request.getOrderCreatedAt() != null)
			entity.setOrderCreatedAt(request.getOrderCreatedAt());

		if (request.getOrderUpdatedAt() != null)
			entity.setOrderUpdatedAt(request.getOrderUpdatedAt());

		return OrderMapper.toResponse(repository.save(entity));
	}

	@Override
	public void delete(UUID orderId) {

		OrderEntity entity = repository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		repository.delete(entity);
	}
}