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
import io.ganguo.utils.util.date.DateTime;


/**
 * 日期格式化 yyyy-MM-dd
 * <p/>
 * Created by Tony on 1/5/15.
 */
public class DateTimeFormatter implements JsonDeserializer<DateTime>, JsonSerializer<DateTime> {

    /**
     * string to date
     *
     * @param json
     * @param typeOfT
     * @param context
     * @return
     * @throws JsonParseException
     */
    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        String value = json.getAsString();
        if (Strings.isEmpty(value) || value.length() == 1) {
            return null;
        }

        return DateTime.parseFor(value);
    }

    /**
     * 实际没有用到后面的两个参数
     *
     * @param json
     * @return
     */
    public DateTime deserialize(JsonElement json) {
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
    @Override
    public JsonElement serialize(DateTime date, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(DateTime.formatFor(date));
    }

    /**
     * 实际没有用到后面的两个参数
     *
     * @param date
     * @return
     */
    public JsonElement serialize(DateTime date) {
        return serialize(date, null, null);
    }
}
