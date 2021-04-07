package com.aldredo.qrcode.data.repository

import android.os.AsyncTask
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.aldredo.core.base.util.IManagerInfoDevice
import java.net.URL

class RegisterForPushNotificationsAsync(private val deviceToken: String, private val token: IManagerInfoDevice) :
    AsyncTask<Void, Void, Any>() {

    override fun doInBackground(vararg params: Void): Any {
        return try {
            // Register the device for notifications

            // Registration succeeded, log token to logcat
            Log.d("Pushy", "Pushy device token: " + deviceToken)

            // Send the token to your backend server via an HTTP GET request
            URL("https://{YOUR_API_HOSTNAME}/register/device?token=" + deviceToken).openConnection()

            // Provide token to onPostExecute()
            deviceToken
        } catch (exc: Exception) {
            // Registration failed, provide exception to onPostExecute()
            exc
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPostExecute(result: Any) {
        // Registration failed?
        var message = if (result is Exception) {
            // Log to console
            Log.e("Pushy", result.message.toString())
            // Display error in alert
        } else {
            // Registration success, result is device token
            token.getInfoDevice()?.notification_id = result.toString()
            "Pushy device token: " + result.toString() + "\n\n(copy from logcat)"
        }
    }
}