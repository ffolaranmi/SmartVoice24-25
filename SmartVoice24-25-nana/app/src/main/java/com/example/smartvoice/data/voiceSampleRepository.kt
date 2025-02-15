package com.example.smartvoice.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface VoiceSampleRepository {
    fun getAllVoiceSampleStream(): Flow<List<VoiceSample>>
    fun getVoiceSampleStream(id: Int): Flow<VoiceSample?>
    suspend fun insertVoiceSample(voiceSample: VoiceSample)
    suspend fun deleteVoiceSample(voiceSample: VoiceSample)
    suspend fun updateVoiceSample(voiceSample: VoiceSample)
}