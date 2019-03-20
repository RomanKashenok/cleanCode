package com.kashanok.domain.entity.vehicle

enum class FleetType {
    TAXI, POOLING, UNDEFINED;

    companion object {
        fun fromString(typeString: String): FleetType {
            return when (typeString) {
                "TAXI" -> FleetType.TAXI
                "POOLING" -> POOLING
                else -> UNDEFINED
            }
        }
    }
}