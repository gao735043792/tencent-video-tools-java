package com.huas.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.huas.bean.Video;
import com.huas.enumeration.Constants;

/**
 *@author GaoFeng
 *@date 2016-7-8下午02:45:41
 *@todo 爬取视频获取视频相关信息
 */
public class VideoUtil{
  
	public static Video getVideoInfo(String url) throws Exception{
    
	Video video = null;
    if (url.indexOf(Constants.youku.getUrl()) != -1) {
      try {
        video = getYouKuVideo(url);
      } catch (Exception e) {
    	  e.printStackTrace();
        video = null;
      }
    } 
    else if (url.indexOf(Constants.tudou.getUrl()) != -1) {
      try {
        video = getTudouVideo(url);
      } catch (Exception e) {
    	e.printStackTrace();
        video = null;
      }
    }
    else if (url.indexOf(Constants.qq.getUrl()) != -1) {
        try {
          video = getQQVideo(url);
        } catch (Exception e) {
        	e.printStackTrace();
          video = null;
        }
      } 
    else if (url.indexOf(Constants.iqiyi.getUrl()) != -1) {
        try {
          video = getAiqiyiVideo(url);
        } catch (Exception e) {
          e.printStackTrace();
          video = null;
        }
      }
    else if (url.indexOf(Constants.ku6.getUrl()) != -1) {
      try {
        video = getKu6Video(url);
      } catch (Exception e) {
    	e.printStackTrace();
        video = null;
      }
    }
    else if (url.indexOf(Constants.letv.getUrl()) != -1) {
      try {
        video = getLetvVideo(url);
      } catch (Exception e) {
    	e.printStackTrace();    	  
        video = null;
      }
    } 
    else if (url.indexOf(Constants.sohu.getUrl()) != -1) {
      try {
        video = getSohuVideo(url);
      } catch (Exception e) {
    	e.printStackTrace();
        video = null;
      }
    } 
    else if (url.indexOf(Constants.ifeng.getUrl()) != -1) {
      try {
        video = getIfengVideo(url);
      } catch (Exception e) {
    	e.printStackTrace();
        video = null;
      }
    } 
    else if (url.indexOf(Constants.yinyuetai.getUrl()) != -1) {
      try {
        video = getYinYueTaiVideo(url);
      } catch (Exception e) {
	   	e.printStackTrace();
        video = null;
      }
    } 
    else if (url.indexOf(Constants.sina.getUrl()) != -1) {
      try {
        video = getSinaVideo(url);
      } catch (Exception e) {
	   	e.printStackTrace();
        video = null;
      }
    } 
    else {
      Document doc = getURLContent(url);
      video = new Video();
      video.setTitle(doc.title());
      video.setPageUrl(url);
    }
    return video;
  }

