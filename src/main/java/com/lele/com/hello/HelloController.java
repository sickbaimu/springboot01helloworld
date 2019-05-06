package com.lele.com.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class HelloController {
    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
    @ResponseBody
    @RequestMapping("/upload")
    public String upload(String id, String base64) {
        String folder_path = "C:\\Users\\Administrator\\Desktop\\training_faces\\"+id;
        File folder = new File(folder_path);
        if (!folder.exists()) {
                 folder.mkdir();
        }
        boolean result  = GenerateImage(base64,folder_path + "\\"+df.format(new Date())+".jpg");
        if(result)
            return "OK";
        return "Error";
    }

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(String base64) {
        boolean result = GenerateImage(base64,"C:\\Users\\Administrator\\Desktop\\temp.jpg");
        if(result)
            return readpython();
        return "Error";
    }

    private String readpython() {
        String id = "";
        try {
            String[] args1 = new String[]{"python", "D:\\PycharmProjects\\Clip\\test_model.py"};
            Process pr = Runtime.getRuntime().exec(args1);
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                id += line;
            }
            in.close();
            pr.waitFor();
            System.out.println("识别完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(id);
        return id;
    }

    //base64字符串转化成图片
    private static boolean GenerateImage(String imgStr,String newPath)
    {
        if (imgStr == null) //图像数据为空
            return false;
        imgStr = imgStr.replace(' ','+');
        System.out.println("接受到base64码");
        String s = "";
        for(int i = 0;i<imgStr.length();i++){
            s += imgStr.charAt(i);
            if((i+1)%76==0)
                s += "\r\n";
        }

        //对字节数组字符串进行Base64解码并生成图片
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(newPath);
            System.out.println("解码完毕");
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    //图片转化成base64字符串
    public static String GetImageStr()
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = "C:\\Users\\Administrator\\Desktop\\ex.jpg";//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }
}








