# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice looking HTML.

## Part 1: App Description

> Please provide a firendly description of your app, including the
> the primary functions available to users of the app. Be sure to
> describe exactly what APIs you are using and how they are connected
> in a meaningful way.

> **Also, include the GitHub `https` URL to your repository.**

My app uses the Spotify, Last.FM, and iTunes APIs to create a visually
appealing Music Explorer which searches a given query given from the user's
input which is either a song or an artist and passes it through the Spotify
API to get a list of tracks related to the query. The user can click on any
of the tracks listed and it will pass the song name and artist name given
from the Spotify Response and passes it through the iTunes API to displace
the song's album cover as well as prints the Song's name, album, artist,
popularity, and duration given by the original Spotify response. The app also
takes the song name and artist name from the Spotify response of the selected
track and sends it through the Last.FM API to display a 3x3 grid of similar
tracks related to the original selected track.

https://github.com/myy04663/cs1302-omega

## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?

I learned how to use github and git a lot more cleanly and I managed
to learn how to create my own custom classes that were used to
deserialize the response and requests from the Json formatted
information from the APIs.

## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?

I would definetely read the API documentations fully since I confused myself
when I created my own custom classes to deserialize the requests and responses
from the APIs.