  /**
 * @author GaoFeng 
 * @date 2016-7-8下午02:47:41
 * @desc 优酷
 */	
public static Video getYouKuVideo(String url) throws Exception {

	/** 情况一**/
	//解决手机端链接   http://c-h5.youku.com/co_show/h5/id_27265660?f=27265660&from=y9.3-idx-uhome-1519-20887.205916.1-1&x=#vid=XMTY2ODQ1Njk4MA==
	//解析出视频vid
	//转换成电脑端链接 http://v.youku.com/v_show/id_XMTY2ODQ1Njk4MA==.html
	
	/** 情况二**/
	//解析直接视频链接  http://m.youku.com/video/id_XNjUwMDUzMDcy.html?from=s7.8-1.2&x=
	
	
	if(url.contains("c-h5.youku.com")){
		String vid = url.substring(url.indexOf("vid=") + 4);
		url = "http://v.youku.com/v_show/id_" + vid + ".html";
	}
	else if(url.contains("youku.com/video")){
		String vid = url.substring(url.indexOf("id_") + 3,url.indexOf(".html"));
		url = "http://v.youku.com/v_show/id_" + vid + ".html";
	}
	Document doc = getURLContent(url);
    String title = doc.select("meta[name=irTitle]").attr("content");
    if(title == null || "".equals(title)){
    	title = doc.title();
    }
    String thumbnail = getElementAttrById(doc, "s_baidu1", "href");
    if(thumbnail != null && !"".equals(thumbnail)){
    	thumbnail = thumbnail.substring(thumbnail.indexOf("pic=") + 4);
    }
    String commonCode = getElementAttrById(doc, "link4", "value");
    String summary = doc.select("meta[name=description]").attr("content");
    String address = "";
    if(commonCode != null && !"".equals(commonCode)){
    	if(commonCode.indexOf("\"") != -1){
    		address = commonCode.substring(commonCode.indexOf("\"") + 1, commonCode.lastIndexOf("\""));
    	}else{
    		address = commonCode.substring(commonCode.indexOf("src='") + 5, commonCode.lastIndexOf("' frameborder="));
    	}
    	if(commonCode.indexOf("'allowfullscreen'") != -1){
    		commonCode = commonCode.replace("'allowfullscreen'", "");
    	}
    	if(commonCode.indexOf("'") != -1){
    		commonCode = commonCode.replace("'", "");
    	}
    }
    Video video = new Video();
    video.setTitle(title);
    video.setThumbnail(thumbnail);
    video.setSource(Constants.youku);
    video.setPageUrl(url);
    video.setAddress(address);
    video.setSummary(summary);
    video.setCommonCode(commonCode);
    return video;
  }

  /**
 * @author GaoFeng 
 * @date 2016-7-8下午02:47:56
 * @desc 土豆
 */	
public static Video getTudouVideo(String url) throws Exception {
	
    Document doc = getURLContent(url);
    
    String content = doc.html();
    int beginLocal = content.indexOf("var pageConfig = {");
    int endLocal = content.indexOf("};");
    content = content.substring(beginLocal, endLocal).trim();

    String title = doc.select("meta[name=irTitle]").attr("content");
    if(title == null || "".equals(title)){
    	title = doc.title();
    }

    String lcode = getScriptVarByName("lcode", content);
    String icode = getScriptVarByName("icode", content);
    
    String address = "http://www.tudou.com/programs/view/html5embed.action?code=" + icode + "&lcode=" + lcode;
    String thumbnail = getScriptVarByName(",pic", content);
    String summary = doc.select("meta[name=description]").attr("content");
    String time = getScriptVarByName("time", content);

    Video video = new Video();
    video.setTitle(title);
    video.setThumbnail(thumbnail);
    video.setTime(time);
    video.setSource(Constants.tudou);
    video.setPageUrl(url);
    video.setSummary(summary);
    video.setAddress(address);
    video.setCommonCode(getCommonCode(address));
    return video;
  }

