package org.android.deggial.gunshowreader;

import net.bytten.comicviewer.IComicInfo;
import android.net.Uri;

public class GunshowComicInfo implements IComicInfo {

    public Uri img;
    public int num;
    public String title = "", alt = "";
    public boolean bookmarked;

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return Integer.toString(num);
    }

    public Uri getImage() {
        return img;
    }

    public String getNextId() {
        int n = num + 1;

        return Integer.toString(n);
    }

    public String getPrevId() {
        int n = num - 1;

        return Integer.toString(n);
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return "http://gunshowcomic.com/"+getId()+"/";
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean b) {
        bookmarked = b;
    }

}
