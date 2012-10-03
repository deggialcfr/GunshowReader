package org.android.deggial.gunshowreader;

import java.util.regex.Pattern;

import net.bytten.comicviewer.IComicDefinition;
import net.bytten.comicviewer.IComicProvider;

import android.net.Uri;

public class GunshowComicDefinition implements IComicDefinition {

    static public final Pattern
        gunshowHomePattern = Pattern.compile(
            "http://(www\\.)?gunshowcomic\\.com(/)?"),
        comicUrlPattern = Pattern.compile(
            "http://(www\\.)?gunshowcomic\\.com//([0-9]+)(/)?"),
        archiveUrlPattern = Pattern.compile(
            "http://(www\\.)?gunshowcomic\\.com/archive.php(/)?");

    private GunshowComicProvider provider;
    
    public GunshowComicDefinition() {
        provider = new GunshowComicProvider(this);
    }
    
    public Uri getArchiveUrl() {
        return Uri.parse("http://gunshowcomic.com/archive.php");
    }

    public String getAuthorLinkText() {
        return "Gunshow Comic official website";
    }

    public Uri getAuthorLinkUrl() {
        return Uri.parse("http://gunshowcomic.com");
    }

    public String getAuthorName() {
        return "KC Green";
    }

    public String getComicTitle() {
        return "Gunshow";
    }

    public String getComicTitleAbbrev() {
        return "Gunshow";
    }

    public String getPackageName() {
        return "org.android.deggial.gunshowreader";
    }

    public boolean hasAltText() {
        return true;
    }

    public boolean idsAreNumbers() {
        return true;
    }

    public boolean isArchiveUrl(Uri url) {
        return archiveUrlPattern.matcher(url.toString()).matches();
    }
    
    public boolean isComicUrl(Uri url) {
        return comicUrlPattern.matcher(url.toString()).matches();
    }

    public boolean isHomeUrl(Uri url) {
        return gunshowHomePattern.matcher(url.toString()).matches();
    }

    public IComicProvider getProvider() {
        return provider;
    }

    public Uri getDevDonateUrl() {
        return Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=ZR6D9T8MH8G62");
    }
    
    public Uri getAuthorDonateUrl() {
        return Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=QJ4QC3DK3UQVS");
    }


}