  /**
 * @author GaoFeng 
 * @date 2016-7-8下午02:48:04
 * @desc 腾讯
 */	
public static Video getQQVideo(String url) throws Exception {

	String thumbnail = null;
	//适配移动端链接
    String coverid = null;
    String vid = null;
    //提取coverid正则表达式
	String cover_regExp = "[a-z0-9]{15}";
	//提取vid正则表达式
	String vid_regExp = "[^a-z0-9][a-z0-9]{11}([^a-z0-9]|$)";
	// 创建 Pattern 对象
    Pattern cover_r = Pattern.compile(cover_regExp);
    // 现在创建 matcher 对象
    Matcher cover_m = cover_r.matcher(url);
    if(cover_m.find()){
    	coverid = cover_m.group(0);
    }
	// 创建 Pattern 对象
    Pattern vid_r = Pattern.compile(vid_regExp);
    // 现在创建 matcher 对象
    Matcher vid_m = vid_r.matcher(url);
    if(vid_m.find()){
    	vid = vid_m.group(0);
    	vid = vid.substring(1, 12);
    }
    
	String m_url = "http://v.qq.com/x/cover/";
	String n_url = "https://v.qq.com/x/page/";
	if(coverid != null && vid != null){
		url = m_url + coverid +"/" + vid + ".html";
	}else{
		if(vid != null && coverid == null){
			url = n_url + vid + ".html";
		}else if(vid == null && coverid != null){
			url = m_url + coverid + ".html";
		}
	}
    Document doc = getURLContent(url);
    String title = doc.select("meta[itemprop=name]").attr("content");
    if(title == null || "".equals(title) || title.contains("undefined")){
    	title = doc.title();
    }

    thumbnail = doc.select("meta[itemprop=thumbnailUrl]").attr("content");
    String source = doc.select("link[rel=canonical]").attr("href");
    if(vid == null && (source != null && !"".equals(source))){
    	// 创建 Pattern 对象
        vid_r = Pattern.compile(vid_regExp);
        // 现在创建 matcher 对象
        vid_m = vid_r.matcher(source);
        if(vid_m.find()){
        	vid = vid_m.group(0);
        	vid = vid.substring(1, 12);
        }
    }
    
    String playerUrl = "https://v.qq.com/iframe/player.html?vid=" + vid;
    
    if(source == null || "".equals(source) ||(source != null && source.contains("undefined"))){
    	if(url.contains("=")){
    		vid = url.substring(url.indexOf("=") + 1);
    	}else{
    		vid = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".html"));
    	}
    }
    
    String summary = doc.select("meta[name=description]").attr("content");
    if(summary != null && summary.contains("undefined")){
    	summary = null;
    }

    Video video = new Video();
    if(title.contains("_腾讯视频")){
		title = title.replaceAll("_腾讯视频", "");
	}
    video.setTitle(title);
    if(thumbnail != null && thumbnail.length() > 10 && !thumbnail.contains("undefined")){
    	video.setThumbnail(thumbnail);
    }
    video.setSource(Constants.qq);
    video.setPageUrl(url);
    video.setSummary(summary);
    if(source.contains("undefined")){
    	source = playerUrl;
    }
    video.setAddress(source);
    video.setCommonCode(getCommonCode(playerUrl));
    return video;
  }

  /**
 * @author GaoFeng 
 * @date 2016-7-8下午02:48:13
 * @desc 酷6
 */	
public static Video getKu6Video(String url) throws Exception {
	
    Document doc = getURLContent(url);

    String content = doc.html();
    int beginLocal = content.indexOf("VideoInfo:{");
    int endLocal = content.indexOf(", ObjectInfo");
    content = content.substring(beginLocal + 10, endLocal).trim();

    String title = doc.select("meta[name=title]").attr("content");
    if(title == null || "".equals(title)){
    	title = doc.title();
    }
    String address = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".html"));
    address = "http://player.ku6.com/refer/" + address + "/v.swf";
    String thumbnail = getScriptVarByName("cover", content);
    if(thumbnail != null && !"".equals(thumbnail)){
    	thumbnail = thumbnail.substring(1, thumbnail.length() - 1);
    }
    String time = getScriptVarByName("time", content);
    if(time != null && !"".equals(time)){
    	 time = time.substring(1, time.length() - 1);
    }
    String summary = doc.select("meta[name=description]").attr("content");

    Video video = new Video();
    video.setTitle(title);
    video.setThumbnail(thumbnail);
    video.setTime(formatSecond(Integer.parseInt(time)));
    video.setSource(Constants.ku6);
    video.setPageUrl(url);
    video.setSummary(summary);
    video.setAddress(address);
    video.setCommonCode(getCommonCode(address));
    return video;
  }

  /**
 * @author GaoFeng 
 * @date 2016-7-8下午02:48:23
 * @desc 乐视
 */	
