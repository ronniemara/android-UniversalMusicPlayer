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

package net.africahomepage.mp3africa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.Toast;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobile.AWSConfiguration;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.content.ContentItem;
import com.amazonaws.mobile.content.ContentManager;
import com.amazonaws.mobile.content.ContentProgressListener;
import com.amazonaws.mobile.user.IdentityManager;
import com.amazonaws.services.s3.internal.ServiceUtils;
import com.bumptech.glide.Glide;

import net.africahomepage.mp3africa.utils.BitmapHelper;
import net.africahomepage.mp3africa.utils.LogHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Security;
import java.util.concurrent.ExecutionException;

import android.content.Context;

/**
 * Implements a basic cache of album arts, with async loading support.
 */
public final class AlbumArtCache {
    private static final String TAG = LogHelper.makeLogTag(AlbumArtCache.class);

    private static final int MAX_ALBUM_ART_CACHE_SIZE = 12 * 1024 * 1024;  // 12 MB
    private static final int MAX_ART_WIDTH = 800;  // pixels
    private static final int MAX_ART_HEIGHT = 480;  // pixels

    // Resolution reasonable for carrying around as an icon (generally in
    // MediaDescription.getIconBitmap). This should not be bigger than necessary, because
    // the MediaDescription object should be lightweight. If you set it too high and try to
    // serialize the MediaDescription, you may get FAILED BINDER TRANSACTION errors.
    private static final int MAX_ART_WIDTH_ICON = 128;  // pixels
    private static final int MAX_ART_HEIGHT_ICON = 128;  // pixels

    private static final int BIG_BITMAP_INDEX = 0;
    private static final int ICON_BITMAP_INDEX = 1;


    private final LruCache<String, Bitmap[]> mCache;

    private Bitmap[] bitmaps;

    private static final AlbumArtCache sInstance = new AlbumArtCache();

    public static AlbumArtCache getInstance() {
        return sInstance;
    }

    private AlbumArtCache() {
        // Holds no more than MAX_ALBUM_ART_CACHE_SIZE bytes, bounded by maxmemory/4 and
        // Integer.MAX_VALUE:
        int maxSize = Math.min(MAX_ALBUM_ART_CACHE_SIZE,
                (int) (Math.min(Integer.MAX_VALUE, Runtime.getRuntime().maxMemory() / 4)));
        mCache = new LruCache<String, Bitmap[]>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap[] value) {
                return value[BIG_BITMAP_INDEX].getByteCount()
                        + value[ICON_BITMAP_INDEX].getByteCount();
            }
        };
    }

    public Bitmap getBigImage(String artUrl) {
        Bitmap[] result = mCache.get(artUrl);
        return result == null ? null : result[BIG_BITMAP_INDEX];
    }

    public Bitmap getIconImage(String artUrl) {
        Bitmap[] result = mCache.get(artUrl);
        return result == null ? null : result[ICON_BITMAP_INDEX];
    }

    public void fetch(final String artUrl, final FetchListener listener) {
        // WARNING: for the sake of simplicity, simultaneous multi-thread fetch requests
        // are not handled properly: they may cause redundant costly operations, like HTTP
        // requests and bitmap rescales. For production-level apps, we recommend you use
        // a proper image loading library, like Glide.
        Bitmap[] bitmap = mCache.get(artUrl);
        if (bitmap != null) {
            LogHelper.d(TAG, "getOrFetch: album art is in cache, using it", artUrl);
            listener.onFetched(artUrl, bitmap[BIG_BITMAP_INDEX], bitmap[ICON_BITMAP_INDEX]);
            return;
        }
        LogHelper.d(TAG, "getOrFetch: starting asynctask to fetch ", artUrl);

        final String fullUrl = "https://doyvfpldm7c3l.cloudfront.net/Thumbnails/" + artUrl + "?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiaHR0cHM6Ly9kb3l2ZnBsZG03YzNsLmNsb3VkZnJvbnQubmV0LyoiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE2MjY3ODEzODB9fX1dfQ__&Signature=aE74KqPsIXsumdhU7ss~bUPajxiCjoGFmUcCIHwgXFe8k6HYV5fTaz1CBL6P1LsO3zPdyv9exF70xafY5DB13GwFepICW8jfPFQunpV8fp-RRvG74Y5Tn5mnPhLDnS-pLv1dPwH56aNB-rIe4Y8qWNZ3~XP204Kn1Np7dMJb-DZ5axc8fL3ypNp6IN1Xsu8l~pT4QGvzzJKFcqq4IXhF5uPFIuy5VlWQjhZQcO85mKqPhNEE62RklwncCigm7EZ3ScFY88Dj3QzXbt1e3kH7mQ3qC4vOES52EwZmnQz2p0vVCFuET5VgTICpvBOhap4oflmelIJc1iIwvQ7Q5EU8BKRn3-L2vimACHJE8RyZDTLYFvM3lhxOoavMvmvy3GwLHNyNJzRlgUCmi7eU9v1e~Rck2U-hIrMeofFacu5ScGl6EZPYhCARY5MS6K641E9HSjJpgFZGh12fBHbCogDwCgJU0c0dDeduaSK7AKlxG4Qc4GPha4ECLuSzdmk7br6I~R9m-mL4jh60rNRyMXj4AD7sw8X9LMWr1Ague-tuosh6i2X7zEy5qfuN5Wf~JF-VfYl~YrNQDV64qBaHRrpGQjOZ6VTiQ1jFKrETNTj5r8uS4AXKH2fbgTrkQNV4C9yYS73nVVx2wSKGxkORHS-o5tiAqVkvusrB36Ds99cJ3Ac_&Key-Pair-Id=APKAIEQ2R5LPDJ4KUKYQ";

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap task = null;
                try {
                    task = Glide.with(MusicService.getInstance().getApplicationContext())
                            .load(fullUrl)
                            .asBitmap()
                            .into(AlbumArtCache.MAX_ART_WIDTH, AlbumArtCache.MAX_ART_HEIGHT)
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                return task;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {

                Bitmap icon = BitmapHelper.scaleBitmap(bitmap,
                        MAX_ART_WIDTH_ICON, MAX_ART_HEIGHT_ICON);
                bitmaps = new Bitmap[]{bitmap, icon};
                mCache.put(artUrl, bitmaps);
                LogHelper.d(TAG, "doInBackground: putting bitmap in cache. cache size=" +
                        mCache.size());

                if (bitmaps == null) {
                    listener.onError(artUrl, new IllegalArgumentException("got null bitmaps"));
                } else {
                    listener.onFetched(artUrl,
                            bitmaps[BIG_BITMAP_INDEX], bitmaps[ICON_BITMAP_INDEX]);
                }
            }
        }.execute();


    }


    public static abstract class FetchListener {
        public abstract void onFetched(String artUrl, Bitmap bigImage, Bitmap iconImage);

        public void onError(String artUrl, Exception e) {
            LogHelper.e(TAG, e, "AlbumArtFetchListener: error while downloading " + artUrl);
        }
    }
}
