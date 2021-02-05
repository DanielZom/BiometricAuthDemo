package hu.daniel.biometricauthdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login.setOnClickListener { createBiometricPrompt().authenticate(createPromptInfo()) }
    }

    private fun createBiometricPrompt(): BiometricPrompt {
        return BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    showToast("onAuthenticationError: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    //Biometric auth succeeded, the "result" represents an encrypted identifier
                    showToast("onAuthenticationSucceeded, type num: ${result.authenticationType}")
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    //Biometric not belongs to user
                    showToast("onAuthenticationFailed")
                }
            })
    }

    private fun createPromptInfo() = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Title")
        .setSubtitle("Subtitle")
        .setDescription("Description")
//        .setAllowedAuthenticators(DEVICE_CREDENTIAL)
        .setAllowedAuthenticators(BIOMETRIC_WEAK) //BIOMETRIC_WEAK: no encrypted respond, BIOMETRIC_STRONG has encrypted respond in onAuthenticationSucceeded
        .setNegativeButtonText("Cancel")
        .build()

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}