public static Video getLetvVideo(String url) throws Exception {
	
    Document doc = getURLContent(url);
    String content = doc.html();
    int beginLocal = content.indexOf("var __INFO__ = {");
    int endLocal = content.indexOf("playlist:");
    content = content.substring(beginLocal + 15, endLocal).trim();

    String title = doc.select("meta[name=irTitle]").attr("content");
    if(title == null || "".equals(title)){
    	title = doc.title();
    }
    String thumbnail = getScriptVarByName("videoPic", content);
    if(thumbnail != null && !"".equals(thumbnail)){
    	thumbnail = thumbnail.substring(0, thumbnail.length() - 1);
    }
    String address = getScriptVarByName("vid", content);
    address = "http://img1.c0.letv.com/ptv/player/swfPlayer.swf?id=" + address + "&autoplay=0";
    String time = getScriptVarByName("duration", content);
    String summary = doc.select("meta[name=description]").attr("content");

    Video video = new Video();
    video.setTitle(title);
    video.setThumbnail(thumbnail);
    video.setTime(time);
    video.setSource(Constants.letv);
    video.setPageUrl(url);
    video.setSummary(summary);
    video.setCommonCode(getCommonCode(address));
    return video;
  }

  /**
 * @author GaoFeng 
 * @date 2016-7-8下午02:48:30
 * @desc 搜狐
 */	
public static Video getSohuVideo(String url) throws Exception {
	
    Document doc = getURLContent(url);

    String title = doc.title();
    if(title == null || "".equals(title)){
    	title = doc.select("meta[property=og:title]").attr("content");
    	if(title != null && title.length() > 50){
    		title = title.substring(0, 50);
    	}
    }
    String summary = doc.select("meta[name=description]").attr("content");
    String thumbnail = doc.select("meta[property=og:image]").attr("content");
    String address = doc.select("meta[property=og:videosrc]").attr("content");
    String id = null;
    if(address != null){
    	id = address.substring(address.indexOf(".com") + 5, address.lastIndexOf("/"));
    	address = "http://tv.sohu.com/upload/static/share/share_play.html#" + id;
    }
    
    Video video = new Video();
    video.setTitle(title);
    video.setThumbnail(thumbnail);
    video.setSource(Constants.sohu);
    video.setPageUrl(url);
    video.setSummary(summary);
    video.setAddress(address);
    video.setCommonCode(getCommonCode(address));
    return video;
  }

  /**
 * @author GaoFeng 
 * @date 2016-7-8下午02:48:37
 * @desc 凤凰网
 */	
public static Video getIfengVideo(String url) throws Exception {
	
    Document doc = getURLContent(url);

    String title = doc.select("meta[property=og:title]").attr("content");
    if(title == null || "".equals(title)){
    	title = doc.title();
    }
    String summary = doc.select("meta[property=og:description]").attr("content");
    String videoId = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".shtml"));
    String address = "http://v.ifeng.com/include/exterior.swf?guid=" + videoId + "&AutoPlay=false";
    String thumbnail = doc.select("meta[property=og:image]").attr("content");

    Video video = new Video();
    video.setTitle(title);
    video.setThumbnail(thumbnail);
    video.setSource(Constants.ifeng);
    video.setPageUrl(url);
    video.setSummary(summary);
    video.setAddress(address);
    video.setCommonCode(getCommonCode(address));
    return video;
  }

  /**
 * @author GaoFeng 
 * @date 2016-7-8下午02:48:45
 * @desc 音悦台
 */	
public static Video getYinYueTaiVideo(String url) throws Exception {
	
    Document doc = getURLContent(url);

    String title = doc.select("meta[property=og:title]").attr("content");
    if(title == null || "".equals(title)){
    	title = doc.title();
    }
    String summary = doc.select("meta[property=og:description]").attr("content");
    String thumbnail = doc.select("meta[property=og:image]").attr("content");
    String address = doc.select("meta[property=og:videosrc]").attr("content");

    Video video = new Video();
    video.setTitle(title);
    video.setThumbnail(thumbnail);
    video.setSource(Constants.yinyuetai);
    video.setPageUrl(url);
    video.setSummary(summary);
    video.setAddress(address);
    video.setCommonCode(getCommonCode(address));
    return video;
  }

  /**
 * @author GaoFeng 
 * @date 2016-7-8下午02:48:52
 * @desc 新浪
 */	
