package com.pregnancy.edu.feature.blogpost.detail.event

import com.pregnancy.edu.common.base.interfaces.ViewEvent

sealed class BlogPostDetailEvent : ViewEvent {
    data class LoadBlogPost(val blogPostId: Long) : BlogPostDetailEvent()
}