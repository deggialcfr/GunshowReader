package org.android.deggial.gunshowreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bytten.comicviewer.ArchiveData.ArchiveItem;
import net.bytten.comicviewer.IComicInfo;
import net.bytten.comicviewer.IComicProvider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.net.Uri;
import android.util.Log;

public class GunshowComicProvider implements IComicProvider {

//	private static final Pattern archiveItemPattern = Pattern.compile(
//			// group(1): comic number;
//			"href=\"http://www.gunshowcomic.com/(.+?)\"");

	private static final String ARCHIVE_URL = "http://gunshowcomic.com/archive.php";
	private static final String COMIC_URL ="http://www.gunshowcomic.com/";
	private GunshowComicDefinition def;

	private int topId;
	private String currentId;
	public GunshowComicProvider(GunshowComicDefinition def) {
		this.def = def;
	}

	public Uri comicDataUrlForUrl(Uri url) {
		return null;
	}

	public Uri createComicUrl(String comicId) {
		currentId=comicId;
		return Uri.parse(COMIC_URL+comicId);  
	}


	private static String imageFromUrl(Document doc) {

		Elements posts = doc.select(".strip");
		Log.d("gunshow","posts size "+posts.size());
		// Log.d("gunshow","posts string"+posts.toString());
		// Log.d("gunshow","posts src "+posts.get(0).attr("src").toString());
		return posts.get(0).attr("src");        
	}

	public IComicInfo fetchComicInfo(Uri url) throws Exception {

		Document doc = Jsoup.connect(url.toString()).timeout(30 * 1000).get();
		String img=imageFromUrl(doc);
		GunshowComicInfo data = new GunshowComicInfo();
		data.img=Uri.parse(img);
		data.num = Integer.parseInt(currentId);
		//data.title = currentId;
		data.title = gunshowTitleExtractor(doc.getElementsByTag("title").toString());
		Log.d("title", data.title.toString());
		data.alt = doc.getElementsByClass("strip").attr("title").toString();
		//Log.d("jsoup", doc.toString());
		return data;
	}
	
	public String gunshowTitleExtractor(String st){
		String result = null, r2 = "Title";
		result = st.substring(17);
		
		int i=0;
		while(!result.substring(i,i+1).contains("<")){
			if(r2.equals("Title"))
				r2=result.substring(i,i+1);
			else
				r2+=result.substring(i,i+1);
			i++;
		}
		
		return r2;
	}
	

	public Uri fetchRandomComicUrl() throws Exception {

		Integer ranInteger= (int)(Math.random()*topId);
		return createComicUrl(ranInteger+"");

	}

	public Uri getFinalComicUrl() {
		topId = 300;
		try {
			InputStream input = new URL("http://www.google.com/reader/api/0/stream/contents/feed/http://www.rsspect.com/rss/gunshowcomic.xml").openStream();
			final Pattern pattern = Pattern.compile("\"href\":\"http://gunshowcomic.com/(.+?)\",\"type");
			String theString = convertStreamToString(input);
			final Matcher matcher = pattern.matcher(theString);
			matcher.find();
			topId = Integer.parseInt(matcher.group(1));
		} catch (IOException e) {
			e.printStackTrace();
			Log.d("error getfinalcomic", e.toString());
		}

		return createComicUrl(topId+"");
	}

	public String getFirstId() {
		return "1";
	}

	public IComicInfo createEmptyComicInfo() {
		return new GunshowComicInfo();
	}

	public List<ArchiveItem> fetchArchive() throws Exception {
		List<ArchiveItem> archiveItems = new ArrayList<ArchiveItem>();
		URL url = new URL(ARCHIVE_URL);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		//Log.d("archiveItemPattern",archiveItemPattern.toString());
		Document doc = Jsoup.connect(url.toString()).timeout(30 * 1000).get();

		//        Log.d("doc child",doc.getElementById("tresult").toString());
		//Log.d("doc child",doc.getElementsByAttribute("href"));
		String line2;
		Object[] doc2 = doc.getElementsByAttribute("href").toArray();
		for(int i = 0; i<doc2.length;i++){
			if(doc2[i].toString().contains("<a href=\"http://gunshowcomic.com/")){	
				ArchiveItem item = new ArchiveItem();
				item.comicId = gunshowIdExtractor(doc2[i].toString());
				item.title = item.comicId + " - " + doc2[i].toString().substring(33+item.comicId.length()+2, doc2[i].toString().length()-4);
				if(!item.comicId.equals("id"))
					archiveItems.add(item);
			}

		}

		return archiveItems;
	}

	public String gunshowIdExtractor(String st){
		String result = null,r2 = "id";
		result = st.substring(33);
		int i=0;
		while(!result.substring(i,i+1).contains("\"")){
			if(Pattern.matches("^\\d*$", result.substring(i,i+1)))
				if(r2.equals("id"))
					r2=result.substring(i,i+1);
				else
					r2+=result.substring(i,i+1);
			i++;
		}
		//Log.d("string r2",r2);

		//return (r2.equals("id")?"1":r2);
		return r2;
	}


	public String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {        
			return "";
		}
	}
}
