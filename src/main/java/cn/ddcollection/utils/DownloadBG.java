package cn.ddcollection.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.jets3t.service.S3Service;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;

import cn.ddcollection.controller.DataController;
import cn.ddcollection.model.AccessKey;
import cn.ddcollection.model.RealTimePrc;
import cn.ddcollection.service.RealTimePrcService;
import cn.ddcollection.service.SSSService;
import cn.ddcollection.service.impl.RealTimePrcServiceImpl;
import cn.ddcollection.service.impl.SSSServiceImpl;

public class DownloadBG {
	//	@Resource(name = "sssService")
//	private SSSService sssService;
//	private SSSService sssService=new SSSServiceImpl();
//	@Resource(name = "realTimePrcService")
//	private RealTimePrcServiceImpl realTimePrcService;
//	private RealTimePrcServiceImpl realTimePrcService = new RealTimePrcServiceImpl();
	static CountDownLatch runningThreadNum = null;
	
	public static void execute(SSSService sssService,RealTimePrcServiceImpl realTimePrcService){
		System.out.println("kaishi...........................................................");
		List<AccessKey> accessKeys = PropertiesOperatator
				.getUserName(DownloadBG.class.getClassLoader().getResource("").getPath() + "conf/userkey.properties");
		for (AccessKey accessKey : accessKeys) {
			AWSCredentials awsCredentials = new AWSCredentials(accessKey.getUserkey().split(" ")[0],
					accessKey.getUserkey().split(" ")[1]);
			S3Service s3Service = new RestS3Service(awsCredentials);
			String bucketname = "dataresult_"+accessKey.getUsername();
			S3Object[] objects;
			try {
				objects = s3Service.listObjects(bucketname);
				long time0 = System.currentTimeMillis();
				for(int i=0;i<objects.length;i++){
					String objectname = objects[i].getName();
					S3Object s3Obj = s3Service.getObject(bucketname, objectname);
					System.out.println(s3Obj.getContentLength()+"?????????????????????????????????"+objectname);
					if(objectname.contains(".")&&s3Obj.getContentLength()!=0){
						String localpath = "F:/学习资料/测试文件/result/";
						long time10 = System.currentTimeMillis();
						String filename = sssService.downloadObject(bucketname,localpath, objectname, s3Service);
						s3Service.deleteObject(bucketname, objectname);
//						String filename = "RealtimePrc2016-04-14-11-51-01.properties";
						long time11 = System.currentTimeMillis();
						System.out.println("下载耗时："+(time11-time10)+"--------------------");
						long time12 = System.currentTimeMillis();
						runningThreadNum=new CountDownLatch(3);						
						MyRunnable r = new MyRunnable(localpath, filename, realTimePrcService);
						new Thread(r).start();
						new Thread(r).start();
						new Thread(r).start();
//						runningThreadNum.await();
//						long time13 = System.currentTimeMillis();
//						System.out.println("对象耗时："+(time13-time12)+"--------------------");
//						long time14 = System.currentTimeMillis();
//						long time15 = System.currentTimeMillis();
//						System.out.println("存储耗时："+(time15-time14)+"--------------------");
						
					}
				}
				long time1 = System.currentTimeMillis();
				System.out.println("总耗时："+(time1-time0)+"--------------------");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static RealTimePrc genMultiThreadRTPrcObject(String content){
		String Symbol;
		String Name;
		BigDecimal TodayStartPrc;
		BigDecimal YesPrc;
		BigDecimal CurrentPrc;
		BigDecimal HighPrc;
		BigDecimal LowPrc;
		BigDecimal BuyPrc;
		BigDecimal SelPrc;
		int FinishVol;
		BigDecimal FinishPrc;
		BigDecimal BuyOnePrc;
		BigDecimal BuyTwoPrc;
		BigDecimal BuyThreePrc;
		BigDecimal BuyFourPrc;
		BigDecimal BuyFivePrc;
		BigDecimal SelOnePrc;
		BigDecimal SelTwoPrc;
		BigDecimal SelThreePrc;
		BigDecimal SelFourPrc;
		BigDecimal SelFivePrc;
		int BuyOneAmt;
		int BuyTwoAmt;
		int BuyThreeAmt;
		int BuyFourAmt;
		int BuyFiveAmt;
		int SelOneAmt;
		int SelTwoAmt;
		int SelThreeAmt;
		int SelFourAmt;
		int SelFiveAmt;
		Timestamp Time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
	
		String[] strArray = content.split(",");
		System.out.println("------长度"+strArray.length);
		String n = strArray[0];
		int nameIndex = n.indexOf("\"");
		Symbol = n.substring(nameIndex-7, nameIndex-1);
		Name = n.substring(nameIndex+1);
		TodayStartPrc = BigDecimal.valueOf(Double.parseDouble(strArray[1]));
		YesPrc = BigDecimal.valueOf(Double.parseDouble(strArray[2]));
		CurrentPrc = BigDecimal.valueOf(Double.parseDouble(strArray[3]));
		HighPrc = BigDecimal.valueOf(Double.parseDouble(strArray[4]));
		LowPrc = BigDecimal.valueOf(Double.parseDouble(strArray[5]));
		BuyPrc = BigDecimal.valueOf(Double.parseDouble(strArray[6]));
		SelPrc = BigDecimal.valueOf(Double.parseDouble(strArray[7]));
		FinishVol = Integer.parseInt(strArray[8]);
		FinishPrc = BigDecimal.valueOf(Double.parseDouble(strArray[9]));
		BuyOnePrc = BigDecimal.valueOf(Double.parseDouble(strArray[11]));
		BuyOneAmt = Integer.parseInt(strArray[10]);
		BuyTwoPrc = BigDecimal.valueOf(Double.parseDouble(strArray[13]));
		BuyTwoAmt = Integer.parseInt(strArray[12]);
		BuyThreePrc = BigDecimal.valueOf(Double.parseDouble(strArray[15]));
		BuyThreeAmt = Integer.parseInt(strArray[14]);
		BuyFourPrc = BigDecimal.valueOf(Double.parseDouble(strArray[17]));
		BuyFourAmt = Integer.parseInt(strArray[16]);
		BuyFivePrc = BigDecimal.valueOf(Double.parseDouble(strArray[19]));
		BuyFiveAmt = Integer.parseInt(strArray[18]);
		SelOnePrc = BigDecimal.valueOf(Double.parseDouble(strArray[21]));
		SelOneAmt = Integer.parseInt(strArray[20]);
		SelTwoPrc = BigDecimal.valueOf(Double.parseDouble(strArray[23]));
		SelTwoAmt = Integer.parseInt(strArray[22]);
		SelThreePrc = BigDecimal.valueOf(Double.parseDouble(strArray[25]));
		SelThreeAmt = Integer.parseInt(strArray[24]);
		SelFourPrc = BigDecimal.valueOf(Double.parseDouble(strArray[27]));
		SelFourAmt = Integer.parseInt(strArray[26]);
		SelFivePrc = BigDecimal.valueOf(Double.parseDouble(strArray[29]));
		SelFiveAmt = Integer.parseInt(strArray[28]);
		
	
		try {
			Time = new Timestamp(sdf.parse(strArray[30]+" "+strArray[31]).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		RealTimePrc realTimePrc = new RealTimePrc(Symbol, Name, TodayStartPrc, YesPrc, CurrentPrc, HighPrc, 
				LowPrc, BuyPrc, SelPrc, FinishVol, FinishPrc, BuyOnePrc, BuyTwoPrc, BuyThreePrc, BuyFourPrc, 
				BuyFivePrc, SelOnePrc, SelTwoPrc, SelThreePrc, SelFourPrc, SelFivePrc, BuyOneAmt, BuyTwoAmt, 
				BuyThreeAmt, BuyFourAmt, BuyFiveAmt, SelOneAmt, SelTwoAmt, SelThreeAmt, 
				SelFourAmt, SelFiveAmt, Time);
		return realTimePrc;
	}
	
}

class MyRunnable implements Runnable{
	
	Properties p;
	Iterator it;
	RealTimePrcService realTimePrcService;

	public MyRunnable(String localpath,String filename,RealTimePrcService realTimePrcService) {
		p = new Properties();
		InputStream is;
		try {
			is = new FileInputStream(localpath+filename);
			p.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set pKey =p.keySet();
		it = pKey.iterator();
		this.realTimePrcService = realTimePrcService;
	}

	@Override
	public void run() {
		String data = null;
		String s = null;
		while(true){
			synchronized (this){
				Boolean f = it.hasNext();
				if(f){
					s = (String)it.next();
				}else{
					DownloadBG.runningThreadNum.countDown();
					return ;
				}
			}
			data = p.getProperty(s);
			RealTimePrc rtp = DownloadBG.genMultiThreadRTPrcObject(data);
			realTimePrcService.insertRealTimePrc(rtp);
		}
	}
	
}
