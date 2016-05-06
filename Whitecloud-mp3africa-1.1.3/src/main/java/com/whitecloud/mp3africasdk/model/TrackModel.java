/*
 * Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.whitecloud.mp3africasdk.model;


public class TrackModel {
    @com.google.gson.annotations.SerializedName("Artist")
    private String artist = null;
    @com.google.gson.annotations.SerializedName("SongTitle")
    private String songTitle = null;
    @com.google.gson.annotations.SerializedName("Duration")
    private Integer duration = null;
    @com.google.gson.annotations.SerializedName("SongUrl")
    private String songUrl = null;

    /**
     * Gets artist
     *
     * @return artist
     **/
    public String getArtist() {
        return artist;
    }

    /**
     * Sets the value of artist.
     *
     * @param artist the new value
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Gets songTitle
     *
     * @return songTitle
     **/
    public String getSongTitle() {
        return songTitle;
    }

    /**
     * Sets the value of songTitle.
     *
     * @param songTitle the new value
     */
    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    /**
     * Gets duration
     *
     * @return duration
     **/
    public Integer getDuration() {
        return duration;
    }

    /**
     * Sets the value of duration.
     *
     * @param duration the new value
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Gets songUrl
     *
     * @return songUrl
     **/
    public String getSongUrl() {
        return songUrl;
    }

    /**
     * Sets the value of songUrl.
     *
     * @param songUrl the new value
     */
    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

}
