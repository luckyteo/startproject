package com.example.startproject.ui.feature.users.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.startproject.R
import com.example.startproject.data.model.User
import com.example.startproject.data.model.buildUserPreview
import com.example.startproject.ui.feature.common.RoundedImage

@Composable
fun UsersListItem(
    user: User,
    onItemClick: (User) -> Unit
) {
    val paddingXXSmall = dimensionResource(id = R.dimen.padding_xxsmall)
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val avatarSize = dimensionResource(id = R.dimen.avatar_size_medium)
    val dividerStartIndent = dimensionResource(id = R.dimen.user_list_item_start_indent)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(user)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium)
        ) {
            RoundedImage(
                url = user.avatarUrl,
                placeholder = R.drawable.avatar_placeholder,
                modifier = Modifier
                    .size(avatarSize)
                    .padding(end = paddingMedium)
            )
            Column {
                Text(
                    text = user.userId,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(paddingXXSmall))

                Text(
                    text = user.htmlUrl,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Divider(
            thickness = dividerStartIndent,
            modifier = Modifier.padding(end = paddingMedium)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UsersListItemPreview() {
    UsersListItem(user = buildUserPreview(), onItemClick = {})
}