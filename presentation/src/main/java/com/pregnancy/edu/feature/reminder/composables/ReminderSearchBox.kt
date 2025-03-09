package com.pregnancy.edu.feature.reminder.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.pregnancy.edu.R
import com.pregnancy.edu.common.base.composable.PrimaryButton
import com.pregnancy.edu.common.base.composable.PrimaryTextField

@Preview
@Composable
fun ReminderSearchBoxPreview() {
    ReminderSearchBox(
        onQueryChange = { },
        onSearchClick = { }
    )
}

@Composable
fun ReminderSearchBox(
    modifier: Modifier = Modifier,
    query: String? = null,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        val (searchTitle, searchButton, searchBox) = createRefs()

        Text(
            modifier = Modifier.constrainAs(searchTitle) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            },
            text = stringResource(id = R.string.cd_search_reminder),
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        PrimaryButton(
            modifier = Modifier.constrainAs(searchButton) {
                top.linkTo(searchTitle.top)
                bottom.linkTo(searchTitle.bottom)
                end.linkTo(parent.end)
            },
            text = stringResource(id = R.string.cd_search),
            onClick = onSearchClick
        )
        PrimaryTextField(
            modifier = Modifier.constrainAs(searchBox) {
                top.linkTo(searchTitle.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            value = query,
            label = stringResource(id = R.string.cd_search),
            onValueChange = onQueryChange,
//            placeholder = stringResource(id = R.string.cd_search_reminder_placeholder)
        )
    }
}
