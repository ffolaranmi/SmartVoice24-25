package com.example.smartvoice.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val voiceSampleRepository: VoiceSampleRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val voiceSampleRepository: VoiceSampleRepository by lazy {
        OfflineVoiceSampleRepository(VoiceSampleDatabase.getDatabase(context).voiceSampleDao())
    }
}