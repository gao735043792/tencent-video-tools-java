package com.huas.test;

import com.huas.bean.Video;
import com.huas.util.VideoUtil;

public class TestVideo {

	public static void main(String[] args) {
		
		try {
			//String url = "http://v.qq.com/x/cover/s52de93iocbdo10.html?vid=x03114a5yst";
			//String url = "https://m.v.qq.com/x/cover/g/gu30rrfp239hddo.html?vid=d0531wdq4ig&ptag=";
			//String url = "https://v.qq.com/x/cover/jf91ufejgz3k0e2.html";
			//String url = "https://v.qq.com/x/page/f0348u1anj1.html";
			//String url = "https://v.qq.com/x/cover/oudsvhx4nau5z9c/o0520z06vzt.html";

			//String url = "https://m.v.qq.com/x/page/e/7/4/e0363trhf74.html";
			//String url = "http://m.v.qq.com/play/play.html?vid=b0324f3lgsx&ptag=4_5.3.0.16792_copy";
			//String url = "https://m.v.qq.com/cover/0/0juu8vidmtu02yy.html";
			//String url = "http://m.v.qq.com/play/play.html?coverid=&vid=b0145ajq9cq";
			//String url = "https://m.v.qq.com/x/page/o/l/8/o0022t0hil8.html";
			//String url = "http://m.v.qq.com/page/m/0/4/m0390honw84.html";
			//String url = "https://m.v.qq.com/x/page/x/x/r/x05581xwfxr.html";
			
			String url = "https://m.v.qq.com/x/cover/d/dvg3ze6zppsvr0i.html?vid=w0025puzhm7&ptag=health.zmpaler.com%23v.index.adaptor%232";
			
			Video video = VideoUtil.getVideoInfo(url);
			System.out.println(video.getTitle());
			System.out.println(video.getCommonCode());
			System.out.println(video.getThumbnail());
			System.out.println(video.getAddress());
			System.out.println(video.getSummary());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
