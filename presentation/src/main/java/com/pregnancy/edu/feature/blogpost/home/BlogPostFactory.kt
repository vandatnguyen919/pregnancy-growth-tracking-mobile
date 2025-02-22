package com.pregnancy.edu.feature.blogpost.home

import com.pregnancy.domain.model.blogpost.BlogPost

object BlogPostFactory {
    fun createBlogPosts(): List<BlogPost> {
        return listOf(
            BlogPost(
                id = 1L,
                heading = "The First Trimester: What to Expect",
                content = "The first trimester is a crucial period of development...",
                pageTitle = "First Trimester Guide",
                shortDescription = "Learn about the changes in your body and how to prepare.",
                featuredImageUrl = "https://img.freepik.com/free-photo/bright-neon-colors-shining-wild-chameleon_23-2151682804.jpg",
                isVisible = true,
                commentQuantity = 12,
                likeQuantity = 45
            ),
            BlogPost(
                id = 2L,
                heading = "Nutrition Tips for a Healthy Pregnancy",
                content = "Eating a balanced diet is essential for both mother and baby...",
                pageTitle = "Pregnancy Nutrition Guide",
                shortDescription = "Discover essential nutrients for a healthy pregnancy.",
                featuredImageUrl = "https://img.freepik.com/free-photo/bright-neon-colors-shining-wild-chameleon_23-2151682804.jpg",
                isVisible = true,
                commentQuantity = 8,
                likeQuantity = 30
            ),
            BlogPost(
                id = 3L,
                heading = "Choosing the Right Prenatal Vitamins",
                content = "Prenatal vitamins provide necessary nutrients...",
                pageTitle = "Prenatal Vitamin Guide",
                shortDescription = "A guide to choosing the best prenatal vitamins.",
                featuredImageUrl = "https://img.freepik.com/free-photo/bright-neon-colors-shining-wild-chameleon_23-2151682804.jpg",
                isVisible = true,
                commentQuantity = 5,
                likeQuantity = 20
            ),
            BlogPost(
                id = 4L,
                heading = "Signs of Labor: When to Go to the Hospital",
                content = "Recognizing the early signs of labor...",
                pageTitle = "Labor and Delivery Tips",
                shortDescription = "Learn the key signs of labor and when to seek medical help.",
                featuredImageUrl = "https://img.freepik.com/free-photo/bright-neon-colors-shining-wild-chameleon_23-2151682804.jpg",
                isVisible = true,
                commentQuantity = 15,
                likeQuantity = 50
            ),
            BlogPost(
                id = 5L,
                heading = "Postpartum Recovery: What You Need to Know",
                content = "The postpartum period requires special care...",
                pageTitle = "Postpartum Care Guide",
                shortDescription = "Tips for a smooth recovery after childbirth.",
                featuredImageUrl = "https://img.freepik.com/free-photo/bright-neon-colors-shining-wild-chameleon_23-2151682804.jpg",
                isVisible = true,
                commentQuantity = 10,
                likeQuantity = 35
            ),
            BlogPost(
                id = 6L,
                heading = "Breastfeeding vs. Formula: Pros and Cons",
                content = "Choosing between breastfeeding and formula...",
                pageTitle = "Infant Feeding Guide",
                shortDescription = "A comparison of breastfeeding and formula feeding.",
                featuredImageUrl = "https://img.freepik.com/free-photo/bright-neon-colors-shining-wild-chameleon_23-2151682804.jpg",
                isVisible = true,
                commentQuantity = 20,
                likeQuantity = 60
            ),
            BlogPost(
                id = 7L,
                heading = "Common Pregnancy Myths Debunked",
                content = "Many myths about pregnancy circulate...",
                pageTitle = "Pregnancy Myths Explained",
                shortDescription = "Separating fact from fiction in pregnancy advice.",
                featuredImageUrl = "https://img.freepik.com/free-photo/bright-neon-colors-shining-wild-chameleon_23-2151682804.jpg",
                isVisible = true,
                commentQuantity = 7,
                likeQuantity = 25
            ),
            BlogPost(
                id = 8L,
                heading = "Maternity Fashion: Dressing Comfortably",
                content = "Dressing stylishly while staying comfortable...",
                pageTitle = "Maternity Fashion Guide",
                shortDescription = "Tips for comfortable and stylish maternity wear.",
                featuredImageUrl = "https://img.freepik.com/free-photo/bright-neon-colors-shining-wild-chameleon_23-2151682804.jpg",
                isVisible = true,
                commentQuantity = 6,
                likeQuantity = 18
            ),
            BlogPost(
                id = 9L,
                heading = "Preparing Your Home for a Newborn",
                content = "Creating a safe and welcoming space for your baby...",
                pageTitle = "Newborn Home Preparation",
                shortDescription = "Essentials for preparing your home for a newborn.",
                featuredImageUrl = "https://img.freepik.com/free-photo/bright-neon-colors-shining-wild-chameleon_23-2151682804.jpg",
                isVisible = true,
                commentQuantity = 14,
                likeQuantity = 40
            ),
            BlogPost(
                id = 10L,
                heading = "Dealing with Morning Sickness: Remedies That Work",
                content = "Morning sickness can be tough, but relief is possible...",
                pageTitle = "Morning Sickness Remedies",
                shortDescription = "Effective ways to manage morning sickness symptoms.",
                featuredImageUrl = "https://img.freepik.com/free-photo/bright-neon-colors-shining-wild-chameleon_23-2151682804.jpg",
                isVisible = true,
                commentQuantity = 9,
                likeQuantity = 28
            )
        )
    }
}