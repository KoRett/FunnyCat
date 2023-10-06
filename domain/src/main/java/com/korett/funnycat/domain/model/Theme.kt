package com.korett.funnycat.domain.model

enum class Theme(val value: Int) {
    Light(1),
    Dark(2),
    System(-1);

    companion object {
        infix fun from(value: Int): Theme? = Theme.values().firstOrNull { it.value == value }
    }
}   