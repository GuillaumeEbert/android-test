package com.shindra.chargemap.feature.planedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shindra.chargemap.core.designsystem.components.FullScreenErrorScreen
import com.shindra.chargemap.core.designsystem.components.KeyValueTextConfig
import com.shindra.chargemap.core.designsystem.components.KeyValueUnit
import com.shindra.chargemap.core.designsystem.components.UiStateContent

@Composable
internal fun PlaneDetailScreenRoute(
    onTitleChange: (String) -> Unit,
    manufacturer: String,
    model: String,
    viewModel: PlaneDetailsViewModel = hiltViewModel()
) {

    val planesUiState by viewModel.planeDetailsState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = "$manufacturer$model") {
        onTitleChange("$manufacturer $model")
    }

    PlaneDetailScreen(
        planeDetail = planesUiState,
        onRetry = { viewModel.planeDetails() }
    )
}


@Composable
private fun PlaneDetailScreen(
    planeDetail: PlaneDetailUiState,
    onRetry: () -> Unit
) {
    UiStateContent(
        onError = { FullScreenErrorScreen(onRetry) },
        state = planeDetail
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            it.forEach { d ->
                KeyValueUnit(
                    key = stringResource(id = d.key),
                    value = d.value,
                    unit = d.unit?.let { stringResource(it) },
                    keyTextConfig = KeyValueTextConfig.defaultText(fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}