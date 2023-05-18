package com.aeml.lolatools.tools

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import com.aeml.lolatools.R
import java.io.*
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object ChaptersContentManager {

    @Throws(Exception::class)

    fun updateSecureStorage(context: Context, chapterName: String, chapterContent: String) {

        require(chapterName.length <= MAX_NAME_LENGTH) {context.getString(R.string.lola_section_name_error) }

        val content = readEncryptedFile(context)

        // Buscar el nombre del capítulo
        val chapterNameStart = content.indexOf("[${chapterName}]")
        if (chapterNameStart != -1) {
            // Encontrar el final del contenido del capítulo actual
            val chapterEnd = content.indexOf("\n\n\n\n\n", chapterNameStart) // 5 saltos de línea
            if (chapterEnd != -1) {
                // Reemplazar el contenido del capítulo existente
                val newContent = content.substring(0, chapterNameStart) +
                        "[${chapterName}]\n${chapterContent}\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        content.substring(chapterEnd)
                writeEncryptedFile(context,newContent)
            }
        } else {
            // Agregar el contenido del capítulo al final del archivo
            val newContent = "$content\n[${chapterName}]\n${chapterContent}\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n"
            writeEncryptedFile(context,newContent)
        }



        var newContent = readEncryptedFile(context)
        newContent += """
            
            
            
            
            
            [$chapterName]
            $chapterContent
            """.trimIndent()
        writeEncryptedFile(context, newContent)
    }

    @Throws(Exception::class)
    private fun readEncryptedFile(context: Context): String {
        val masterKey: MasterKey = MasterKey.Builder(context, KEY_NAME)
            .setKeyGenParameterSpec(
                KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(256)
                    .build()
            )
            .build()
        val encryptedFile = File(context.getFilesDir(), ENCRYPTED_FILE_NAME)
        val builder: EncryptedFile.Builder = EncryptedFile.Builder(
            context,
            encryptedFile,
            masterKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        )
        val encryptedFileInstance: EncryptedFile = builder.build()
        val reader = BufferedReader(
            InputStreamReader(
                encryptedFileInstance.openFileInput(),
                StandardCharsets.UTF_8
            )
        )
        var line: String?
        val stringBuilder = StringBuilder()
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        reader.close()
        return stringBuilder.toString()
    }

    fun getChapterContent(chapterName: String,context: Context): String? {
        val fileContent = readEncryptedFile(context)
        val startIndex = fileContent.indexOf("[${chapterName}]") + chapterName.length + 2
        if (startIndex == -1) {
            return null
        }
        val endIndex = fileContent.indexOf("\n\n\n\n\n", startIndex)
        if (endIndex == -1) {
            return fileContent.substring(startIndex)
        }
        return fileContent.substring(startIndex, endIndex)
    }

    @Throws(Exception::class)
    private fun writeEncryptedFile(context: Context, content: String) {
        val masterKey: MasterKey = MasterKey.Builder(context, KEY_NAME)
            .setKeyGenParameterSpec(
                KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(256)
                    .build()
            )
            .build()
        val encryptedFile = File(context.getFilesDir(), ENCRYPTED_FILE_NAME)
        val builder: EncryptedFile.Builder = EncryptedFile.Builder(
            context,
            encryptedFile,
            masterKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        )
        val encryptedFileInstance: EncryptedFile = builder.build()
        val writer = BufferedWriter(
            OutputStreamWriter(
                encryptedFileInstance.openFileOutput(),
                StandardCharsets.UTF_8
            )
        )
        writer.write(content)
        writer.flush()
        writer.close()
    }


    fun encryptContent(content: String, password: String): String {
        val encryptedContent = encryptString(content, password)
        return saveToFile(encryptedContent)
    }

    private fun encryptString(content: String, password: String): ByteArray {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(password.toByteArray(Charsets.UTF_8))
        val key = md.digest()

        val cipher = javax.crypto.Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, javax.crypto.spec.SecretKeySpec(key, "AES"))

        return cipher.doFinal(content.toByteArray(Charsets.UTF_8))
    }

    private fun saveToFile(encryptedContent: ByteArray): String {
        val file = File("${getInternalStoragePath()}/encrypted_content.txt")
        val fos = FileOutputStream(file)
        fos.write(encryptedContent)
        fos.close()

        return file.absolutePath
    }

    private fun getInternalStoragePath(): String {
        return File("").absolutePath
    }
}