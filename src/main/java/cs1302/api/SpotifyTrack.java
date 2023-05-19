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
 * Define the SpotifyTrack class for deserialization of the Spotify track response.
 */
class SpotifyTrack {
    private String id;
    private String name;
    private List<SpotifyArtist> artists;
    private SpotifyAlbum album;
    @SerializedName("duration_ms")
    private long durationMs;
    private int popularity;

    /**
     * Getter method for SpotifyTrack's id.
     *
     * @return String of ID
     */
    public String getId() {
        return id;
    } // getId

    /**
     * Getter method for SpotifyTrack's name.
     *
     * @return String of name
     */
    public String getName() {
        return name;
    } // getName

    /**
     * Getter method for SpotifyTrack's artists.
     *
     * @return List of SpotifyArtist objects
     */
    public List<SpotifyArtist> getArtists() {
        return artists;
    } // getArtists

    /**
     * Getter method for SpotifyTrack's album.
     *
     * @return SpotifyAlbum of the track
     */
    public SpotifyAlbum getAlbum() {
        return album;
    } // getAlbum

    /**
     * Getter method for SpotifyTrack's duration.
     *
     * @return long of duration
     */
    public long getDurationMs() {
        return durationMs;
    } // getDurationMs

    /**
     * Getter method for SpotifyTrack's popularity.
     *
     * @return int of popularity
     */
    public int getPopularity() {
        return popularity;
    } // getPopularity

} // SpotifyTrack
