package com.custom.relish.font.utils

import android.content.Context
import android.content.res.AssetManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


/**
 *author: hss
 *date: 2024/5/15 11:19
 *class desc:
 **/
class ExportSQLiteToJson(context: Context) {

    /*font*/
//    private val DB_NAME = "Font.sqlite"
//    private val TABLE_NAME = "FONTSMAP"

    /*emoji*/
    private val DB_NAME = "Emoji.sqlite"
    private val TABLE_NAME = "EmoJiArt"
//    private val TABLE_NAME = "EmoJiArtTagIndexer" // 无数据
//    private val TABLE_NAME = "EmojiArtHistory" //无数据



    private val context: Context = context

    fun exportDatabaseToJson() {

        // 打开 Assets 文件夹中的 SQLite 数据库文件
        val assetManager: AssetManager = context.assets
        val inputStream = assetManager.open(DB_NAME)

        val outFile = File(context.filesDir, TABLE_NAME)
        val outputStream: OutputStream = FileOutputStream(outFile)

        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }

        inputStream.close()
        outputStream.close()

        val db =
            SQLiteDatabase.openDatabase(outFile.absolutePath, null, SQLiteDatabase.OPEN_READONLY)

        // 执行查询语句
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        // 将查询结果转换为 JSON
        val jsonArray = JSONArray()
        if (cursor.moveToFirst()) {
            do {
                val jsonObject = JSONObject()
                for (i in 0 until cursor.columnCount) {
                    val columnName = cursor.getColumnName(i)
                    val columnValue = cursor.getString(i)
                    try {
                        jsonObject.put(columnName, columnValue)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                jsonArray.put(jsonObject)
            } while (cursor.moveToNext())
        }

        // 关闭数据库和游标
        cursor.close()
        db.close()

        // 输出 JSON 字符串
        val jsonString = jsonArray.toString()
        Log.d("JSON Data", jsonString)
    }
}