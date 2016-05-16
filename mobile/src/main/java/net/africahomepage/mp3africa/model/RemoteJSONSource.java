/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.africahomepage.mp3africa.model;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.regions.Regions;
import com.whitecloud.mp3africasdk.MPAfricaClient;
import com.whitecloud.mp3africasdk.model.TrackModel;

import net.africahomepage.mp3africa.AWSConfiguration;
import net.africahomepage.mp3africa.utils.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Utility class to get a list of MusicTrack's based on a server-side JSON
 * configuration.
 */
public class RemoteJSONSource implements MusicProviderSource {

    private static final String TAG = LogHelper.makeLogTag(RemoteJSONSource.class);


    @Override
    public Iterator<MediaMetadataCompat> iterator(Context context) {
        try {
            TrackModel model = getTracksFromApi(context);
            ArrayList<MediaMetadataCompat> tracks = new ArrayList<>();

            if (model != null) {
               // for (int j = 0; j < jsonTracks.length(); j++) {
                for (int i = 0; i < 1; i++) {
                tracks.add(buildFromTrack(model));
                 }
            }
            return tracks.iterator();
        } catch (Exception e) {
            LogHelper.e(TAG, e, "Could not retrieve music list");
            throw new RuntimeException("Could not retrieve music list", e);
        }
    }

    private TrackModel getTracksFromApi(Context context) {

        final AWSCredentialsProvider credentialsProvider = initializeApi(context);


                ApiClientFactory factory = new ApiClientFactory().credentialsProvider(credentialsProvider);
                // create a client
                final MPAfricaClient client = factory.build(MPAfricaClient.class);

                TrackModel model = null;
                try {
                    model = client.trackGet();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }

                return model;
            }




    private AWSCredentialsProvider initializeApi(Context   context) {
        // Use CognitoCachingCredentialsProvider to provide AWS credentials
        // for the ApiClientFactory

        return new CognitoCachingCredentialsProvider(
                context ,          // activity context
                AWSConfiguration.IDENTITY_ID, // Cognito identity pool id
                Regions.US_EAST_1 // region of Cognito identity pool
        );
    }

    private MediaMetadataCompat buildFromTrack(TrackModel model) {
        String title = model.getSongTitle();
        String album = model.getAlbum();
        String artist = model.getArtist();
        String genre = model.getGenre();
       String source = model.getSongUrl();
        String iconUrl = model.getIconUrl();
        int trackNumber = model.getTrackNumber();
        int totalTrackCount = 10;
        int duration = model.getDuration() * 1000; // ms


        // Since we don't have a unique ID in the server, we fake one using the hashcode of
        // the music source. In a real world app, this could come from the server.
        String id = model.getArtist() + ", " +model.getSongTitle();

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
                .putString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE, source)
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
