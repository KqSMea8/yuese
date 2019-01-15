package com.net.yuesejiaoyou.classroot.interface4;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;

import com.net.yuesejiaoyou.classroot.interface4.util.Util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 安卓版探针	当前版本号：1.0.06
 * 
 * 1.0.01: 使用&lt;和&gt;替换字符串数据中的尖括号"<"、">"那样就可以在htm中以文本的形式显示尖括号
 * 1.0.02: 添加探针过滤
 * 			1. 每条数据末尾追加"\r\n"换行字符，便于按行对每条数据进行过滤
 * 			2. 在每条数据的日期和文件名字段之间添加IP占位符，发送到服务版后，由服务器进行IP替换(本机不太容易直接获取外网ip)
 * 			3. 添加System1类，使用System1.out.println(String)打印字符串
 * 				注：System1类的引入，会导致System1.out.println()方法的调用文件和行的判断不准确，需要修正
 * 			4. append(DataType, String, Object)和send(DataType, String, Object)方法
 * 				加同步标志，因为两个方法有颜色设置和恢复动作，如果不使用同步处理可能会造成颜色无法恢复。
 * 			5. 为了获取安卓的IP地址(java的InetAddress.getLocalHost()无法获取安卓ip地址)，需要安卓的Context类型对象，init()初始化的时候传输这个参数
 * 1.0.03: 修改安卓获取本机IP地址方式(相对wifi和移动网络更通用的方法)
 * 1.0.04: 添加getRunningAppProcessInfo()方法获取当前App内存占用大小(KB)
 * 1.0.05: 修正"java.lang.InternalError: Thread starting during runtime shutdown"异常崩溃问题，在UncaughtExceptionHandler接口的uncaughtException()
 * 			方法里面创建的线程里面再嵌套线程，会有很大几率的崩溃风险，uncaughtException()方法执行完毕后会APP的运行时环境会shutdown(ART)，这个时候如果嵌套的内部线程才启动就会抛出上面的异常，
 * 			解决的办法就是尽量不要在uncaughtException()方法里面嵌套创建并启动线程，HttpClient的创建会伴随线程的创建，可以在线程外创建完成后以参数形式传递进去。
 * 			补充：在捕获到异常信息后关闭探针功能，防止再次启动线程。
 * 1.0.06: 
 * 			1. 新增getClientInfo()方法获取APK客户端手机型号android版本号和APK版本号信息，APK版本号需要手动修改APKVERSION变量。
 * 			2. HttpClient连接有时会超时，造成探针卡顿31秒，这样会引起APK无响应崩溃，缩短HttpClient超时时间，并做超时重发。
 * 			3. 将init()方法的Context参数改为弱引用，避免影响Activity的资源释放。
 * 			
 * 
 * @author Administrator
 *
 */
public class LogDetect {
	
	//安卓版APK版本
	private static final String APKVERSION = "1.2.51";

