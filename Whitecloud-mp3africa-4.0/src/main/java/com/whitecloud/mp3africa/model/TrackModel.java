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

package com.whitecloud.mp3africa.model;


public class TrackModel {
    @com.google.gson.annotations.SerializedName("Artist")
    private String artist = null;
    @com.google.gson.annotations.SerializedName("SongTitle")
    private String songTitle = null;
    @com.google.gson.annotations.SerializedName("Duration")
    private Integer duration = null;
    @com.google.gson.annotations.SerializedName("SongUrl")
    private String songUrl = null;
    @com.google.gson.annotations.SerializedName("Album")
    private String album = null;
    @com.google.gson.annotations.SerializedName("IconUrl")
    private String iconUrl = null;
    @com.google.gson.annotations.SerializedName("Plays")
    private Integer plays = null;
    @com.google.gson.annotations.SerializedName("Genre")
    private String genre = null;
    @com.google.gson.annotations.SerializedName("TrackNumber")
    private Integer trackNumber = null;

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

    /**
     * Gets album
     *
     * @return album
     **/
    public String getAlbum() {
        return album;
    }

    /**
     * Sets the value of album.
     *
     * @param album the new value
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * Gets iconUrl
     *
     * @return iconUrl
     **/
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * Sets the value of iconUrl.
     *
     * @param iconUrl the new value
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * Gets plays
     *
     * @return plays
     **/
    public Integer getPlays() {
        return plays;
    }

    /**
     * Sets the value of plays.
     *
     * @param plays the new value
     */
    public void setPlays(Integer plays) {
        this.plays = plays;
    }

    /**
     * Gets genre
     *
     * @return genre
     **/
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the value of genre.
     *
     * @param genre the new value
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Gets trackNumber
     *
     * @return trackNumber
     **/
    public Integer getTrackNumber() {
        return trackNumber;
    }

    /**
     * Sets the value of trackNumber.
     *
     * @param trackNumber the new value
     */
    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

}
