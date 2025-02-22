package com.pregnancy.edu.feature.blogpost.home.event

import com.pregnancy.edu.common.base.interfaces.ViewEvent

sealed class BlogPostEvent : ViewEvent {

    data object LoadBlogPosts: BlogPostEvent()
}