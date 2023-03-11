package net.deechael.invokationtcg.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.deechael.dutil.gson.JOReader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GedAccountAPIAccessor {

    private final static OkHttpClient HTTP_CLIENT = new OkHttpClient();

    public static List<String> listSessions() {
        try {
            Response response = HTTP_CLIENT.newCall(new Request.Builder().url("http://127.0.0.1:65321/internal/sessions/list").build()).execute();
            InputStream inputStream = Objects.requireNonNull(response.body()).byteStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            JOReader reader = new JOReader(JsonParser.parseReader(inputStreamReader).getAsJsonObject());
            inputStreamReader.close();
            inputStream.close();
            response.close();
            return reader.array("sessions")
                    .original()
                    .asList()
                    .stream()
                    .map(JsonElement::getAsString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUserIdBySession(String session) {
        // TODO
        return null;
    }

}
