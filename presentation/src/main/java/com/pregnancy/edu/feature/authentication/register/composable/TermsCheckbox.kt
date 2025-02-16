package com.pregnancy.edu.feature.authentication.register.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun TermsCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onTermsClicked: () -> Unit,
    onPrivacyClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFFAACAA) // Pink color when checked
            )
        )
        
        Text(
            text = buildAnnotatedString {
                append("By creating an account, I agree to our ")
                
                // Terms of use link
                pushStringAnnotation(tag = "TERMS", annotation = "")
                withStyle(SpanStyle(color = Color.Blue)) {
                    append("Terms of use")
                }
                pop()
                
                append(" and ")
                
                // Privacy Policy link
                pushStringAnnotation(tag = "PRIVACY", annotation = "")
                withStyle(SpanStyle(color = Color.Blue)) {
                    append("Privacy Policy")
                }
                pop()
            },
            modifier = Modifier.weight(1f),
            color = Color.Black
        )
    }
}