	// 日志文本处理相关
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	private static StringBuffer totalString = new StringBuffer();	// 所有累加字符串数据
	private static StringBuffer lineString = new StringBuffer();	// 一条字符串数据
	private static String logFile;	// 日志文件和异常崩溃文件的存放路径，包括绝对路径和文件名
	private static String clientName;
	private static String userID;
	private static DataType enumDataType = DataType.noType;
	private static String dataType = "notype";	// 数据类型,默认为无类型
	// HttpClient相关
	//private static AsyncHttpClient client = new AsyncHttpClient();
	private static String url = Util.url+"/ExcpServletInOut";	// 网络探针数据http提交链接
	//private static String url = "http://192.168.0.3:8080/WeixinLock/ExcpServletInOut";	// 网络探针数据http提交链接
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,200, TimeUnit.MILLISECONDS,
			new ArrayBlockingQueue<Runnable>(1000));	// 容量只有1的线程池，为了实现单线程循环执行(为了避免多线程并行的冲突)
	private static ArrayList<String> strList = new ArrayList<String>();
	// 关键字粉红色高亮显示
	private static Set<String> keyWords = new HashSet<String>();	// 存放关键字
	// 安卓相关
	private static Context context;
	// 内存容量记录
	private static int memSizeOld;
	private static int memSizeOld2;
	private static ActivityManager manager;
	// 添加探针开关
	private static boolean bClose = true;
	
	/**
	 * 安卓端探针初始化
	 * @param id		程序员工号id，例如：01107
	 * @param name		当前安卓端的名称
	 */
	public static void init(WeakReference<Context> ctxt, String id, String name) {
		
		context = ctxt.get();
		userID = id;
		clientName = name;
		Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		//
		manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		// 初始化关键字列表
		keyWords.add("-json");
		keyWords.add("HM NOTE 1TD");
		keyWords.add("OrdersManageServlet");
	}
	
	/**
	 * 关闭探针功能
	 */
	public static void close() {
		bClose = true;
		executor.shutdown();
	}
	
	/**
	 * 将对象转化为字符串存入totalString
	 * 可以识别数组、ArrayList、HashMap、String、int等基本类型
	 * 和泛型的组合，对于自定义类型需要程序员自己实现toString()方法
	 * @param obj
	 */
	private static void recursion(Object obj) {
		if(obj == null) {
			lineString.append("null");
			return;
		}
		
		// String类型
		if(obj instanceof String) {
			lineString.append(((String)obj).replaceAll("<", "&lt;").replaceAll(">", "&gt;"));	// 将字符串中的加括号替换成html中可显示的形式
		// int类型
		} else if(obj instanceof Integer) {
			lineString.append(obj);
		// 数组类型
		} else if(obj.getClass().isArray()) {
			int length = Array.getLength(obj);
			lineString.append("[");
			for(int i = 0; i < length; i++) {
				recursion(Array.get(obj,i));
				
				if(i < length - 1) {
					lineString.append(",");
				}
			}
			lineString.append("]");
		// ArrayList类型
		} else if(obj instanceof ArrayList) {
			ArrayList arrayList = (ArrayList)obj;
			int length = arrayList.size();
			lineString.append("[");
			for(int i = 0; i < length; i++) {
				recursion(arrayList.get(i));
				if(i < length-1) {
					lineString.append(",");
				}
			}
			lineString.append("]");
		// HashMap类型
		} else if(obj instanceof HashMap) {
			HashMap map = (HashMap)obj;
			int length = map.size();
			int index = 0;
			lineString.append("[");
			for(Object key : map.keySet()) {
				index++;
				lineString.append("(");
				recursion(key);
				lineString.append(",");
				recursion(map.get(key));
				
				if(index < length) {
					lineString.append("),");
				} else {
					lineString.append(")");
				}
			}
			lineString.append("]");
		// 其他自定义类型，需要程序员重写toString()方法
		} else {
			lineString.append(obj.toString());
		}
	}
	
	private static String getObjString(Object obj) {
		recursion(obj);
		
		String returnString = lineString.toString();
		lineString.setLength(0);	// 清空lineString
		
		return returnString;
	}
	
	/**
	 *  探针数据累加到totalString
	 * @param tag	一条探针数据的tag部分
	 * @param obj	一条探针数据的实际内容
	 * @throws UnknownHostException
	 */
	public static void append(String tag, Object obj) {
		
		// 如果探针功能已关闭，停止解析
		if(bClose) {
			return;
		}
		
		boolean bContain = false;
		//---------------------------------------
		// 识别当前类名和行号
		int stackDeepth = Thread.currentThread().getStackTrace().length;	// 获取调用栈深度
		int stackIndex = 0;
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		for(int i = 1; i <= stackDeepth; i++) {
			String filename = elements[stackDeepth-i].getFileName();
			if(filename != null && (filename.equals("LogDetect.java") || filename.equals("System1.java"))) {	// 有些情况filename为null
				stackIndex = i - 1;
				break;
			}
		}
		String className = Thread.currentThread().getStackTrace()[stackDeepth-stackIndex].getFileName().split("\\.")[0];
		int lineNum = Thread.currentThread().getStackTrace()[stackDeepth-stackIndex].getLineNumber();
		// 获取对象解析的字符串
		String strLine = getObjString(obj);
		// 进行关键字比对
		for(String str : keyWords) {
			if(strLine.contains(str)) {
				bContain = true;
				strLine = strLine.replaceAll(str, "<font color=\"blue\">"+str+"</font>");	// 将关键字添加背景蓝色标签
				break;
			}
		}
		// 获取本机IP
		String strIp = null;
		try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                    //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {    
                        strIp = inetAddress.getHostAddress().toString();	//return inetAddress.getHostAddress().toString();
                        break;
                    }    
                }
                if(strIp != null) {
                	break;
                }
            }    
        } catch (Exception e) {
        } 
		if(strIp == null) {
			strIp = "127.0.0.1";
		}
		// 如果包含关键字则粉红色显示
		if(bContain) {
			totalString.append("<font color=\"#f31af3\">"+df.format(new Date())+": [${ip}"+strIp+"] ["+userID+"-"+className+"-"+lineNum+"] "+tag);
		} else {
			totalString.append("<font color=\""+dataType+"\">"+df.format(new Date())+": [${ip}"+strIp+"] ["+userID+"-"+className+"-"+lineNum+"] "+tag);
		}
		totalString.append(android.os.Build.MODEL+"|"+android.os.Build.VERSION.SDK+"|"+android.os.Build.VERSION.RELEASE);
		totalString.append(strLine);
		totalString.append("</font><br>\r\n");	// 每条数据占一行
	}
	
	/**
	 * 探针数据累加的同时做好颜色设置的设置与恢复
	 * @param type		数据类型，用来获取颜色信息
	 * @param tag		一条探针数据的tag部分
	 * @param obj		一条探针数据的实际内容
	 */
	public synchronized static void append(DataType type, String tag, Object obj) {
//		DataType oldType = setDataType(type);
//		append(tag,obj);
//		setDataType(oldType);
	}
	
	/**
	 * 发送探针数据,将所有累加的探针数据和当前要打印的内容一起提交到网络探针数据
	 * @param tag	一条探针数据的tag部分
	 * @param obj	一条探针数据的实际内容
	 */
	public static void send(String tag, Object obj) {
		append(tag,obj);
		flush();
	}

	/**
	 * 发送探针数据,将所有累加的探针数据和当前要打印的内容一起提交到网络探针数据
	 * 并做好颜色的设置和恢复
	 * @param type		数据类型，用来获取颜色信息
	 * @param tag		一条探针数据的tag部分
	 * @param obj		一条探针数据的实际内容
	 */
	public synchronized static void send(DataType type, String tag, Object obj) {
//       DataType oldType = setDataType(type);
//       send(tag,obj);
//       setDataType(oldType);
	}

	/**
	 * 通过httpclient发送totalString里面的字符串
	 * 发送完成后清空totalString
	 * @param str			要发送的字符串
	 */
	@SuppressWarnings("deprecation")
	private static void httpPostString(String str) {
		BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 1000);
        HttpConnectionParams.setSoTimeout(httpParams, 1000);
		HttpClient httpclient = new DefaultHttpClient(httpParams);
		HttpPost httppost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("mode1", "A-user-add"));
		params.add(new BasicNameValuePair("mode2", clientName));
		params.add(new BasicNameValuePair("mode3", str));
		HttpEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(params, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			//TODO Auto-generated catch block
			e1.printStackTrace();
		}

		httppost.setEntity(entity);
		
		boolean isTimeout = false;	// 是否是超时
		int failCount = 0;	// 超时发送次数

		do{
			try {
				httpclient.execute(httppost);
				isTimeout = false;
			} catch(HttpHostConnectException e) {
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConnectTimeoutException e) {
				// 累计超时发送10次后退出循环
				if((++failCount) > 10) {
					isTimeout = false;
				} else {
					isTimeout = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(isTimeout);
	}
	
	/**
	 * 用于将totalString中的内容走http提交到网络探针服务版
	 * 如果totalString为空则无操作
	 */
	public synchronized static void flush() {

		Log.e("TT","logdetect flush");
		// 若缓存数据为空则无操作,或者探针功能已关闭则立即返回
		if(totalString.length() == 0 || bClose) {
			return;
		}
		
		// 提前创建(在线程外)创建HttpClient对象
		//final HttpClient httpclient = new DefaultHttpClient();
		//final HttpPost httppost = new HttpPost(url);
		
		// 获取当前线程信息，如果是主线程则用异步http如果是子线程则用同步http
		if(Thread.currentThread().getName().equals("MainActivity")) {
			// 多线程发送http数据会发生顺序错乱，而且所有线程公用totalString也会发生数据错误，
			// 所以先将字符串数据存入strList，然后用只有一个线程的线程池，按顺序发送strList
			// 里面的数据
			strList.add(totalString.toString());
			
			executor.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(strList.size() != 0) {
						httpPostString(strList.remove(0));
					}
				}
			});
		} else {
			httpPostString(totalString.toString());
		}
		
		totalString.setLength(0);	// 清空字符串缓存
	}
	
	/**
	 * 获取安卓当前App的内存大小，返回值单位为KB
	 * @return
	 */
	public static String getRunningAppProcessInfo() {
	    // 通过调用ActivityManager的getRunningAppProcesses()方法获得系统里所有正在运行的进程
		if(context == null) {
			return "0";
		}

		int memSize2 = (int)(Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory())/1024;

	    List<ActivityManager.RunningAppProcessInfo> appProcessList = manager
	            .getRunningAppProcesses();    
	  
	    for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
	        // 进程ID号    
	        int pid = appProcessInfo.pid;    
	        // 用户ID 类似于Linux的权限不同，ID也就不同 比如 root等    
	        int uid = appProcessInfo.uid;    
	        // 进程名，默认是包名或者由属性android：process=""指定    
	        String processName = appProcessInfo.processName;
	        // 获得该进程占用的内存    
	        int[] myMempid = new int[] { pid };    
	        // 此MemoryInfo位于android.os.Debug.MemoryInfo包中，用来统计进程的内存信息    
	        Debug.MemoryInfo[] memoryInfo = manager.getProcessMemoryInfo(myMempid);
	        // 获取进程占内存用信息 kb单位    
	        int memSize = memoryInfo[0].dalvikPrivateDirty;    
	        
	        String strReturn = "("+memSize+","+memSize2+"|"+(memSize-memSizeOld)+","+(memSize2-memSizeOld2)+")KB";
	        memSizeOld = memSize;
	        memSizeOld2 = memSize2;
	        return strReturn;
	  
	        // 获得每个进程里运行的应用程序(包),即每个应用程序的包名    
//	        String[] packageList = appProcessInfo.pkgList;    
//	        Log.i(TAG, "process id is " + pid + "has " + packageList.length);    
//	        for (String pkg : packageList) {    
//	            Log.i(TAG, "packageName " + pkg + " in process id is -->"+ pid);    
//	        }    
	    }
	    return "0";
	}
	
	public static DataType setDataType(DataType type) {
		DataType oldType = enumDataType;
		enumDataType = type;
		switch(type) {
		case noType:	// 普通
			dataType = "black";
			break;
		case basicType:	//　基本数据类型
			dataType = "blue";
			break;
		case nonbasicType:	// 非基本数据类型
			dataType = "#bbbb00";
			break;
		case exceptionType:	// 异常类型
			dataType = "red";
			break;
		case specialType:	// 自定义特殊标志
			dataType = "green";
			break;
		}
		
		return oldType;
	}

	public static void writeCause(DataType type, String userID, String tag, Throwable obj){
		DataType oldType = setDataType(type);
		String info;
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		obj.printStackTrace(pw);
		info = writer.toString();
		pw.close();
		try {
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		flush();
		setDataType(oldType);
	}
	
	public enum DataType{
		noType,		// 普通数据
		basicType,	// 基本数据类型
		nonbasicType,	// 非基本数据类型
		exceptionType,	// 异常数据类型
		specialType		// 特殊标志
	}

	public static void writeFile(String string) {
		String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test/";
		File dir=new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String filename=df.format(new Date())+"_test.txt";
		FileOutputStream outputStream;
		try {
			File text=new File(path+filename);
			outputStream = new FileOutputStream(text,true);
			outputStream.write(("---------------------------------------------------\r\n"+df2.format(new Date())+"\r\n").getBytes());
			outputStream.write(string.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

	private String getStackInfo(Throwable e) {
		String info;
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		e.printStackTrace(pw);
		info = writer.toString();
		pw.close();
		try {
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return info;
	}

	/**
	 * 写文件
	 * @param string
	 */
	public static void writeFile(String string) {
		String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test/";
		File dir=new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String filename=df.format(new Date())+"_test.txt";
		FileOutputStream outputStream;
		try {
			File text=new File(path+filename);
			outputStream = new FileOutputStream(text,true);
			outputStream.write(("---------------------------------------------------\r\n"+df2.format(new Date())+"\r\n").getBytes());
			outputStream.write(string.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private synchronized void sendToLog(Thread t, Throwable ex) {

		writeFile(getStackInfo(ex));

		LogDetect.DataType oldType = LogDetect.setDataType(LogDetect.DataType.exceptionType);
		//send(tag,obj);
		LogDetect.send("CrashLog("+t.getName()+"|"+LogDetect.getRunningAppProcessInfo()+" - "+android.os.Build.MODEL+"|"+android.os.Build.VERSION.SDK+"|"+android.os.Build.VERSION.RELEASE+"): ",getStackInfo(ex));
		LogDetect.setDataType(oldType);
		//LogDetect.send(LogDetect.DataType.exceptionType,"CrashLog("+LogDetect.getRunningAppProcessInfo()+"): ",getStackInfo(ex));
		LogDetect.close();	// 遇到异常打印完数据后关闭探针功能，主要是防止再次启动线程，避免出现 java.lang.InternalError: Thread starting during runtime shutdown异常
	}
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// TODO Auto-generated method stub
		sendToLog(t,e);
	try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}