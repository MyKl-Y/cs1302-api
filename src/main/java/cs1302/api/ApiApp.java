package cs1302.api;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Modality;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Represents a Music Exploring App which uses Spotify, iTunes, and Last.FM APIs.
 */
public class ApiApp extends Application {

    /** HTTP Client. */
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build();

    /** Google {@code Gson} object for parsing JSON-formatted strings. */
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    // Spotify API credentials
    private static final String CLIENT_ID = "fd80f44f450c42baa188abc3acce0ff0";
    private static final String CLIENT_SECRET = "1846cb26e9e74279860b663ce36b7782";

    // Last.fm API key
    private static final String API_KEY = "df37cfd163b10a3358350c7aaac7dcfc";

    // UI elements
    private Stage stage;
    private Scene scene;
    private TextField searchField;
    private Button searchButton;
    private ListView<String> tracksListView;
    private HBox searchBox;
    private GridPane tracksPane;
    private BorderPane root;
    private ProgressBar progressBar;
    private Text instructions;
    private Text statusLabel;
    private VBox topVBox;
    private HBox statusBox;
    private HBox progressBox;
    private HBox instructionsBox;
    private Separator separator;
    private Separator separator1;
    private ImageView albumCover;
    private Text artistNameText;
    private Text durationText;
    private Text songName;
    private Text popularityText;
    private Text albumName;
    private ProgressBar durationBar;
    private VBox albumArtistBox;
    private HBox durationBox;
    private VBox infoBox;
    private VBox outerBox;
    private VBox similarTracksBox;
    private GridPane similarTracksGrid;
    private ImageView[] similarTracksImages;
    private Label[] similarTracksLabels;

    // Data structures
    private List<String> trackIds = new ArrayList<>();
    private SpotifyResponse selectedTrack;

    // API endpoints
    private static final String SPOTIFY_AUTH_URL = "https://accounts.spotify.com/api/token";
    private static final String SPOTIFY_SEARCH_URL = "https://api.spotify.com/v1/search";
    private static final String SPOTIFY_TRACK_URL = "https://api.spotify.com/v1/tracks/%s";
    private static final String LASTFM_ARTIST_URL =
        "https://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=%s&api_key=%s&format=json";

    // Authorization token for Spotify API
    private String authToken;

    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public ApiApp() {
        // UI setup
        searchField = new TextField("joji");
        searchButton = new Button("Search");
        tracksListView = new ListView<>();
        searchBox = new HBox(10, searchField, searchButton);
        tracksPane = new GridPane();
        root = new BorderPane();
        progressBar = new ProgressBar(0);
        instructions = new Text("Enter a song/artist name to get started:");
        statusLabel = new Text("");
        topVBox = new VBox(5);
        separator = new Separator(Orientation.HORIZONTAL);
        separator1 = new Separator(Orientation.HORIZONTAL);
        statusBox = new HBox(separator, statusLabel);
        progressBox = new HBox(progressBar);
        instructionsBox = new HBox(separator1, instructions);
        albumCover = new ImageView();
        artistNameText = new Text("");
        durationText = new Text("");
        songName = new Text("");
        popularityText = new Text("");
        albumName = new Text("");
        durationBar = new ProgressBar(0);
        albumArtistBox = new VBox(albumCover, artistNameText);
        durationBox = new HBox(durationBar, durationText);
        infoBox = new VBox(albumArtistBox, songName, albumName, popularityText, durationBox);
        outerBox = new VBox(infoBox);
        similarTracksImages = new ImageView[9];
        similarTracksLabels = new Label[9];
        similarTracksGrid = new GridPane();
        similarTracksBox = new VBox(similarTracksGrid);
        for (int i = 0; i < 9; i++) {
            similarTracksImages[i] = new ImageView();
            similarTracksImages[i].setFitWidth(100);
            similarTracksImages[i].setFitHeight(100);
            similarTracksLabels[i] = new Label();
            similarTracksLabels[i].setWrapText(true);
            similarTracksLabels[i].setPrefWidth(100);
            similarTracksLabels[i].setAlignment(Pos.CENTER);
        } // for
    } // ApiApp

