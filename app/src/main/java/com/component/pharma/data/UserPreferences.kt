package com.component.pharma.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferences (
    context: Context
) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            name = "my_data_store"
        )
    }

    val authToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    val firstName: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[FIRST_NAME]
        }

    val lastName: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[LAST_NAME]
        }

    val pic: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[PIC]
        }

    val initial: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[INITIAL]
        }

    val profileType: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[PROFILE_TYPE]
        }

    val mobileNum: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[MOB_NUM]
        }

    val lastNotificationId: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[LAST_NOTIFICATION]
        }

    val userStatus: Flow<Int?>
        get() = dataStore.data.map { preferences ->
            preferences[USER_STATUS]
        }

    val userProfileId: Flow<Int?>
        get() = dataStore.data.map { preferences ->
            preferences[USER_PROFILE]
        }

    val userId: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[USER_ID]
        }

    val onboardingFinished: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[ONBOARDING_FINISHED]
        }

    val badgeStart: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[BADGE_START]
        }

    val notifyBadge: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[NOTIFY_BADGE]
        }
//
//    val focusFinished: Flow<String?>
//        get() = dataStore.data.map { preferences ->
//            preferences[FOCUS_FINISHED]
//        }


    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    suspend fun saveFirstName(firstName: String) {
        dataStore.edit { preferences ->
            preferences[FIRST_NAME] = firstName
        }
    }
    suspend fun saveLastName(lastName: String) {
        dataStore.edit { preferences ->
            preferences[LAST_NAME] = lastName
        }
    }

    suspend fun savePic(pic: String) {
        dataStore.edit { preferences ->
            preferences[PIC] = pic
        }
    }

    suspend fun saveProfileType(profileType: String) {
        dataStore.edit { preferences ->
            preferences[PROFILE_TYPE] = profileType
        }
    }

    suspend fun saveInitial(initial: String) {
        dataStore.edit { preferences ->
            preferences[INITIAL] = initial
        }
    }

    suspend fun saveMobile(mobile: String) {
        dataStore.edit { preferences ->
            preferences[MOB_NUM] = mobile
        }
    }

    suspend fun saveLastNotificationId(notifyId: String) {
        dataStore.edit { preferences ->
            preferences[LAST_NOTIFICATION] = notifyId
        }
    }

    suspend fun saveUserStatus(userStatus: Int) {
        dataStore.edit { preferences ->
            preferences[USER_STATUS] = userStatus
        }
    }

    suspend fun saveUserProfileId(userProfileId: Int) {
        dataStore.edit { preferences ->
            preferences[USER_PROFILE] = userProfileId
        }
    }

    suspend fun saveUserId(mobile: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = mobile
        }
    }
    suspend fun saveOnboarding(onboardingFinished: String) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_FINISHED] = onboardingFinished
        }
    }
    suspend fun saveBadgeStart(badgeStart: String) {
        dataStore.edit { preferences ->
            preferences[BADGE_START] = badgeStart
        }
    }

    suspend fun saveNotifyCartBadge(badgeStart: String) {
        dataStore.edit { preferences ->
            preferences[NOTIFY_BADGE] = badgeStart
        }
    }
//
//    suspend fun saveFocus(focusFinished: String) {
//        dataStore.edit { preferences ->
//            preferences[FOCUS_FINISHED] = focusFinished
//        }
//    }

    suspend fun clear(){
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val KEY_AUTH = preferencesKey<String>("key_auth")
        private val USER_PROFILE = preferencesKey<Int>("user_profile")
        private val FIRST_NAME = preferencesKey<String>("first_name")
        private val LAST_NAME = preferencesKey<String>("last_name")
        private val LAST_NOTIFICATION = preferencesKey<String>("last_notification")
        private val PROFILE_TYPE = preferencesKey<String>("profile_type")
        private val INITIAL = preferencesKey<String>("initial")
        private val PIC = preferencesKey<String>("pic")
        private val MOB_NUM = preferencesKey<String>("mobile_num")
        private val ONBOARDING_FINISHED = preferencesKey<String>("onboarding_finished")
        private val BADGE_START = preferencesKey<String>("badge_start")
        private val NOTIFY_BADGE = preferencesKey<String>("notify_badge")
        private val USER_STATUS = preferencesKey<Int>("user_status")
        private val USER_ID = preferencesKey<String>("user_id")
//        private val FOCUS_FINISHED = preferencesKey<String>("focus_finished")
    }
}