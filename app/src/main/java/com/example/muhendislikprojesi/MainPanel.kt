package com.example.muhendislikprojesi

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.muhendislikprojesi.storage.getThemePreference
import com.example.muhendislikprojesi.storage.saveThemePreference
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import com.example.retrofitdeneme6.retrofit.ApiUtils
import com.example.retrofitdeneme6.retrofit.ResponseMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import com.example.muhendislikprojesi.storage.clearLoginPreferences
import java.net.NetworkInterface
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPanel(navController: NavController) {
    val context = LocalContext.current

    // Mac Adresi Kısmı
    var macAddress by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        macAddress = getMacAddress(context)
        Log.d("MAC Address", macAddress)
    }

    // Tema İçin Değişkenler
    var isDarkTheme by remember { mutableStateOf(getThemePreference(context)) }

    // Veriler
    var firstName by remember { mutableStateOf("") }
    var departmentID by remember { mutableStateOf(0) }
    var id by remember { mutableStateOf(0) }
    var email by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var emailConfirmed by remember { mutableStateOf(false) }
    var securityStamp by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val sonuc = getVeri()
        if (sonuc != null) {
            departmentID = sonuc.departmentID
            firstName = sonuc.firstName
            id = sonuc.id
            email = sonuc.email
            userName = sonuc.userName
            emailConfirmed = sonuc.emailConfirmed
            securityStamp = sonuc.securityStamp
        } else {
            Log.e("SuccessScreen", "Veriler alınamadı")
        }
    }

    val activity = LocalContext.current as Activity

    // Geri tuşu işlevi
    BackHandler(onBack = {
        activity.finish()
    })

    MuhendislikProjesiTheme(darkTheme = isDarkTheme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Fotoğraf Bölümü
                Card(
                    modifier = Modifier
                        .weight(40f)
                        .height(200.dp)
                        .padding(end = 20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Image(painter = painterResource(id = R.drawable.biyometrik), contentDescription = "", Modifier.padding(top = 20.dp))
                }
                // Kullanıcı Bilgileri Bölümü
                Card(
                    modifier = Modifier
                        .weight(60f)
                        .height(200.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "$departmentID",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp)
                        Text(text = "Takip Sistemine",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp)
                        Text(text = "Hoş Geldiniz",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp)
                        Text(text = "$firstName",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp)
                        Text(text = "En Son Giriş Tarihi:",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp)
                        Text(text = "02.07.2002 ",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp)
                        Text(text = "En Son Giriş Saati:",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp)
                        Text(text = "16:07",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp)
                    }
                }
            }
            // Duyurular Bölümü
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .size(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { navController.navigate("Duyurular") },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "DUYURULAR --->", modifier = Modifier.padding(start = 25.dp))
                }
            }
            // Geçmiş Uyarılar Bölümü
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .size(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { navController.navigate("GecmisUyarilar") },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "GEÇMİŞ UYARILAR --->", modifier = Modifier.padding(start = 25.dp))
                }
            }
            // Kayıtlı Cihazlar Bölümü
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .size(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { navController.navigate("KayitliCihazlar") },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "KAYITLI CİHAZLAR --->", modifier = Modifier.padding(start = 25.dp))
                }
            }
            // Login Ekranına Dönüş Butonu Bölümü
            Button(
                onClick = {
                    clearLoginPreferences(context)
                    navController.navigate("LoginPanel")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Login Ekranı")
            }
            // Çıkış Butonu Bölümü
            Button(
                onClick = { activity.finish() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Çıkış")
            }
            // Tema Değiştirme Switchi Bölümü
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Tema Değiştir: ", color = MaterialTheme.colorScheme.onBackground)
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = {
                        isDarkTheme = it
                        saveThemePreference(context, isDarkTheme)
                    }
                )
            }

            // MAC Adresini göstermek için
            Text(text = "MAC Adresi: $macAddress", color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

//MAC Adresi Alma Fonksiyonu
fun getMacAddress(context: Context): String {
    val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    return wifiManager.connectionInfo.macAddress
}

//RETROFİT KISMI
//Get İşlemi
suspend fun getVeri(): ResponseMessage? {
    return suspendCoroutine { continuation ->
        val kisilerDaoInterface = ApiUtils.getVerilerDaoInterface()
        kisilerDaoInterface.getComments().enqueue(object : Callback<List<ResponseMessage>> {
            override fun onResponse(call: Call<List<ResponseMessage>>, response: Response<List<ResponseMessage>>) {
                if (response.isSuccessful) {
                    val verilerListesi = response.body()
                    if (!verilerListesi.isNullOrEmpty()) {
                        val veri = verilerListesi[0]
                        continuation.resume(veri)
                    } else {
                        continuation.resume(null)
                    }
                } else {
                    continuation.resume(null)
                }
            }

            override fun onFailure(call: Call<List<ResponseMessage>>, t: Throwable) {
                continuation.resume(null)
            }
        })
    }
}

@Preview
@Composable
fun MainPanelPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}