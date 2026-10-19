package dev.iln.cobblemongo.ui.screen.settings.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.iln.cobblemongo.data.config.Language
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.settings_language
import dev.iln.cobblemongo.resources.settings_language_default
import dev.iln.cobblemongo.resources.settings_language_restart
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.screen.settings.components.SettingDropdown
import dev.iln.cobblemongo.ui.screen.settings.components.SettingsCard
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
fun LanguageSection(
    language: Language,
    onLanguageChange: (Language) -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsCard(
        title = stringResource(Res.string.settings_language),
        modifier = modifier
    ) {
        SettingDropdown(
            selected = language,
            options = Language.entries,
            onSelect = onLanguageChange
        ) {
            it.label
        }

        if (language != Language.applied) {
            AppText(
                text = stringResource(Res.string.settings_language_restart),
                style = typography.caption,
                color = colors.orange200
            )
        }
    }
}


private val Language.label: String
    @Composable get() = when (this) {
        Language.Default -> stringResource(Res.string.settings_language_default)
        Language.French -> "Français"
        Language.English -> "English"
        Language.German -> "Deutsch"
        Language.Spanish -> "Español"
        Language.Brazilian -> "Brésiliano"
    }