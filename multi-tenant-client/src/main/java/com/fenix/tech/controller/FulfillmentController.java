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

import com.fenix.tech.request.FulfillmentCreateRequest;
import com.fenix.tech.request.FulfillmentPatchRequest;
import com.fenix.tech.response.FulfillmentResponse;
import com.fenix.tech.response.PagedFulfillment;
import com.fenix.tech.service.FulfillmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders/{orderId}/fulfillments")
@RequiredArgsConstructor
public class FulfillmentController {

	private final FulfillmentService service;

	@PostMapping
	public ResponseEntity<FulfillmentResponse> create(@PathVariable UUID orderId,
			@RequestBody FulfillmentCreateRequest request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(orderId, request));
	}

	@GetMapping
	public ResponseEntity<PagedFulfillment> search(@PathVariable UUID orderId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
			@RequestParam(required = false) String status, @RequestParam(required = false) String carrier,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size) {

		return ResponseEntity.ok(service.search(orderId, from, to, status, carrier, page, size));
	}

	@GetMapping("/search")
	public ResponseEntity<PagedFulfillment> searchByExternal(@PathVariable UUID orderId,
			@RequestParam String externalFulfillmentId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "50") int size) {

		return ResponseEntity.ok(service.searchByExternal(orderId, externalFulfillmentId, page, size));
	}

	@GetMapping("/{fulfillmentId}")
	public ResponseEntity<FulfillmentResponse> getById(@PathVariable UUID orderId, @PathVariable UUID fulfillmentId) {

		return ResponseEntity.ok(service.getById(orderId, fulfillmentId));
	}

	@PutMapping("/{fulfillmentId}")
	public ResponseEntity<FulfillmentResponse> update(@PathVariable UUID orderId, @PathVariable UUID fulfillmentId,
			@RequestBody FulfillmentCreateRequest request) {

		return ResponseEntity.ok(service.update(orderId, fulfillmentId, request));
	}

	@PatchMapping("/{fulfillmentId}")
	public ResponseEntity<FulfillmentResponse> patch(@PathVariable UUID orderId, @PathVariable UUID fulfillmentId,
			@RequestBody FulfillmentPatchRequest request) {

		return ResponseEntity.ok(service.patch(orderId, fulfillmentId, request));
	}

	@DeleteMapping("/{fulfillmentId}")
	public ResponseEntity<Void> delete(@PathVariable UUID orderId, @PathVariable UUID fulfillmentId) {

		service.delete(orderId, fulfillmentId);
		return ResponseEntity.noContent().build();
	}
}