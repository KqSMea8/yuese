/**
 *
 * Copyright the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import java.util.regex.Pattern;

/**
 * Http Proxy Socket Factory which returns socket connected to Http Proxy
 * 
 * @author Atul Aggarwal
 */
class HTTPProxySocketFactory 
    extends SocketFactory
{

    private ProxyInfo proxy;

    public HTTPProxySocketFactory(ProxyInfo proxy)
    {
        this.proxy = proxy;
    }

   
    

    private static final Pattern RESPONSE_PATTERN
        = Pattern.compile("HTTP/\\S+\\s(\\d+)\\s(.*)\\s*");

	@Override
	public Socket createSocket(String arg0, int arg1) throws IOException,
			UnknownHostException {
		// TODO Auto-generated method stub
		return httpProxifiedSocket(arg0, arg1);
	}

	private Socket httpProxifiedSocket(String host, int port)
	        throws IOException 
	    {
		    //获取IP和端口链接socket
	        String proxyhost = proxy.getProxyAddress();
	        int proxyPort = proxy.getProxyPort();
	        //String proxyhost = "192.168.0.3";
	        //int proxyPort = 5222;
	        @SuppressWarnings("resource")
	        //Socket socket = new Socket(proxyhost,proxyPort);
	        //直接使用参数Ip和端口号
	        Socket socket = new Socket(host,port);
	        //String hostport = "CONNECT " + host + ":" + port;
	        
		// String proxyLine;
		// //String username = proxy.getProxyUsername();
		// String username = "123";
		// if (username == null)
		// {
		// proxyLine = "";
		// }
		// else
		// {
		// //String password = proxy.getProxyPassword();
		// String password ="123456";
		// proxyLine = "\r\nProxy-Authorization: Basic " +
		// StringUtils.encodeBase64(username + ":" + password);
		// }
		// socket.getOutputStream().write((hostport + " HTTP/1.1\r\nHost: "
		// + hostport + proxyLine + "\r\n\r\n").getBytes("UTF-8"));
		//
		// InputStream in = socket.getInputStream();
		// StringBuilder got = new StringBuilder(100);
		// int nlchars = 0;
		//
		// while (true)
		// {
		// char c = (char) in.read();
		// got.append(c);
		// if (got.length() > 1024)
		// {
		// /*throw new ProxyException(ProxyInfo.ProxyType.HTTP, "Recieved " +
		// "header of >1024 characters from "
		// + proxyhost + ", cancelling connection");*/
		// }
		// if (c == -1)
		// {
		// // throw new ProxyException(ProxyInfo.ProxyType.HTTP);
		// }
		// if ((nlchars == 0 || nlchars == 2) && c == '\r')
		// {
		// nlchars++;
		// }
		// else if ((nlchars == 1 || nlchars == 3) && c == '\n')
		// {
		// nlchars++;
		// }
		// else
		// {
		// nlchars = 0;
		// }
		// if (nlchars == 4)
		// {
		// break;
		// }
		// }
		//
		// if (nlchars != 4)
		// {
		// /*throw new ProxyException(ProxyInfo.ProxyType.HTTP, "Never " +
		// "received blank line from "
		// + proxyhost + ", cancelling connection");*/
		// }
		//
		// String gotstr = got.toString();
		//
		// BufferedReader br = new BufferedReader(new StringReader(gotstr));
		// String response = br.readLine();
		//
		// if (response == null)
		// {
		// /*throw new ProxyException(ProxyInfo.ProxyType.HTTP, "Empty proxy " +
		// "response from " + proxyhost + ", cancelling");*/
		// }
		//
		// Matcher m = RESPONSE_PATTERN.matcher(response);
		// if (!m.matches())
		// {
		// /*throw new ProxyException(ProxyInfo.ProxyType.HTTP , "Unexpected " +
		// "proxy response from " + proxyhost + ": " + response);*/
		// }
		//
		// int code = Integer.parseInt(m.group(1));
		//
		// if (code != HttpURLConnection.HTTP_OK)
		// {
		// //throw new ProxyException(ProxyInfo.ProxyType.HTTP);
		// }
	        
	        return socket;
	    }

	@Override
	public Socket createSocket(InetAddress arg0, int arg1) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3)
			throws IOException, UnknownHostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2,
			int arg3) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
