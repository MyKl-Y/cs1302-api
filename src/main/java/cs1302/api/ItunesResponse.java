package cs1302.api;

/**
 * Custom class for deserializing the ItunesResponse.
 */
public class ItunesResponse {
    int resultCount;
    private ItunesResult[] results;

    /**
     * Getter method for the ItunesResults.
     *
     * @return ItunesResult array of the results
     */
    public ItunesResult[] getResults() {
        return results;
    } // getResults

    /**
     * Setter for the ItunesResults.
     *
     * @param results for ItunesResults array to be set
     */
    public void setResults(ItunesResult[] results) {
        this.results = results;
    } // setResults

} // ItunesResponse
