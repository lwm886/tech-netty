package com.tech.netty.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author lw
 * @since 2021/9/6
 */
public class HttpUtils {
    public static void main(String[] args) throws Exception {
        URL url=new URL("https://www.baidu.com/link?url=YDeAjPiECysgL1MQdqxLFVqphKvBIpMyvnWtJkvJtusgje2yj7LCx_-Ns3nO1jOUzRgzq1m5a8fzg9-gUtdEEAHDLU7sD1PDwUe8ewZ2LyiU7AUSXr2AhQyGwwiClv1aJJ28zLsTxigdj9pAm8emkhf76ctPY36QEEHVo4UJPakPavD0atZ6R7Uxdvyk2rPpgPd0yuruLuFMt7BzwplXM6SY0GK-YciYieyxxs4RyhEH5-3XJ4xPAZIYHO1cHoZ_rVVmkKHGfUXWvHMacCiNFSfqlSH-wNUxAPpPsOUhQohcw3xWhvYyCUlzhdFDCnksnsIqKz4fxMDfGYFX9cPvwf9tLj5Wsrqlthl-1Ci6dJkREvwNAjkvQ99sV0aOOEWWxKojZ-h5rD6IAlUkRhGWuMtfRlT3mXS9yYLS-TPaiMmO90d08E_pb0ChUQczt6bveZVCv2VbHq6BnWmTRnUOHK1pIYJmPvIU07Ra2G3yqRwbcKQtI2jBaIcvPXeu01DXiY_PLHqAPOIHCD2XH4-TNwjQ797PrWnNRUP7AsZBXZJ4jPw9LGB_qQy1ScnF1AXLyiaG2z7MSDptgaCbJZPdQMLx9L7BDz1Xeo-qteUsC_PMy2q0_S4IAzjJFm5Sbs4Ll12fmF3UtGWNuFspX761Ys6fOiqTh3xs_09iF-" +
                "dQ5oK&wd=&eqid=af751a800001a8630000000261357c39");
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(1000*30);
        conn.setReadTimeout(1000*60*10);
        InputStream inStream=conn.getInputStream();//获取输出流
        byte[] data=readInputStream(inStream);

        File file=new File("D://","Logo.jpg");
        FileOutputStream outStream=new FileOutputStream(file);
        outStream.write(data);
        outStream.close();
        System.out.println("ok");
    }
    //readInputStream方法--------------------------------------------------
    private static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];//转换为二进制
        int len=0;
        while((len =inStream.read(buffer))!=-1){
            outStream.write(buffer,0,len);
        }
        return  outStream.toByteArray();
    }

}
