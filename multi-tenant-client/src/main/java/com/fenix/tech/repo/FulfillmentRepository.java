package com.fenix.tech.repo;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.fenix.tech.model.FulfillmentEntity;
import com.fenix.tech.request.FulfillmentStatus;

public interface FulfillmentRepository
		extends JpaRepository<FulfillmentEntity, UUID>, JpaSpecificationExecutor<FulfillmentEntity> {

	Optional<FulfillmentEntity> findByIdAndOrderId(UUID id, UUID orderId);

	@Query("""
			    SELECT f FROM FulfillmentEntity f
			    WHERE f.orderId = :orderId
			      AND (:from IS NULL OR f.shippedAt >= :from)
			      AND (:to IS NULL OR f.shippedAt <= :to)
			      AND (:status IS NULL OR f.status = :status)
			      AND (:carrier IS NULL OR f.carrier = :carrier)
			""")
	Page<FulfillmentEntity> searchFulfillments(UUID orderId, LocalDateTime from, LocalDateTime to,
			FulfillmentStatus status, String carrier, Pageable pageable);

	@Query("""
			    SELECT f FROM FulfillmentEntity f
			    WHERE f.orderId = :orderId
			      AND f.externalFulfillmentId = :externalFulfillmentId
			""")
	Page<FulfillmentEntity> searchByExternal(UUID orderId, String externalFulfillmentId, Pageable pageable);
}