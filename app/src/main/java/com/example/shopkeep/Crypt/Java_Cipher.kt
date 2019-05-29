package com.io.wiemcozjem.Crypt



import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object Java_Cipher {

    private val CIPHER_NAME = "DESede/CBC/PKCS5PADDING"
    private val CIPHER_KEY_LEN = 24 //128 bits


    fun encrypt(key: String, data: String): String {

        var random = Array(8){i -> Random().nextInt(9) }
        var iv = ""
        random.iterator().forEach {
            iv+=it
        }

        var pass = Array<Byte>(24){0}
        key.forEachIndexed { i, it ->
            pass[i] = it.toByte()
        }

        val keyBytes = key.toByteArray(charset("utf-8"))

        val initVector = IvParameterSpec(iv.toByteArray(charset("UTF-8")))
        val skeySpec = SecretKeySpec(pass.toByteArray(), "DESede")

        val cipher = Cipher.getInstance(Java_Cipher.CIPHER_NAME)
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, initVector)

        val encryptedData = cipher.doFinal(data.toByteArray())

        val base64_EncryptedData = android.util.Base64.encodeToString(encryptedData,android.util.Base64.DEFAULT)

        val base64_IV = android.util.Base64.encodeToString(iv.toByteArray(charset("UTF-8")),android.util.Base64.DEFAULT)

        return "$base64_EncryptedData:$base64_IV"
    }


    fun decrypt(key: String, data: String): String {

        var pass = Array<Byte>(24){0}
        key.forEachIndexed { i, it ->
            pass[i] = it.toByte()
        }

        val parts = data.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val iv = IvParameterSpec(android.util.Base64.decode(parts[1],android.util.Base64.DEFAULT))
        val skeySpec = SecretKeySpec(pass.toByteArray(), "DESede")

        val cipher = Cipher.getInstance(Java_Cipher.CIPHER_NAME)
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)

        val decodedEncryptedData = android.util.Base64.decode(parts[0],android.util.Base64.DEFAULT)


        return String(cipher.doFinal(decodedEncryptedData))
    }

}