package io.ganguo.utils.json

import io.ganguo.utils.isEmpty
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * 人工解析Json数据
 * 直接转java object推荐使用[Gsons]
 *
 *
 * Created by Wilson on 7/12/15.
 */
object Jsons {
    var isPrintException = true

    /**
     * get Long from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if key is null or empty, return defaultValue
     *  * if [JSONObject.getLong] exception, return defaultValue
     *  * return [JSONObject.getLong]
     *
     */
    fun getLong(jsonObject: JSONObject?, key: String?, defaultValue: Long): Long {
        return if (jsonObject == null || isEmpty(key)) {
            defaultValue
        } else try {
            jsonObject.getLong(key.orEmpty())
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get Long from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if jsonData [JSONObject.JSONObject] exception, return defaultValue
     *  * return [Jsons.getLong]
     *
     */
    fun getLong(jsonData: String, key: String, defaultValue: Long): Long {
        return if (isEmpty(jsonData)) {
            defaultValue
        } else try {
            val jsonObject = JSONObject(jsonData)
            getLong(jsonObject, key, defaultValue)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }


    /**
     * get Int from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if key is null or empty, return defaultValue
     *  * if [JSONObject.getInt] exception, return defaultValue
     *  * return [JSONObject.getInt]
     *
     */
    fun getInt(jsonObject: JSONObject, key: String, defaultValue: Int): Int {
        return if (isEmpty(key)) {
            defaultValue
        } else try {
            jsonObject.getInt(key)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get Int from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if jsonData [JSONObject.JSONObject] exception, return defaultValue
     *  * return [Jsons.getInt]
     *
     */
    fun getInt(jsonData: String, key: String, defaultValue: Int): Int {
        return if (isEmpty(jsonData)) {
            defaultValue
        } else try {
            val jsonObject = JSONObject(jsonData)
            getInt(jsonObject, key, defaultValue)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }


    /**
     * get Double from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if key is null or empty, return defaultValue
     *  * if [JSONObject.getDouble] exception, return defaultValue
     *  * return [JSONObject.getDouble]
     *
     */
    fun getDouble(
        jsonObject: JSONObject?,
        key: String?,
        defaultValue: Double
    ): Double {
        return if (jsonObject == null || isEmpty(key)) {
            defaultValue
        } else try {
            jsonObject.getDouble(key.orEmpty())
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get Double from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if jsonData [JSONObject.JSONObject] exception, return defaultValue
     *  * return [Jsons.getDouble]
     *
     */
    fun getDouble(
        jsonData: String,
        key: String,
        defaultValue: Double
    ): Double {
        return if (isEmpty(jsonData)) {
            defaultValue
        } else try {
            val jsonObject = JSONObject(jsonData)
            getDouble(jsonObject, key, defaultValue)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }


    /**
     * get String from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if key is null or empty, return defaultValue
     *  * if [JSONObject.getString] exception, return defaultValue
     *  * return [JSONObject.getString]
     *
     */
    fun getString(
        jsonObject: JSONObject?,
        key: String?,
        defaultValue: String?
    ): String? {
        return if (jsonObject == null || isEmpty(key)) {
            defaultValue
        } else try {
            jsonObject.getString(key.orEmpty())
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get String from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if jsonData [JSONObject.JSONObject] exception, return defaultValue
     *  * return [Jsons.getString]
     *
     */
    fun getString(
        jsonData: String,
        key: String,
        defaultValue: String
    ): String? {
        return if (isEmpty(jsonData)) {
            defaultValue
        } else try {
            val jsonObject = JSONObject(jsonData)
            getString(jsonObject, key, defaultValue)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get String from jsonObject
     *
     * @param jsonObject
     * @param defaultValue
     * @param keyArray
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if keyArray is null or empty, return defaultValue
     *  * get [.getJSONObject] by recursion, return it. if anyone is
     * null, return directly
     *
     */
    fun getStringCascade(
        jsonObject: JSONObject,
        defaultValue: String,
        vararg keyArray: String
    ): String? {
        if (keyArray.isEmpty()) {
            return defaultValue
        }
        var data: String? = jsonObject.toString()
        for (key in keyArray) {
            data = getStringCascade(data.orEmpty(), key, defaultValue)
            if (data == null) {
                return defaultValue
            }
        }
        return data
    }

    /**
     * get String from jsonData
     *
     * @param jsonData
     * @param defaultValue
     * @param keyArray
     * @return
     *  * if jsonData is null, return defaultValue
     *  * if keyArray is null or empty, return defaultValue
     *  * get [.getJSONObject] by recursion, return it. if anyone is
     * null, return directly
     *
     */
    fun getStringCascade(
        jsonData: String,
        defaultValue: String,
        vararg keyArray: String
    ): String? {
        if (isEmpty(jsonData)) {
            return defaultValue
        }
        var data = jsonData
        for (key in keyArray) {
            data = getString(data, key, defaultValue).toString()
        }
        return data
    }

    /**
     * get String array from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if key is null or empty, return defaultValue
     *  * if [JSONObject.getJSONArray] exception, return defaultValue
     *  * if [JSONArray.getString] exception, return defaultValue
     *  * return string array
     *
     */
    fun getStringArray(
        jsonObject: JSONObject,
        key: String,
        defaultValue: Array<String>
    ): Array<String> {
        if (isEmpty(key)) {
            return defaultValue
        }
        return try {
            val statusArray = jsonObject.getJSONArray(key)
            val value: Array<String> = arrayOf()
            val values =
                arrayOfNulls<String>(statusArray.length())
            for (i in 0 until statusArray.length()) {
                values[i] = statusArray.getString(i)
            }
            value
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get String array from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if jsonData [JSONObject.JSONObject] exception, return defaultValue
     *  * return [Jsons.getStringArray]
     *
     */
    fun getStringArray(
        jsonData: String,
        key: String,
        defaultValue: Array<String>
    ): Array<String> {
        return if (isEmpty(jsonData)) {
            defaultValue
        } else try {
            val jsonObject = JSONObject(jsonData)
            getStringArray(jsonObject, key, defaultValue)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get String list from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if key is null or empty, return defaultValue
     *  * if [JSONObject.getJSONArray] exception, return defaultValue
     *  * if [JSONArray.getString] exception, return defaultValue
     *  * return string array
     *
     */
    fun getStringList(
        jsonObject: JSONObject,
        key: String,
        defaultValue: List<String>
    ): List<String> {
        if (isEmpty(key)) {
            return defaultValue
        }
        return try {
            val statusArray = jsonObject.getJSONArray(key)
            val list: MutableList<String> =
                ArrayList()
            for (i in 0 until statusArray.length()) {
                list.add(statusArray.getString(i))
            }
            list
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get String list from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if jsonData [JSONObject.JSONObject] exception, return defaultValue
     *  * return [Jsons.getStringList]
     *
     */
    fun getStringList(
        jsonData: String,
        key: String,
        defaultValue: List<String>
    ): List<String> {
        return if (isEmpty(jsonData)) {
            defaultValue
        } else try {
            val jsonObject = JSONObject(jsonData)
            getStringList(jsonObject, key, defaultValue)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get JSONObject from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if key is null or empty, return defaultValue
     *  * if [JSONObject.getJSONObject] exception, return defaultValue
     *  * return [JSONObject.getJSONObject]
     *
     */
    fun getJSONObject(
        jsonObject: JSONObject,
        key: String,
        defaultValue: JSONObject
    ): JSONObject {
        return if (isEmpty(key)) {
            defaultValue
        } else try {
            jsonObject.getJSONObject(key)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get JSONObject from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonData is null, return defaultValue
     *  * if jsonData [JSONObject.JSONObject] exception, return defaultValue
     *  * return [Jsons.getJSONObject]
     *
     */
    fun getJSONObject(
        jsonData: String,
        key: String,
        defaultValue: JSONObject
    ): JSONObject {
        return if (isEmpty(jsonData)) {
            defaultValue
        } else try {
            val jsonObject = JSONObject(jsonData)
            getJSONObject(jsonObject, key, defaultValue)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get JSONObject from jsonObject
     *
     * @param jsonObject
     * @param defaultValue
     * @param keyArray
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if keyArray is null or empty, return defaultValue
     *  * get [.getJSONObject] by recursion, return it. if anyone is
     * null, return directly
     *
     */
    fun getJSONObjectCascade(
        jsonObject: JSONObject,
        defaultValue: JSONObject,
        vararg keyArray: String
    ): JSONObject? {
        if (keyArray.isEmpty()) {
            return defaultValue
        }
        var js = jsonObject
        for (key in keyArray) {
            js = getJSONObject(js, key, defaultValue)
        }
        return js
    }

    /**
     * get JSONObject from jsonData
     *
     * @param jsonData
     * @param defaultValue
     * @param keyArray
     * @return
     *  * if jsonData is null, return defaultValue
     *  * if keyArray is null or empty, return defaultValue
     *  * get [.getJSONObject] by recursion, return it. if anyone is
     * null, return directly
     *
     */
    fun getJSONObjectCascade(
        jsonData: String,
        defaultValue: JSONObject,
        vararg keyArray: String
    ): JSONObject? {
        return if (isEmpty(jsonData)) {
            defaultValue
        } else try {
            val jsonObject = JSONObject(jsonData)
            getJSONObjectCascade(jsonObject, defaultValue, *keyArray)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get JSONArray from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if key is null or empty, return defaultValue
     *  * if [JSONObject.getJSONArray] exception, return defaultValue
     *  * return [JSONObject.getJSONArray]
     *
     */
    fun getJSONArray(
        jsonObject: JSONObject,
        key: String,
        defaultValue: JSONArray
    ): JSONArray {
        return if (isEmpty(key)) {
            defaultValue
        } else try {
            jsonObject.getJSONArray(key)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get JSONArray from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if jsonData [JSONObject.JSONObject] exception, return defaultValue
     *  * return
     *
     */
    fun getJSONArray(
        jsonData: String,
        key: String,
        defaultValue: JSONArray
    ): JSONArray {
        return if (isEmpty(jsonData)) {
            defaultValue
        } else try {
            val jsonObject = JSONObject(jsonData)
            getJSONArray(jsonObject, key, defaultValue)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get Boolean from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if key is null or empty, return defaultValue
     *  * return [JSONObject.getBoolean]
     *
     */
    fun getBoolean(
        jsonObject: JSONObject,
        key: String,
        defaultValue: Boolean
    ): Boolean {
        return if (isEmpty(key)) {
            defaultValue
        } else try {
            jsonObject.getBoolean(key)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get Boolean from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     *  * if jsonObject is null, return defaultValue
     *  * if jsonData [JSONObject.JSONObject] exception, return defaultValue
     *  * return [Jsons.getBoolean]
     *
     */
    fun getBoolean(
        jsonData: String,
        key: String,
        defaultValue: Boolean
    ): Boolean {
        return if (isEmpty(jsonData)) {
            defaultValue
        } else try {
            val jsonObject = JSONObject(jsonData)
            getBoolean(jsonObject, key, defaultValue)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            defaultValue
        }
    }

    /**
     * get map from jsonObject.
     *
     * @param jsonObject key-value pairs json
     * @param key
     * @return
     *  * if jsonObject is null, return null
     *  * return [Jsons.parseKeyAndValueToMap]
     *
     */
    fun getMap(
        jsonObject: JSONObject?,
        key: String?
    ): Map<String, String?>? {
        return parseKeyAndValueToMap(getString(jsonObject, key, null))
    }

    /**
     * get map from jsonData.
     *
     * @param jsonData key-value pairs string
     * @param key
     * @return
     *  * if jsonData is null, return null
     *  * if jsonData length is 0, return empty map
     *  * if jsonData [JSONObject.JSONObject] exception, return null
     *  * return [Jsons.getMap]
     *
     */
    fun getMap(
        jsonData: String?,
        key: String?
    ): Map<String, String?>? {
        if (jsonData == null) {
            return null
        }
        return if (jsonData.length == 0) {
            HashMap()
        } else try {
            val jsonObject = JSONObject(jsonData)
            getMap(jsonObject, key)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            null
        }
    }

    /**
     * parse key-value pairs to map. ignore empty key, if getValue exception, put empty value
     *
     * @param sourceObj key-value pairs json
     * @return
     *  * if sourceObj is null, return null
     *  * else parse entry one by one
     *
     */
    fun parseKeyAndValueToMap(sourceObj: JSONObject?): Map<String, String?>? {
        if (sourceObj == null) {
            return null
        }
        val keyAndValueMap: MutableMap<String, String?> =
            HashMap()
        val iter: Iterator<*> = sourceObj.keys()
        while (iter.hasNext()) {
            val key = iter.next() as String
            keyAndValueMap[key] = getString(sourceObj, key, "")
        }
        return keyAndValueMap
    }

    /**
     * parse key-value pairs to map. ignore empty key, if getValue exception, put empty value
     *
     * @param source key-value pairs json
     * @return
     *  * if source is null or source's length is 0, return empty map
     *  * if source [JSONObject.JSONObject] exception, return null
     *  * return [Jsons.parseKeyAndValueToMap]
     *
     */
    fun parseKeyAndValueToMap(source: String?): Map<String, String?>? {
        return if (isEmpty(source)) {
            null
        } else try {
            val jsonObject = JSONObject(source.orEmpty())
            parseKeyAndValueToMap(jsonObject)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            null
        }
    }

    fun containsKey(jsonObject: JSONObject?, key: String?): Boolean {
        return if (jsonObject == null || isEmpty(key)) {
            false
        } else jsonObject.has(key)
    }

    fun containsKey(jsonData: String?, key: String?): Boolean {
        return if (isEmpty(jsonData)) {
            false
        } else try {
            val jsonObject = JSONObject(jsonData.orEmpty())
            containsKey(jsonObject, key)
        } catch (e: JSONException) {
            if (isPrintException) {
                e.printStackTrace()
            }
            false
        }
    }
}