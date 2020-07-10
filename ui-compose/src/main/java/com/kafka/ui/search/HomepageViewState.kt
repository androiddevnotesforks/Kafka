package com.kafka.ui.search

import com.kafka.data.model.RowItems
import com.kafka.data.entities.Item
import com.kafka.data.entities.Language
import com.kafka.ui.actions.PlayerCommand
import com.kafka.ui.player.PlayerData
import com.kafka.ui_common.BaseViewState

/**
 * @author Vipul Kumar; dated 27/12/18.
 */
data class HomepageViewState(
    var query: String? = null,
    var selectedLanguages: List<Language>? = null,
    var favorites: List<Item>? = null,
    var homepageItems: RowItems = RowItems(),
    var isLoading: Boolean = false,
    var playerData: PlayerData = PlayerData(),
    val playerCommand: (PlayerCommand) -> Unit = {}
) : BaseViewState

data class SearchQuery(val text: String? = null, val type: SearchQueryType)

sealed class SearchQueryType {
    object Creator : SearchQueryType()
    object Title : SearchQueryType()
    object Collection : SearchQueryType()
}

