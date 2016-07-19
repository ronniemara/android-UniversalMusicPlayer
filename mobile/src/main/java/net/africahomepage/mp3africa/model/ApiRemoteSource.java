package net.africahomepage.mp3africa.model;

import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;

import com.whitecloud.mp3africa.model.GenreTrackListModel;
import com.whitecloud.mp3africa.model.GenreTrackListModelTracksItem;


import net.africahomepage.mp3africa.MusicService;
import net.africahomepage.mp3africa.utils.LogHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.String;

import java.lang.Exception;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.whitecloud.mp3africa.MPAfricaClient;

/**
 * Created by ron on 02/07/16.
 */
public class ApiRemoteSource implements MusicProviderSource {

    private GenreTrackListModel tracks;

    @Override
    public HashMap<String, String> getTracksUrls() {
        return mTracksUrls;
    }

    private HashMap<String, String> mTracksUrls = new HashMap<>();


    private static final String TAG = LogHelper.makeLogTag(ApiRemoteSource.class);
    @Override
    public Iterator<MediaMetadataCompat> iterator() {
        try {
             tracks = getTracksFromApi();
            ArrayList<MediaMetadataCompat> metadataList = new ArrayList<>();

            if (tracks != null) {
                for (int j = 0; j < tracks.getCount(); j++) {
                    GenreTrackListModelTracksItem item = tracks.getTracks().get(j);
                    metadataList.add(buildFromTrack(item));
                }
            }
            return metadataList.iterator();
        } catch (Exception e) {
            LogHelper.e(TAG, e, "Could not retrieve music list");
            throw new RuntimeException("Could not retrieve music list", e);
        }
    }


    private GenreTrackListModel getTracksFromApi() {
        final MPAfricaClient client = MusicService.initializeApi();

        GenreTrackListModel listOfTracks;

        try {
            listOfTracks = client.tracksGet();
            return listOfTracks;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private MediaMetadataCompat buildFromTrack(GenreTrackListModelTracksItem model) {
        String title = model.getSongTitle();
        String album = model.getAlbum();
        String artist = model.getArtist();
        String genre = model.getGenre();
        String iconUrl = model.getIconUrl();
        int trackNumber = model.getTrackNumber();
        int totalTrackCount = 10;
        int duration = model.getDuration() * 1000; // ms


        // Since we don't have a unique ID in the server, we fake one using the hashcode of
        // the music source. In a real world app, this could come from the server.
        String id = model.getArtist() + ", " +model.getSongTitle();

        //Keep music urls in separate map
        mTracksUrls.put(id, model.getSongUrl());


        /*
        STOP SHIP
        Adding the music source to the MediaMetadata (and consequently using it in the
        mediaSession.setMetadata) is not a good idea for a real world music app, because
        the session metadata can be accessed by notification listeners. This is done in this
        sample for convenience only.
        noinspection ResourceType
        */
        //noinspection WrongConstant
        return new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, id)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, duration)
                .putString(MediaMetadataCompat.METADATA_KEY_GENRE, genre)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, iconUrl)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                .putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, trackNumber)
                .putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, totalTrackCount)
                .build();
    }
}
