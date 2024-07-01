package app.k9mail.feature.account.setup.ui.autodiscovery

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.k9mail.core.common.provider.AppNameProvider
import app.k9mail.core.ui.compose.common.mvi.observe
import app.k9mail.feature.account.setup.ui.autodiscovery.AccountAutoDiscoveryContract.AutoDiscoveryUiResult
import app.k9mail.feature.account.setup.ui.autodiscovery.AccountAutoDiscoveryContract.Effect
import app.k9mail.feature.account.setup.ui.autodiscovery.AccountAutoDiscoveryContract.Event
import app.k9mail.feature.account.setup.ui.autodiscovery.AccountAutoDiscoveryContract.ViewModel

@Composable
internal fun AccountAutoDiscoveryScreen(
    onNext: (AutoDiscoveryUiResult) -> Unit,
    onBack: () -> Unit,
    viewModel: ViewModel,
    appNameProvider: AppNameProvider,
    modifier: Modifier = Modifier,
) {
    val (state, dispatch) = viewModel.observe { effect ->
        when (effect) {
            Effect.NavigateBack -> onBack()
            is Effect.NavigateNext -> onNext(effect.result)
        }
    }

    BackHandler {
        dispatch(Event.OnBackClicked)
    }

    AccountAutoDiscoveryContent(
        state = state.value,
        onEvent = { dispatch(it) },
        oAuthViewModel = viewModel.oAuthViewModel,
        appName = appNameProvider.appName,
        modifier = modifier,
    )
}
