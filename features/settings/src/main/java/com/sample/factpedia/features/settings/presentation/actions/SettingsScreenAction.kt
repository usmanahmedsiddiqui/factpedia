package com.sample.factpedia.features.settings.presentation.actions

import com.sample.factpedia.datastore.ThemePreference

sealed interface SettingsScreenAction {
    data class ThemeChanged(val preference: ThemePreference): SettingsScreenAction
}