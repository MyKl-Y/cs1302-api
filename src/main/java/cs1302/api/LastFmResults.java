package cs1302.api;

import java.util.List;

/**
 * Custom class for deserialization of LastFmResults.
 */
public class LastFmResults {
    private List<String> similarArtists;
    private List<String> similarSongs;

    /**
     * Getter for similar artists.
     *
     * @return List of Strings of similar artists
     */
    public List<String> getSimilarArtists() {
        return similarArtists;
    } // getSimilarArtists

    /**
     * Setter for similar artists.
     *
     * @param similarArtists similar artists to be set
     */
    public void setSimilarArtists(List<String> similarArtists) {
        this.similarArtists = similarArtists;
    } // setSimilarArtists

    /**
     * Getter for similar songs.
     *
     * @return List of Strings of similar songs
     */
    public List<String> getSimilarSongs() {
        return similarSongs;
    } // getSimilarSongs

    /**
     * Setter for similar songs.
     *
     * @param similarSongs similar songs to be set
     */
    public void setSimilarSongs(List<String> similarSongs) {
        this.similarSongs = similarSongs;
    } // setSimilarSongs
} // LastFmResults
