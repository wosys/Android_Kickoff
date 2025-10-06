/*
 * Copyright 2023-2025 wintmain
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wintmain.wBasis.transition.util;

/**
 * Represents an Item in our application. Each item has a name, id, full size image url and
 * thumbnail url.
 */
public class SceneTransitionBasicItem {

    private static final String LARGE_BASE_URL = "https://picsum.photos/600/400?image=";
    private static final String THUMB_BASE_URL = "https://picsum.photos/100/100?image=";

    public static SceneTransitionBasicItem[] SceneTransitionBasicITEMS = new SceneTransitionBasicItem[]{
            new SceneTransitionBasicItem("Flying in the Light", "Romain Guy", "1"),
            new SceneTransitionBasicItem("Caterpillar", "Romain Guy", "10"),
            new SceneTransitionBasicItem("Look Me in the Eye", "Romain Guy", "100"),
            new SceneTransitionBasicItem("Flamingo", "Romain Guy", "1000"),
            new SceneTransitionBasicItem("Rainbow", "Romain Guy", "1001"),
            new SceneTransitionBasicItem("Over there", "Romain Guy", "1002"),
            new SceneTransitionBasicItem("Jelly Fish 2", "Romain Guy", "1003"),
            new SceneTransitionBasicItem("Lone Pine Sunset", "Romain Guy", "1004"),
    };
    private final String mName;
    private final String mAuthor;
    private final String mFileName;
    SceneTransitionBasicItem(String name, String author, String fileName) {
        mName = name;
        mAuthor = author;
        mFileName = fileName;
    }

    public static SceneTransitionBasicItem getItem(int id) {
        for (SceneTransitionBasicItem sceneTransitionBasicItem : SceneTransitionBasicITEMS) {
            if (sceneTransitionBasicItem.getId() == id) {
                return sceneTransitionBasicItem;
            }
        }
        return null;
    }

    public int getId() {
        return mName.hashCode() + mFileName.hashCode();
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getName() {
        return mName;
    }

    public String getPhotoUrl() {
        return LARGE_BASE_URL + mFileName;
    }

    public String getThumbnailUrl() {
        return THUMB_BASE_URL + mFileName;
    }

}
