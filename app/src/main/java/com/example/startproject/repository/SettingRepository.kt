package com.example.startproject.repository

import android.content.Context
import com.example.startproject.StartProjectApp

class SettingRepository {

    private val context = StartProjectApp.applicationContext()
    private val pref = context.getSharedPreferences(PREF_SETTINGS_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREF_SETTINGS_NAME = "PREF_SETTINGS_NAME"

        const val KEY_USER_ID = "KEY_USER_ID"
        const val KEY_SESSION = "KEY_SESSION"
        const val KEY_TRUSTED_TERMINAL_ID = "KEY_TRUSTED_TERMINAL_ID"
        const val KEY_FCM_TOKEN = "KEY_FCM_TOKEN"
        const val KEY_PET_PASS_MEMBER = "KEY_PET_PASS_MEMBER_REGISTER"
        const val KEY_TICKETS = "KEY_TICKETS"
        const val KEY_HOSPITALS = "KEY_HOSPITALS"
        const val KEY_PUSH = "KEY_PUSH"
        const val KEY_SEND_CARD_NO = "KEY_SEND_CARD_NO"
        const val KEY_MESSAGE_UPDATE_DATE = "KEY_MESSAGE_UPDATE_DATE"
        const val KEY_REVIEW = "KEY_REVIEW"
    }

    fun loadUserId(): String? = pref.getString(KEY_USER_ID, null)
    fun saveUserId(userId: String?) = pref.edit().putString(KEY_USER_ID, userId).apply()

    fun loadSession(): String? = pref.getString(KEY_SESSION, null)
    fun saveSession(session: String?) = pref.edit().putString(KEY_SESSION, session).apply()

    fun loadTrustedTerminalId(): String? = pref.getString(KEY_TRUSTED_TERMINAL_ID, null)
    fun saveTrustedTerminalId(id: String?) =
        pref.edit().putString(KEY_TRUSTED_TERMINAL_ID, id).apply()

    fun loadFcmToken(): String? = pref.getString(KEY_FCM_TOKEN, null)
    fun saveFcmToken(token: String?) = pref.edit().putString(KEY_FCM_TOKEN, token).apply()

    //-------------------------------------------------------------------------
    fun loadTickets(): String? = pref.getString(KEY_TICKETS, null)
    fun saveTickets(json: String) = pref.edit().putString(KEY_TICKETS, json).apply()

    fun loadHospitals(): String? = pref.getString(KEY_HOSPITALS, null)
    fun saveHospitals(json: String) = pref.edit().putString(KEY_HOSPITALS, json).apply()

    fun loadPetPassMember(): Int = pref.getInt(KEY_PET_PASS_MEMBER, 0)
    fun savePetPass(id: Int) = pref.edit().putInt(KEY_PET_PASS_MEMBER, id).apply()

    //-------------------------------------------------------------------------
    fun isSendCardNo(cardNo: String): Boolean {
        val set = pref.getStringSet(KEY_SEND_CARD_NO, null) ?: return false
        if (set.isEmpty()) return false
        return set.contains(cardNo)
    }

    fun saveSendCardNo(cardNo: String) {
        var cards = pref.getStringSet(KEY_SEND_CARD_NO, mutableSetOf())?.toMutableSet()
        if (cards == null) cards = mutableSetOf()
        cards.add(cardNo)
        pref.edit().putStringSet(KEY_SEND_CARD_NO, cards).apply()
    }

    //-------------------------------------------------------------------------
    fun loadMessageUpdateDate(): Long = pref.getLong(KEY_MESSAGE_UPDATE_DATE, 0L)
    fun saveMessageUpdateDate(date: Long) =
        pref.edit().putLong(KEY_MESSAGE_UPDATE_DATE, date).apply()

    //-------------------------------------------------------------------------
    fun loadReviewDate(): Long = pref.getLong(KEY_REVIEW, 0L)
    fun saveReviewDate(date: Long) = pref.edit().putLong(KEY_REVIEW, date).apply()
}
