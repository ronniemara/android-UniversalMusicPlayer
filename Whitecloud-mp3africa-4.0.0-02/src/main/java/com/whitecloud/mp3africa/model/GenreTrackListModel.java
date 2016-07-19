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

import java.util.*;
import com.whitecloud.mp3africa.model.GenreTrackListModelTracksItem;

public class GenreTrackListModel {
    @com.google.gson.annotations.SerializedName("count")
    private Integer count = null;
    @com.google.gson.annotations.SerializedName("tracks")
    private List<GenreTrackListModelTracksItem> tracks = new ArrayList<GenreTrackListModelTracksItem>() ;

    /**
     * Gets count
     *
     * @return count
     **/
    public Integer getCount() {
        return count;
    }

    /**
     * Sets the value of count.
     *
     * @param count the new value
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * Gets tracks
     *
     * @return tracks
     **/
    public List<GenreTrackListModelTracksItem> getTracks() {
        return tracks;
    }

    /**
     * Sets the value of tracks.
     *
     * @param tracks the new value
     */
    public void setTracks(List<GenreTrackListModelTracksItem> tracks) {
        this.tracks = tracks;
    }

}
