package cs1302.api;

import java.util.List;

/**
 * Custom class for deserialization of LastfmResponse.
 */
public class LastfmResponse {
    private SimilarArtists similarartists;
    private SimilarTracks similartracks;

    /**
     * Getter for similar artists.
     *
     * @return SimilarArtists artists info
     */
    public SimilarArtists getSimilarartists() {
        return similarartists;
    } // getSimilarartists

    /**
     * Setter for similar artists.
     *
     * @param similarartists artists to be set
     */
    public void setSimilarartists(SimilarArtists similarartists) {
        this.similarartists = similarartists;
    } // setSimilarartists

    /**
     * Getter for similar tracks.
     *
     * @return SimilarTracks tracks info
     */
    public SimilarTracks getSimilartracks() {
        return similartracks;
    } // getSimilartracks

    /**
     * Setter for similar tracks.
     *
     * @param similartracks tracks to be set
     */
    public void setSimilartracks(SimilarTracks similartracks) {
        this.similartracks = similartracks;
    } // setSimilartracks

    /**
     * Custom class for SimilarArtists object.
     */
    public static class SimilarArtists {
        private List<Artist> artist;

        /**
         * Getter for artists.
         *
         * @return List of Artists
         */
        public List<Artist> getArtist() {
            return artist;
        } // getArtist

        /**
         * Setter for artists.
         *
         * @param artist to be set
         */
        public void setArtist(List<Artist> artist) {
            this.artist = artist;
        } // setArtist
    } // SimilarArtists

    /**
     * Custom class for SimilarTracks object.
     */
    public static class SimilarTracks {
        private List<Track> track;

        /**
         * Getter for tracks.
         *
         * @return List of tracks
         */
        public List<Track> getTrack() {
            return track;
        } // getTrack

        /**
         * Setter for tracks.
         *
         * @param track to be set
         */
        public void setTrack(List<Track> track) {
            this.track = track;
        } // setTrack
    } // SimilarTracks

    /**
     * Custom class for artist object.
     */
    public static class Artist {
        private String name;

        /**
         * Getter for artist name.
         *
         * @return String of name
         */
        public String getName() {
            return name;
        } // getName

        /**
         * Setter for artist name.
         *
         * @param name String of artist name
         */
        public void setName(String name) {
            this.name = name;
        } // setName
    } // Artist

    /**
     * Custom class for track object.
     */
    public static class Track {
        private String name;

        /**
         * Getter for track name.
         *
         * @return String of track name
         */
        public String getName() {
            return name;
        } // getName

        /**
         * Setter for track name.
         *
         * @param name String of track name
         */
        public void setName(String name) {
            this.name = name;
        } // setName
    } // Track
} // LastfmResponse
