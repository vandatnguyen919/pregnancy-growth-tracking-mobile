package com.pregnancy.data.source.remote


// Number of items per page
const val PAGE_SIZE: Int = 8

// Number of items to prefetch
const val PREFETCH_DISTANCE: Int = 1

// Whether to display placeholders when actual data is not yet loaded
const val ENABLE_PLACEHOLDERS: Boolean = false

// Number of items to load for the first page
const val INITIAL_LOAD_SIZE_HINT: Int = 8

// Maximum number of items that Paging will keep in memory
// Standard equation: maxSize = pageSize + (2 * prefetchDistance)
const val MAX_SIZE: Int = PAGE_SIZE + (2 * PREFETCH_DISTANCE)