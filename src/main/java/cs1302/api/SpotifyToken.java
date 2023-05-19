package cs1302.api;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;

/**
 * Define the SpotifyToken class for deserialization of the Spotify auth response.
 */
class SpotifyToken {
    @SerializedName("access_token")
    private String accessToken;

    /**
     * Getter of Spotify access token.
     *
     * @return String of access token
     */
    public String getAccessToken() {
        return accessToken;
    } // getAccessToken
} // SpotifyToken
