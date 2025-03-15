package com.example.smartvoice.data

import kotlinx.coroutines.flow.Flow

class OfflineVoiceSampleRepository (private val voiceSampleDAO: VoiceSampleDAO) : VoiceSampleRepository {
    override fun getAllVoiceSampleStream(): Flow<List<VoiceSample>> = voiceSampleDAO.getAllVoiceSamples()

    override fun getVoiceSampleStream(id: Int): Flow<VoiceSample?> = voiceSampleDAO.getVoiceSample(id)

    override suspend fun insertVoiceSample(voiceSample: VoiceSample) = voiceSampleDAO.insert(voiceSample)

    override suspend fun deleteVoiceSample(voiceSample: VoiceSample) = voiceSampleDAO.delete(voiceSample)

    override suspend fun updateVoiceSample(voiceSample: VoiceSample) = voiceSampleDAO.update(voiceSample)
}