public static Video getSinaVideo(String url) throws Exception {
	
    Document doc = getURLContent(url);
    
    String content = doc.html();
    int beginLocal = content.indexOf("$SCOPE['video'] = {");
    int endLocal = content.indexOf("AllCount:0");
    content = content.substring(beginLocal, endLocal + 15).trim();

    String title = doc.select("meta[name=keywords]").attr("content");
    if(title == null || "".equals(title)){
    	title = doc.title();
    }
    String address = getScriptVarByName("swfOutsideUrl", content);
    String thumbnail = getScriptVarByName("pic", content);
    String summary = doc.select("meta[name=description]").attr("content");

    Video video = new Video();
    video.setTitle(title);
    video.setThumbnail(thumbnail);
    video.setSource(Constants.sina);
    video.setPageUrl(url);
    video.setSummary(summary);
    video.setCommonCode(getCommonCode(address));
    return video;
  }

  /**
 * @author GaoFeng 
 * @date 2016-7-8下午02:49:01
 * @desc 爱奇艺
 */	
public static Video getAiqiyiVideo(String url) throws Exception {
	
    Document doc = getURLContent(url);
    
    String title = doc.select("meta[name=irTitle]").attr("content");
    if(title == null || "".equals(title)){
    	title = doc.title();
    }
    String videoid = doc.select("div[data-widget-player=flash]").attr("data-player-videoid");
    String tvid = doc.select("div[data-widget-player=flash]").attr("data-player-tvid");
    String address = "http://open.iqiyi.com/developer/player_js/coopPlayerIndex.html?vid=" + videoid + "&tvId=" + tvid ;
    String commonCode = "<iframe src=\"" + address + "&height=100%&width=100%\" frameborder=\"0\" allowfullscreen=\"false\" width=\"100%\" height=\"100%\" autoplay=\"false\"></iframe>";
    String thumbnail = doc.select("meta[property=og:image]").attr("content");
    String summary = doc.select("meta[itemprop=description]").attr("content");

    Video video = new Video();
    video.setTitle(title);
    video.setThumbnail(thumbnail);
    video.setSource(Constants.iqiyi);
    video.setPageUrl(url);
    video.setAddress(address);
    video.setSummary(summary);
    video.setCommonCode(commonCode);
    return video;
  }

  private static String getScriptVarByName(String name, String content) {
    String script = content;
    int begin = script.indexOf(name);
    script = script.substring(begin + name.length() + 2);
    int end = script.indexOf(",");
    script = script.substring(0, end);
    String result = script.replaceAll("'", "");
    return result.trim();
  }

  private static String getElementAttrById(Document doc, String id, String attrName) throws Exception {
    Element et = doc.getElementById(id);
    String attrValue = null;
    if(et != null){
    	  attrValue = et.attr(attrName);
    }
  
    return attrValue;
  }

  private static String getCommonCode(String flashUrl) throws Exception {
    return "<iframe width=\"100%\" src=\"" + flashUrl + "&auto=0\" frameborder=0 allowfullscreen></iframe>";
  }

  public static String formatSecond(int second) {
    String html = "0";
    if (second != 0)
    {
      Integer hours = Integer.valueOf(second / 3600);
      Integer minutes = Integer.valueOf(second / 60 - hours.intValue() * 60);
      Integer seconds = Integer.valueOf(second - minutes.intValue() * 60 - hours.intValue() * 60 * 60);
      Object[] array;
      String format;
      if (hours.intValue() > 0) {
        format = "%1$,d:%2$,d:%3$,d";
        array = new Object[] { hours, minutes, seconds };
      }
      else
      {
        if (minutes.intValue() > 0) {
          format = "00:%1$,d:%2$,d";
          array = new Object[] { minutes, seconds };
        } else {
          format = "00:00:%1$,d";
          array = new Object[] { seconds };
        }
      }
      html = String.format(format, array);
    }
    return html;
  }
  

  private static Document getURLContent(String url) throws Exception {
    Document doc = Jsoup.connect(url).data("query", "Java").userAgent("Mozilla").cookie("auth", "token").timeout(5000).get();
    return doc;
  }
}