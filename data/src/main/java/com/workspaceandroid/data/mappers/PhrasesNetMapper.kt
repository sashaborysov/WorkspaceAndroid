package com.workspaceandroid.data.mappers

import com.workspaceandroid.data.common.ITimeHelper
import com.workspaceandroid.data.common.TIME_FORMAT_DATE_BIRTH_PATTERN
import com.workspaceandroid.data.common.TIME_FORMAT_FULL_DATE_PATTERN
import com.workspaceandroid.data.common.TIME_FORMAT_RESPONSE_UTC_PATTERN
import com.workspaceandroid.data.common.TimeHelper
import com.workspaceandroid.data.dto.phrases.PhraseNetDTO
import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.models.phrase.PhraseInput
import javax.inject.Inject

class PhrasesNetMapper @Inject constructor(private val timeHelper: ITimeHelper) :
    EntityMapper<PhraseNetDTO, Phrase> {

    override fun mapFromEntity(entity: PhraseNetDTO): Phrase {
        val createdAtTimeStamp =
            timeHelper.getLongFromString(entity.createdAt, TIME_FORMAT_RESPONSE_UTC_PATTERN)
        return Phrase(
            id = entity.phraseId ?: -1,
            createdAt = createdAtTimeStamp,
            formattedDate = timeHelper.convertToFormattedTime(
                createdAtTimeStamp,
                TIME_FORMAT_FULL_DATE_PATTERN
            ),
            text = entity.phraseText.orEmpty(),
            imgUrl = entity.phraseImgUrl.orEmpty(),
            examples = entity.phraseExamples ?: emptyList(),
            definition = entity.phraseDefinition.orEmpty(),
            isExpanded = false
        )
    }

    override fun mapToEntity(domainModel: Phrase): PhraseNetDTO {
        return PhraseNetDTO(
            phraseText = domainModel.text,
            phraseDefinition = domainModel.definition,
            phraseExamples = domainModel.examples,
            createdAt = null,
            phraseUserId = null,
            phraseId = null,
            phraseImgUrl = null
        )
    }

    fun phraseInputToDto(initial: PhraseInput): PhraseNetDTO {
        return PhraseNetDTO(
            phraseText = initial.text,
            phraseDefinition = initial.definition,
            phraseExamples = initial.examples.values.toList(),
            createdAt = null,
            phraseUserId = null,
            phraseId = null,
            phraseImgUrl = null
        )
    }

    fun fromEntityList(initial: List<PhraseNetDTO>): List<Phrase> {
        return initial.map { mapFromEntity(it) }
    }

    fun toEntityList(initial: List<Phrase>): List<PhraseNetDTO> {
        return initial.map { mapToEntity(it) }
    }

}