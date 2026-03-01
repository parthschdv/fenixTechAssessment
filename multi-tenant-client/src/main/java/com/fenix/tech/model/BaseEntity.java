package com.fenix.tech.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

	@Column(name = "created_at", nullable = false, updatable = false)
	public Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	public Instant updatedAt;
}