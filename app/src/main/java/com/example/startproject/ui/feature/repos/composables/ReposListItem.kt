package com.example.startproject.ui.feature.repos.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CallSplit
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.startproject.R
import com.example.startproject.common.buildUrlIntent
import com.example.startproject.data.model.Repo
import com.example.startproject.data.model.RepoPreview
import com.example.startproject.ui.theme.OnSurfaceTextAlpha
import java.util.Locale

@Composable
fun ReposListItem(repo: Repo) {
    val paddingXXSmall = dimensionResource(id = R.dimen.padding_xxsmall)
    val paddingSmall = dimensionResource(id = R.dimen.padding_small)
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)

    val context = LocalContext.current
    val visitRepoIntent = remember{ buildUrlIntent(repo.htmlUrl) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { context.startActivity(visitRepoIntent) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium)
        ) {
            Column {
                Text(
                    text = repo.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(paddingXXSmall))

                Text(
                    text = repo.description ?: stringResource(R.string.no_description_informed),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = OnSurfaceTextAlpha),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(paddingSmall))

                Text(
                    text = repo.language?.uppercase(Locale.getDefault())
                        ?: stringResource(R.string.language_not_defined),
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.size(paddingMedium))

                CounterSession(repo = repo)
            }
        }
        Divider()
    }
}

@Composable
fun CounterSession(repo: Repo) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        CounterItem(icon = Icons.Outlined.Visibility, counterText = repo.watchersCount.toString())
        CounterItem(icon = Icons.Outlined.StarBorder, counterText = repo.stargazersCount.toString())
        CounterItem(icon = Icons.Outlined.CallSplit, counterText = repo.forksCount.toString())
    }
}

@Composable
fun CounterItem(
    icon: ImageVector,
    counterText: String
) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = OnSurfaceTextAlpha)
    )
    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_xsmall)))
    Text(
        text = counterText,
        style = MaterialTheme.typography.titleLarge,
    )
    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))
}

@Preview(showBackground = true)
@Composable
fun UsersListItemPreview() {
    ReposListItem(repo = RepoPreview.repo)
}