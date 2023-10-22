package com.example.startproject.ui.feature.repos.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.startproject.R
import com.example.startproject.common.buildUrlIntent
import com.example.startproject.data.model.UserDetail
import com.example.startproject.data.model.buildUserDetailPreview
import com.example.startproject.ui.theme.OnSurfaceBackgroundAlpha
import com.example.startproject.ui.theme.OnSurfaceTextAlpha
import java.util.Locale

@Composable
fun ReposListHeader(userDetail: UserDetail) {
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val paddingXSmall = dimensionResource(id = R.dimen.padding_xsmall)

    Column{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium)
        ) {
            ScoreSession(userDetail)

            Spacer(modifier = Modifier.size(paddingXSmall))

            UserDetailSession(userDetail)

            Spacer(modifier = Modifier.size(paddingMedium))

            ButtonsSession(userDetail)
        }

        Divider(
            thickness = paddingMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = OnSurfaceBackgroundAlpha)
        )
    }
}

@Composable
fun ScoreSession(userDetail: UserDetail) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        RoundedImage(
            url = userDetail.avatarUrl,
            placeholder = R.drawable.avatar_placeholder,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.avatar_size_large))
                .padding(end = dimensionResource(id = R.dimen.padding_medium))
        )

        ScoreItem(count = userDetail.publicRepos, description = stringResource(R.string.repos_score_title))
        ScoreItem(count = userDetail.followers, description = stringResource(R.string.followers_score_title))
        ScoreItem(count = userDetail.following, description = stringResource(R.string.following_score_title))
    }
}

@Composable
fun UserDetailSession(userDetail: UserDetail) {
    Text(
        text = userDetail.name,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = userDetail.location ?: stringResource(R.string.location_not_defined),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = OnSurfaceTextAlpha)
    )
}

@Composable
fun ButtonsSession(userDetail: UserDetail) {
    // See all button
    val context = LocalContext.current
    val profileIntent = remember{ buildUrlIntent(userDetail.htmlUrl) }
    val blogIntent = remember{ buildUrlIntent(userDetail.blogUrl) }

    // View Blog
    val blogNotFoundDialog = remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = {
            context.startActivity(profileIntent)
        }) {
            Text(text = stringResource(R.string.button_see_all_title))
        }

        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))

        OutlinedButton(onClick = {
            if (userDetail.blogUrl.isEmpty()) {
                blogNotFoundDialog.value = true
            } else {
                context.startActivity(blogIntent)
            }
        }) {
            Text(text = stringResource(R.string.button_view_blog_title))
        }
    }

    if (blogNotFoundDialog.value) {
        AlertDialog(onDismissRequest = {
            blogNotFoundDialog.value = false
        },
            title = {
                Text(text = stringResource(R.string.blog_not_found_dialog_title))
            },
            text = {
                Text(text = stringResource(R.string.blog_not_found_dialog_text))
            },
            confirmButton = {
                Text(text = stringResource(R.string.blog_not_found_dialog_confirm_button).uppercase(
                    Locale.getDefault()
                ))
            })
    }
}

@Composable
fun ScoreItem(count: Int, description: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = OnSurfaceTextAlpha)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReposUserDetailPreview() {
    ReposListHeader(userDetail = buildUserDetailPreview())
}