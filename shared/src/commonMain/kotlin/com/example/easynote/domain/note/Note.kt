package com.example.easynote.domain.note

import com.example.easynote.presentation.BabyBlueHex
import com.example.easynote.presentation.LightGreenHex
import com.example.easynote.presentation.RedOrangeHex
import com.example.easynote.presentation.RedPinkHex
import com.example.easynote.presentation.VioletHex
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
) {
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex)

        fun generateRandomColor() = colors.random()
    }
}