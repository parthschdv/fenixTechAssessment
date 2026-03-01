package com.fenix.tech.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fenix.tech.exception.ResourceNotFoundException;
import com.fenix.tech.mapper.FulfillmentMapper;
import com.fenix.tech.mapper.PageMapper;
import com.fenix.tech.model.FulfillmentEntity;
import com.fenix.tech.model.OrderEntity;
import com.fenix.tech.repo.FulfillmentRepository;
import com.fenix.tech.repo.OrderRepository;
import com.fenix.tech.request.FulfillmentCreateRequest;
import com.fenix.tech.request.FulfillmentPatchRequest;
import com.fenix.tech.request.FulfillmentStatus;
import com.fenix.tech.response.FulfillmentResponse;
import com.fenix.tech.response.PagedFulfillment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FulfillmentServiceImpl implements FulfillmentService {

	private final FulfillmentRepository fulfillmentRepository;
	private final OrderRepository orderRepository;

	@Override
	public FulfillmentResponse create(UUID orderId, FulfillmentCreateRequest request) {

		OrderEntity order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		FulfillmentEntity entity = new FulfillmentEntity();
		entity.setId(UUID.randomUUID());

		entity.setOrgId(order.getOrgId());

		entity.setOrderId(orderId);
		entity.setExternalFulfillmentId(request.getExternalFulfillmentId());
		entity.setStatus(request.getStatus() != null ? request.getStatus() : FulfillmentStatus.CREATED);
		entity.setCarrier(request.getCarrier());
		entity.setServiceLevel(request.getServiceLevel());
		entity.setShippedAt(request.getShippedAt());
		entity.setDeliveredAt(request.getDeliveredAt());

		return FulfillmentMapper.toResponse(fulfillmentRepository.save(entity));
	}

	@Override
	@Transactional(readOnly = true)
	public PagedFulfillment search(UUID orderId, LocalDateTime from, LocalDateTime to, String status, String carrier,
			int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<FulfillmentEntity> result = fulfillmentRepository.searchFulfillments(orderId, from, to,
				status != null ? FulfillmentStatus.valueOf(status) : null, carrier, pageable);

		return PageMapper.toPagedFulfillment(result);
	}

	@Override
	public PagedFulfillment searchByExternal(UUID orderId, String externalFulfillmentId, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<FulfillmentEntity> result = fulfillmentRepository.searchByExternal(orderId, externalFulfillmentId,
				pageable);

		return PageMapper.toPagedFulfillment(result);
	}

	@Override
	@Transactional(readOnly = true)
	public FulfillmentResponse getById(UUID orderId, UUID fulfillmentId) {

		FulfillmentEntity entity = fulfillmentRepository.findByIdAndOrderId(fulfillmentId, orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Fulfillment not found"));

		return FulfillmentMapper.toResponse(entity);
	}

	@Override
	public FulfillmentResponse update(UUID orderId, UUID fulfillmentId, FulfillmentCreateRequest request) {

		FulfillmentEntity entity = fulfillmentRepository.findByIdAndOrderId(fulfillmentId, orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Fulfillment not found"));

		entity.setStatus(request.getStatus());
		entity.setCarrier(request.getCarrier());
		entity.setServiceLevel(request.getServiceLevel());
		entity.setShippedAt(request.getShippedAt());
		entity.setDeliveredAt(request.getDeliveredAt());

		return FulfillmentMapper.toResponse(fulfillmentRepository.save(entity));
	}

	@Override
	public FulfillmentResponse patch(UUID orderId, UUID fulfillmentId, FulfillmentPatchRequest request) {

		FulfillmentEntity entity = fulfillmentRepository.findByIdAndOrderId(fulfillmentId, orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Fulfillment not found"));

		if (request.getStatus() != null)
			entity.setStatus(request.getStatus());

		if (request.getCarrier() != null)
			entity.setCarrier(request.getCarrier());

		if (request.getServiceLevel() != null)
			entity.setServiceLevel(request.getServiceLevel());

		if (request.getShippedAt() != null)
			entity.setShippedAt(request.getShippedAt());

		if (request.getDeliveredAt() != null)
			entity.setDeliveredAt(request.getDeliveredAt());

		return FulfillmentMapper.toResponse(fulfillmentRepository.save(entity));
	}

	@Override
	public void delete(UUID orderId, UUID fulfillmentId) {

		FulfillmentEntity entity = fulfillmentRepository.findByIdAndOrderId(fulfillmentId, orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Fulfillment not found"));

		fulfillmentRepository.delete(entity);
	}
}