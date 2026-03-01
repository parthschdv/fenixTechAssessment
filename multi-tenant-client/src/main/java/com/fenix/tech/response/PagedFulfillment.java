package com.fenix.tech.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagedFulfillment {

	private List<FulfillmentResponse> data;
	private int page;
	private int size;
	private long totalElements;
	private int totalPages;
	private boolean hasNext;
}