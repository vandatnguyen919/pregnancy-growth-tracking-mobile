package com.pregnancy.domain.model.blogpost

data class BlogPost(
    val id: Long,
    val heading: String? = null,
    val content: String? = null,
    val pageTitle: String? = null,
    val shortDescription: String? = null,
    val featuredImageUrl: String? = null,
    val isVisible: Boolean,
    val commentQuantity: Int,
    val likeQuantity: Int,
    val authorName: String? = "Nguyen Van Dat",
    val authorAvatarUrl: String? = null,
    val nameTags: List<String> = emptyList(),
) {
}