package com.example.mye_pargne

data class AuthentificationResult (val token: String) {
    override fun toString(): String {
        return token
    }
}