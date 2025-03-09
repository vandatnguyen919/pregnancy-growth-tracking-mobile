package com.pregnancy.edu.feature.blogpost.home.event

import com.pregnancy.edu.common.base.interfaces.ViewEvent

sealed class BlogPostEvent : ViewEvent {

    data object LoadBlogPosts : BlogPostEvent()
    data object RefreshBlogPosts : BlogPostEvent()
    data class FilterByTag(val tag: String) : BlogPostEvent()
    data object ClearFilters : BlogPostEvent()
    data object ErrorDismissed : BlogPostEvent()
}