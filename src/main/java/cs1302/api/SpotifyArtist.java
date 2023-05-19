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
 * Define the SpotifyArtist class for deserialization of the Spotify artist response.
 */
class SpotifyArtist {
    private String name;

    /**
     * Getter method for name of the Spotify Artist.
     *
     * @return String of name of artist
     */
    public String getName() {
        return name;
    } // getName
} // SpotifyArtist