    /** {@inheritDoc} */
    @Override
    public void init() {
        setButtonEventHandlers();
        // Layout setup
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 3; col++) {
                int index = col * 3 + (row / 2);
                if (row % 2 == 0 && index < similarTracksImages.length) {
                    similarTracksGrid.add(similarTracksImages[index], col, row);
                } else if (row % 2 != 0 && index < similarTracksLabels.length) {
                    similarTracksGrid.add(similarTracksLabels[index], col, row);
                } // if
            } // for
        } // for
        songName.setWrappingWidth(275);
        albumName.setWrappingWidth(275);
        similarTracksGrid.setVgap(10);
        similarTracksGrid.setHgap(10);
        similarTracksGrid.setAlignment(Pos.CENTER);
        albumCover.setFitWidth(275);
        albumCover.setFitHeight(275);
        albumArtistBox.setSpacing(10);
        albumArtistBox.setAlignment(Pos.CENTER_LEFT);
        durationBox.setSpacing(5);
        durationBox.setVisible(false);
        infoBox.setSpacing(10);
        infoBox.setPadding(new Insets(10));
        outerBox.setAlignment(Pos.CENTER);
        outerBox.setPadding(new Insets(20));
        similarTracksBox.setPadding(new Insets(20));
        durationBar.setPrefWidth(250);
        separator.setPrefWidth(5);
        separator1.setPrefWidth(5);
        separator.setOpacity(0);
        separator1.setOpacity(0);
        progressBar.setPrefWidth(930);
        searchField.setPrefWidth(855);
        tracksPane.setHgap(10);
        tracksPane.setPadding(new Insets(10));
        tracksPane.add(new Label("Tracks"), 0, 0);
        tracksPane.add(new Label("Track Info"), 1, 0);
        tracksPane.add(new Label("Similar Tracks"), 2, 0);
        tracksPane.add(tracksListView, 0, 1);
        tracksPane.add(outerBox, 1, 1);
        tracksPane.add(similarTracksBox, 2, 1);
        progressBox.setAlignment(Pos.CENTER);
        searchBox.setAlignment(Pos.CENTER);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        topVBox.getChildren()
            .addAll(instructionsBox, searchBox, progressBox, statusBox);
        outerBox.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        outerBox.setBackground(new Background(
            new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        similarTracksBox.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        similarTracksBox.setBackground(new Background(
            new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    } // init

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {

        this.stage = stage;

        // setup scene
        root.setTop(topVBox);
        root.setCenter(tracksPane);
        scene = new Scene(root);

        // setup stage
        stage.setMinWidth(950);
        stage.setMaxWidth(950);
        stage.setMaxHeight(720);
        stage.setTitle("Music Explorer");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();
        Platform.runLater(() -> this.stage.setResizable(false));

        // Get authorization token for Spotify API
        try {
            authToken = getAuthToken();
        } catch (IOException | InterruptedException e) {
            showErrorBox(e);
        } // try
    } // start

    /**
     * Makes a search request to the Spotify API using the given query string,
     * and updates the tracks list view with the search results.
     *
     * @param query the term to search for
     */
    private void searchTracks(String query) {
        // Encode query string for use in the URL
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        // Build the search URL
        URI searchURI = URI.create(SPOTIFY_SEARCH_URL + "?q=" + encodedQuery + "&type=track");
        try {
            // Build the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(searchURI)
                .header("Authorization", "Bearer " + authToken)
                .build();

            // Send the search request and parse the response JSON
            HttpResponse<String> response = HTTP_CLIENT
                .send(request, HttpResponse.BodyHandlers.ofString());

            // Deserialize the response into a SpotifyResponse object
            SpotifyResponse spotifyResponse = GSON.fromJson(response.body(), SpotifyResponse.class);
            List<SpotifyTrack> tracks = spotifyResponse.getTracks().getItems();

            Platform.runLater(() -> {
                // Clear the previous search results
                tracksListView.getSelectionModel().clearSelection();
                tracksListView.getItems()
                    .removeAll(new ArrayList<String>(tracksListView
                    .getSelectionModel().getSelectedItems()));
                tracksListView.getItems().clear();
                trackIds.clear();

                // Add the tracks to the list view and trackIds list
                for (SpotifyTrack track : tracks) {
                    tracksListView.getItems()
                        .add(track.getName() + " - " + track.getArtists().get(0).getName());
                    trackIds.add(track.getId());
                    Platform.runLater(() -> {
                        double progress = progressBar.getProgress() + 1.0 / tracks.size();
                        progressBar.setProgress(progress);
                    });
                } // for
            });
        } catch (IOException | InterruptedException | NullPointerException e) {
            showErrorBox(e);
        } // try
    } // searchTracks

    /**
     * Sets the event handlers for button presses.
     */
    public void setButtonEventHandlers() {
        // Set up the search button event handler
        searchButton.setOnAction(event -> {
            String query = searchField.getText();
            progressBar.setProgress(0);
            statusLabel.setText("Searching...");
            searchButton.setDisable(true);
            Platform.runLater(() -> {
                durationBox.setVisible(false);
                WritableImage tempBlank1 = new WritableImage(275, 275);
                Image temp1 = new ImageView(tempBlank1).getImage();
                albumCover.setImage(temp1);
                artistNameText.setText("");
                durationText.setText("");
                songName.setText("");
                popularityText.setText("");
                albumName.setText("");
                WritableImage tempBlank2 = new WritableImage(100, 100);
                Image temp2 = new ImageView(tempBlank2).getImage();
                for (int i = 0; i < 9; i++) {
                    similarTracksLabels[i].setText("");
                    similarTracksImages[i].setImage(temp2);
                } // for
            });
            new Thread(() -> {
                searchTracks(searchField.getText());
                Platform.runLater(() -> {
                    if (!query.isEmpty()) {
                        statusLabel.setText("Search completed successfully.");
                    } else {
                        statusLabel.setText("Error: Please enter a valid search query.");
                    } // if
                    searchButton.setDisable(false);
                });
            }).start();
        });

        // Set up the tracks list view event handler
        tracksListView.setOnMouseClicked(event -> {
            int selectedIndex = tracksListView.getSelectionModel().getSelectedIndex();
            tracksListView.setDisable(true);
            if (selectedIndex != -1) {
                new Thread(() -> {
                    selectTrack(selectedIndex);
                    tracksListView.setDisable(false);
                }).start();
            } // if
        });
    } // setButtonEventHandlers

    /**
     * Retrieve information for the selected track and display it in the Track Info area
     * using the Spotify API.
     *
     * @param index selected track's index in ListView object
     */
    private void selectTrack(int index) {
        try {
            // Get the track ID for the selected track
            String trackId = trackIds.get(index);

            // Build the track URL
            URI trackURI = URI.create(String.format(SPOTIFY_TRACK_URL, trackId));

            // Build the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(trackURI)
                .header("Authorization", "Bearer " + authToken)
                .build();

            // Send the request and get the response
            HttpResponse<String> response = HTTP_CLIENT
                .send(request, HttpResponse.BodyHandlers.ofString());

            // Deserialize the response into a SpotifyResponse object
            SpotifyTrack selectedTrack = GSON.fromJson(response.body(), SpotifyTrack.class);

            // Get the artist name for the selected track
            String artistName = selectedTrack.getArtists().get(0).getName();
            Platform.runLater(() -> {
                try {
                    durationBox.setVisible(true);
                    getSongCoverAndArtistPicture(selectedTrack.getName(),
                        selectedTrack.getArtists().get(0).getName(), albumCover);
                    artistNameText.setText("Artist: " + selectedTrack.getArtists()
                        .get(0).getName());
                    durationText.setText(getDurationString(selectedTrack.getDurationMs()));
                    songName.setText("Song: " + selectedTrack.getName());
                    popularityText.setText("Rating: "
                        + Integer.toString(selectedTrack.getPopularity()) + "%");
                    albumName.setText("Album: " + selectedTrack.getAlbum().getName());
                    getSimilarArtistsAndSongs(selectedTrack.getArtists().get(0).getName(),
                        selectedTrack.getName(), similarTracksImages, similarTracksLabels);
                } catch (IOException | InterruptedException e) {
                    showErrorBox(e);
                } // try
            });
        } catch (IOException | InterruptedException e) {
            showErrorBox(e);
        } // try
    } // selectTrack

    /**
     * Sends a request to the iTunes API with specified song name, artist name, and ImageView
     * object that will be updated to get the song cover for a given song by a given artist.
     *
     * @param songName name of song to be searched
     * @param artistName name of artist that the song is by
     * @param songCoverImageView ImageView object to be updated
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void getSongCoverAndArtistPicture(String songName,
        String artistName, ImageView songCoverImageView) throws IOException, InterruptedException {
        // Construct the URI for the iTunes API request
        String encodedSongName = URLEncoder.encode(songName, StandardCharsets.UTF_8);
        String encodedArtistName = URLEncoder.encode(artistName, StandardCharsets.UTF_8);
        String url = String
            .format("https://itunes.apple.com/search?term=%s+%s&media=music&entity=song&limit=1",
            encodedSongName, encodedArtistName);

        // Create a new HttpClient and HttpRequest for the API request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
                .GET()
                .build();

        // Send the API request and parse the response using Gson
        HttpResponse<String> response =
            HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        ItunesResponse itunesResponse = GSON.fromJson(response.body(), ItunesResponse.class);

        // Extract the first result from the API response and set the song cover and artist picture
        if (itunesResponse.getResults().length > 0) {
            ItunesResult results = itunesResponse.getResults()[0];
            String artworkUrl = results.getArtworkUrl100().replace("100x100", "275x275");
            songCoverImageView.setImage(new Image(artworkUrl));
        } // if
    } // getSongCoverAndArtistPicture

    /**
     * Sends a request to the Last.FM API with artist, song, and arrays for ImageViews and
     * Labels to update using the found similar tracks.
     *
     * @param artistName artist of the selected song
     * @param selectedSong song to search for similar tracks
     * @param similarTrackImageView array of similar tracks album covers
     * @param similarTrackLabel array of names of the similar tracks
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void getSimilarArtistsAndSongs(String artistName, String selectedSong,
        ImageView[] similarTrackImageView, Label[] similarTrackLabel)
        throws IOException, InterruptedException {
        String encodedSongName = URLEncoder.encode(selectedSong, StandardCharsets.UTF_8);
        String encodedArtistName = URLEncoder.encode(artistName, StandardCharsets.UTF_8);
        String url = "http://ws.audioscrobbler.com/2.0/?method=track.getsimilar&artist="
            + encodedArtistName + "&track=" + encodedSongName + "&api_key=" + API_KEY
            + "&format=json";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();
        HttpResponse<String> response =
            HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        LastfmResponse lastfmResponse = GSON.fromJson(responseBody, LastfmResponse.class);

        List<String> similarSongs = lastfmResponse.getSimilartracks().getTrack().stream()
            .map(LastfmResponse.Track::getName)
            .collect(Collectors.toList());
        LastFmResults results = new LastFmResults();
        results.setSimilarSongs(similarSongs);

        if (similarSongs.size() != 0 && similarSongs.size() >= 9) {
            for (int i = 0; i < 9; i++) {
                getSongCoverAndArtistPicture(similarSongs.get(i), "",
                    similarTrackImageView[i]);
                similarTrackLabel[i].setText(similarSongs.get(i));
            } // for
        } else {
            WritableImage temp1 = new WritableImage(100, 100);
            Image temp = new ImageView(temp1).getImage();
            for (int i = 0; i < 9; i++) {
                similarTrackLabel[i].setText("");
                similarTrackImageView[i].setImage(temp);
            } // for
            similarTrackLabel[0].setText("No");
            similarTrackLabel[3].setText("similar");
            similarTrackLabel[6].setText("track");
            similarTrackLabel[1].setText("was");
            similarTrackLabel[4].setText("found");
            similarTrackLabel[7].setText("for");
            similarTrackLabel[2].setText("this");
            similarTrackLabel[5].setText("track");
            similarTrackLabel[8].setText(":)");
        } // if
    } // getSimilarArtistsAndSongs

    /**
     * Convert a duration in milliseconds to a string in the format mm:ss.
     *
     * @param durationMs duration given in seconds
     * @return String of the duration given in format mm:ss
     */
    private String getDurationString(long durationMs) {
        long seconds = durationMs / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    } // getDurationString

    /**
     * Retrieve the authorization token for the Spotify API.
     *
     * @return String of the authentication token for the API
     * @throws IOException
     * @throws InterruptedException
     */
    private String getAuthToken() throws IOException, InterruptedException {
        // Build the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(SPOTIFY_AUTH_URL))
            .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((CLIENT_ID + ":"
                + CLIENT_SECRET).getBytes()))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
            .build();

        // Send the request and get the response
        HttpResponse<String> response = HTTP_CLIENT
            .send(request, HttpResponse.BodyHandlers.ofString());

        // Deserialize the response into a SpotifyToken object and return the access token
        SpotifyToken token = GSON.fromJson(response.body(), SpotifyToken.class);
        return token.getAccessToken();
    } // getAuthToken

    /**
     * Opens a small GUI for errors.
     *
     * @param exception pass in exception to put message in error box
     */
    public void showErrorBox(Throwable exception) {
        Platform.runLater(() -> {

            Stage errorStage = new Stage();
            errorStage.setTitle("Error");
            errorStage.initModality(Modality.APPLICATION_MODAL);

            VBox errorBox = new VBox();

            Button closeButton = new Button("OK");
            closeButton.setOnAction(event -> errorStage.close());

            Label headingLabel = new Label("Error");

            TextArea messageArea = new TextArea();
            messageArea.setText(exception.getMessage());
            messageArea.setEditable(false);
            messageArea.setWrapText(true);
            messageArea.setPrefWidth(300);

            GridPane contentPane = new GridPane();
            contentPane.setHgap(10);
            contentPane.setVgap(10);
            contentPane.setPadding(new Insets(10));
            contentPane.add(headingLabel, 0, 0);
            contentPane.add(messageArea, 0, 1);
            contentPane.add(closeButton, 0, 2);
            contentPane.setHalignment(closeButton, HPos.CENTER);

            errorBox.getChildren().addAll(contentPane);
            errorBox.setDisable(true);
            Scene errorScene = new Scene(errorBox, 300, 200);
            errorStage.setScene(errorScene);
            errorStage.setOnShown(event -> errorBox.setDisable(false));
            errorStage.showAndWait();
        });
    } // showErrorBox

} // ApiApp
