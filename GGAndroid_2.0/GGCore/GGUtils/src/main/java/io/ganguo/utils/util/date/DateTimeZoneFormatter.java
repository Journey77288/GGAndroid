package io.ganguo.utils.util.date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import io.ganguo.utils.util.Strings;
import io.ganguo.utils.util.date.DateTimeZone;


/**
 * 日期格式化 yyyy-MM-dd
 * <p/>
 * Created by Tony on 1/5/15.
 */
public class DateTimeZoneFormatter implements JsonDeserializer<DateTimeZone>, JsonSerializer<DateTimeZone> {

    /**
     * string to date
     *
     * @param json
     * @param typeOfT
     * @param context
     * @return
     * @throws JsonParseException
     */
    public DateTimeZone deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        String value = json.getAsString();
        if (Strings.isEmpty(value) || value.length() == 1) {
            return null;
        }

        return DateTimeZone.parseFor(value);
    }

    /**
     * 没有用到后面两个参数
     *
     * @param json
     * @return
     */
    public DateTimeZone deserialize(JsonElement json) {
        return deserialize(json, null, null);
    }

    /**
     * date to string
     *
     * @param date
     * @param type
     * @param context
     * @return
     */
    public JsonElement serialize(DateTimeZone date, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(DateTimeZone.formatFor(date));
    }

    /**
     * 没有用到后面两个参数
     *
     * @param date
     * @return
     */
    public JsonElement serialize(DateTimeZone date) {
        return serialize(date, null, null);
    }
}