package com.netcracker_study_autumn_2020.data.mapper;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateConverter extends TypeAdapter<Date> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            NetworkUtils.DATE_PATTERN_DB);

    @Override
    public void write(JsonWriter out, Date date) throws IOException {
        if (date == null) {
            out.nullValue();
        } else {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+3"));
            out.value(simpleDateFormat.format(date));
        }
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        return null;
    }


}