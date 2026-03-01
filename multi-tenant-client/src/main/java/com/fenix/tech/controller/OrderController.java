package com.fenix.tech.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fenix.tech.request.OrderCreateRequest;
import com.fenix.tech.request.OrderPatchRequest;
import com.fenix.tech.response.OrderResponse;
import com.fenix.tech.response.PagedOrder;
import com.fenix.tech.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService service;

	@PostMapping
	public ResponseEntity<OrderResponse> create(@RequestBody OrderCreateRequest request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrUpsert(request));
	}

	@GetMapping
	public ResponseEntity<PagedOrder> search(@RequestParam UUID orgId, @RequestParam(required = false) UUID websiteId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
			@RequestParam(required = false) String status, @RequestParam(required = false) String financialStatus,
			@RequestParam(required = false) String fulfillmentStatus, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "50") int size, @RequestParam(defaultValue = "updatedAt,desc") String sort) {

		return ResponseEntity.ok(service.search(orgId, websiteId, from, to, sort, page, size, status, financialStatus,
				fulfillmentStatus));
	}

	@GetMapping("/search")
	public ResponseEntity<PagedOrder> searchByExternal(@RequestParam UUID orgId,
			@RequestParam(required = false) UUID websiteId, @RequestParam(required = false) String externalOrderId,
			@RequestParam(required = false) String externalOrderNumber, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "50") int size) {

		return ResponseEntity
				.ok(service.searchByExternal(orgId, websiteId, externalOrderId, externalOrderNumber, page, size));
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> getById(@PathVariable UUID orderId) {

		return ResponseEntity.ok(service.getById(orderId));
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<OrderResponse> update(@PathVariable UUID orderId, @RequestBody OrderCreateRequest request) {

		return ResponseEntity.ok(service.update(orderId, request));
	}

	@PatchMapping("/{orderId}")
	public ResponseEntity<OrderResponse> patch(@PathVariable UUID orderId, @RequestBody OrderPatchRequest request) {

		return ResponseEntity.ok(service.patch(orderId, request));
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<Void> delete(@PathVariable UUID orderId) {

		service.delete(orderId);
		return ResponseEntity.noContent().build();
	}
}