package cs1302.api;

/**
 * Custom class for desearialization of ItunesResult.
 */
public class ItunesResult {
    private String wrapperType;
    private String kind;
    private String artworkUrl100;
    private String artistName;
    private String collectionName;
    private String trackName;
    private String artistViewUrl;

    /**
     * Getter method for Artist Name.
     *
     * @return String of artist name
     */
    public String getArtistName() {
        return artistName;
    } // getArtistName

    /**
     * Setter method for Artist Name.
     *
     * @param artistName string of name
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    } // setArtistName

    /**
     * Getter method of Collection Name.
     *
     * @return String of collection name
     */
    public String getCollectionName() {
        return collectionName;
    } // getCollectionName

    /**
     * Setter method of Collection Name.
     *
     * @param collectionName string of name
     */
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    } // setCollectionName

    /**
     * Getter method of Track Name.
     *
     * @return String of track name
     */
    public String getTrackName() {
        return trackName;
    } // getTrackName

    /**
     * Setter method of Track Name.
     *
     * @param trackName String of name
     */
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    } // setTrackName

    /**
     * Getter method of Artwork URL 100x100.
     *
     * @return String of URL
     */
    public String getArtworkUrl100() {
        return artworkUrl100;
    } // getArtworkURL100

    /**
     * Setter method of Artwork URL 100x100.
     *
     * @param artworkUrl100 string of URL
     */
    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    } // setArtworkUrl100

    /**
     * Getter method of Artist Image URL.
     *
     * @return String of URL
     */
    public String getArtistViewUrl() {
        return artistViewUrl;
    } // getArtistViewUrl

    /**
     * Setter method of Artist Image URL.
     *
     * @param artistViewUrl string of URL
     */
    public void setArtistViewUrl(String artistViewUrl) {
        this.artistViewUrl = artistViewUrl;
    } // setArtistViewUrl

} // ItunesResult
