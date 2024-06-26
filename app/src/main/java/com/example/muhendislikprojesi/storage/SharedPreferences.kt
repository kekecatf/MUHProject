package com.example.tokentry.storage

import android.content.Context
import com.example.tokentry.retrofitt.LoginRequest

// Giriş bilgilerini SharedPreferences'a kaydetme fonksiyonu
fun saveLoginInfo(context: Context, email: String, password: String, rememberMe: Boolean) {
    val sharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("email", email)
    editor.putString("password", password)
    editor.putBoolean("remember_me", rememberMe)
    editor.apply()
}

// Giriş Bilgilerini SharedPreferences'tan Alma Fonksiyonu
fun getLoginInfo(context: Context): LoginRequest {
    val sharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    val email = sharedPreferences.getString("email", "") ?: ""
    val password = sharedPreferences.getString("password", "") ?: ""
    return LoginRequest(email, password)
}
//Giriş Bilgilerini Silme Fonksiyonu
fun clearLoginPreferences(context: Context) {
    val sharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    with (sharedPreferences.edit()) {
        clear()
        apply()
    }
}

//"Beni Hatırla" Seçeneğini Kontrol Eden Fonksiyon
fun isRememberMeEnabled(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("remember_me", false)
}

//Tema Tercihini Kaydetme Fonksiyonu
fun saveThemePreference(context: Context, isDarkTheme: Boolean) {
    val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("dark_theme", isDarkTheme)
    editor.apply()
}

//Tema Tercihini Alma Fonksiyonu
fun getThemePreference(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("dark_theme", false)
}