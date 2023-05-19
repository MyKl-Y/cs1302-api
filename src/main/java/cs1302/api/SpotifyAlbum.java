package cs1302.api;

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
 * Define the SpotifyAlbum class for deserialization of the Spotify album response.
 */
class SpotifyAlbum {
    private String name;

    /**
     * Getter method for name of spotify album.
     *
     * @return String of the name of the album
     */
    public String getName() {
        return name;
    } // getName
} // SpotifyAlbum
