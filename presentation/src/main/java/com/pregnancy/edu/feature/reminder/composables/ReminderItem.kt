package com.pregnancy.edu.feature.reminder.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.pregnancy.domain.model.reminder.Reminder
import com.pregnancy.edu.feature.blogpost.home.composables.TagChip

@Preview
@Composable
fun ReminderItemPreview() {
    ReminderItem(
        reminder = Reminder(
            id = 1,
            description = "Meeting with Dr. Smith",
            reminderDate = "2025-02-23",
            reminderType = "Appointment",
        )
    ) { }
}

@Composable
fun ReminderItem(
    modifier: Modifier = Modifier,
    reminder: Reminder,
    onReminderItemClick: (Long) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onReminderItemClick(reminder.id) }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        val (circle, text, chip) = createRefs()

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFF9D0CA))
                .constrainAs(circle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = reminder.description ?: "",
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(circle.end, margin = 8.dp)
                }
        )
        reminder.reminderType?.let {
            TagChip(
                modifier = Modifier
                    .constrainAs(chip) {
                        top.linkTo(text.bottom, margin = 8.dp)
                        start.linkTo(circle.end, margin = 8.dp)
                    },
                text = it
            )
        }
    }
}