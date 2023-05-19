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
 * Define the SpotifyResponse class for deserialization of the Spotify response.
 */
class SpotifyResponse {
    private Tracks tracks;

    /**
     * Setter method for Tracks.
     *
     * @param tracks tracks that will be set for the response
     */
    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    } // setTracks

    /**
     * Getter method for Tracks.
     *
     * @return Tracks object of the tracks
     */
    public Tracks getTracks() {
        return tracks;
    } // getTracks

    /**
     * Custom Tracks object class for desearialization of the Spotify response.
     */
    public static class Tracks {
        private List<SpotifyTrack> items;

        /**
         * Getter method for items in a Track.
         *
         * @return List of SpotifyTrack objects of the items
         */
        public List<SpotifyTrack> getItems() {
            return items;
        } // getItems

        /**
         * Setter method for items in a track.
         *
         * @param items List of SpotifyTracks to set as items
         */
        public void setItems(List<SpotifyTrack> items) {
            this.items = items;
        } // setItems
    } // Tracks
} // SpotifyResponse
