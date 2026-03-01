package com.fenix.tech.repo;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenix.tech.model.OrderEntity;
import com.fenix.tech.request.FinancialStatus;
import com.fenix.tech.request.FulfillmentOverallStatus;
import com.fenix.tech.request.OrderStatus;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

	Optional<OrderEntity> findByOrgIdAndWebsiteIdAndExternalOrderId(UUID orgId, UUID websiteId, String externalOrderId);

	Optional<OrderEntity> findByIdAndOrgId(UUID id, UUID orgId);

	@Query("""
			    SELECT o FROM OrderEntity o
			    WHERE o.orgId = :orgId
			      AND (:websiteId IS NULL OR o.websiteId = :websiteId)
			      AND (:from IS NULL OR o.orderUpdatedAt >= :from)
			      AND (:to IS NULL OR o.orderUpdatedAt <= :to)
			      AND (:status IS NULL OR o.status = :status)
			      AND (:financialStatus IS NULL OR o.financialStatus = :financialStatus)
			      AND (:fulfillmentStatus IS NULL OR o.fulfillmentStatus = :fulfillmentStatus)
			""")
	Page<OrderEntity> searchOrders(UUID orgId, UUID websiteId, LocalDateTime from, LocalDateTime to, OrderStatus status,
			FinancialStatus financialStatus, FulfillmentOverallStatus fulfillmentStatus, Pageable pageable);

	@Query("""
			    SELECT o FROM OrderEntity o
			    WHERE o.orgId = :orgId
			      AND (:websiteId IS NULL OR o.websiteId = :websiteId)
			      AND (:externalOrderId IS NULL OR o.externalOrderId = :externalOrderId)
			      AND (:externalOrderNumber IS NULL OR o.externalOrderNumber = :externalOrderNumber)
			""")
	Page<OrderEntity> searchByExternal(UUID orgId, UUID websiteId, String externalOrderId, String externalOrderNumber,
			Pageable pageable);
}