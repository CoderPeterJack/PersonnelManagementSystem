package com.jp.vhr_2020;
import java.io.*;

import java.nio.Buffer;

/**
 * @program: vhr_2020
 * @description:
 * @author: CoderPengJiang
 * @create: 2020-02-24 10:33
 **/
public class BufferReaderTest {
    public static void main(String[] args) throws IOException {
//一、字符流输出输出
        //1、从控制台读取多字符输入
        //        char c;
//        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("输入字符，按下'q'键退出。");
//        //读取字符
//        do {
//            c = (char) br.read();
//            System.out.println(c);
//        }while (c !='q');

        //2、从控制台读取字符串
//        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
////        String str;
////        System.out.println("输入读取的字符串");
////        System.out.println("输入结束的字符串'end'");
////        do {
////            str = br.readLine();
////            System.out.println(str);
////        }while (!str.equals("end"));

        //3、控制台输出
//        int b;
//        b='A';
//        System.out.write(b);
//        System.out.println('\n');

        //一、字节流输出输出InputStream 和 OutputStream
        //由于是二进制写入，可能存在乱码，
//        try {
//            byte[] bWrite = {11,21,3,40,5 };
//            OutputStream os = new FileOutputStream("test.text");
//            for (int i = 0; i < bWrite.length;i++){
//                os.write(bWrite[i]);
//            }
//            os.close();
//
//            InputStream is=new FileInputStream("test.txt");
//            int size=is.available();
//
//            for (int i = 0; i < size; i++) {
//                System.out.print((char) is.read()+" ");
//            }
//            is.close();
//        }catch (IOException e) {
//            System.out.println("Exception");
//        }

        //一、字节流输出输出InputStream 和 OutputStream
        //解决存在乱码，
        //创建文件
//        File f= new File("a.txt");
//        // 构建FileOutputStream对象,文件不存在会自动新建
//        FileOutputStream fop=new FileOutputStream(f);
//
//// 构建OutputStreamWriter对象,参数可以指定编码,默认为操作系统默认编码,windows上是gbk
//        OutputStreamWriter writer = new OutputStreamWriter(fop,"UTF-8");
//
//        // 写入到缓冲区
//        writer.append("中文输入");
//        // 换行
//        writer.append("\r\n");
//        // 刷新缓存冲,写入到文件,如果下面已经没有写入的内容了,直接close也会写入
//        writer.append("English");
//        // 关闭写入流,同时会把缓冲区内容写入文件
//        writer.close();
//        // 关闭输出流,释放系统资源
//        fop.close();
//
//        FileInputStream fip=new FileInputStream(f);
//
//        InputStreamReader reader = new InputStreamReader(fip,"UTF-8");
//
//        StringBuffer sb = new StringBuffer();
//        while (reader.ready()){
//            sb.append((char) reader.read());
//
//        }
//        System.out.println(sb.toString());
//        reader.close();
//        fip.close();

        //二、文件和I/O
        //还有一些关于文件和I/O的类，我们也需要知道：
        //File Class(类)
        //FileReader Class(类)
        //FileWriter Class(类)
        //1、创建目录：
//        String dirname="/tmp/user/java/bin";
//        File d=new File(dirname);
//        //现在创建目录
//        d.mkdirs();

        //2、读取目录
//        String dirname="/tmp";
//        File f1=new File(dirname);
//        if (f1.isDirectory()){
//            System.out.println("目录"+dirname);
//            String[] s=f1.list();
//            for (int i = 0; i < s.length;i++){
//                File f=new File(dirname+"/"+s[i]);
//                if (f.isDirectory()){
//                    System.out.println(s[i]+"是一个目录");
//                }else {
//                    System.out.println(s[i]+"是一个文件");
//                }
//            }
//        }else {
//            System.out.println(dirname+"不是一个目录");
//        }

//        3、删除目录或文件
        File folder=new File("/tmp");
        deleteFolder(folder);
    }
    public static void deleteFolder(File folder){
        File[] files=folder.listFiles();
        if (files!=null){
            for (File file : files) {
                if (file.isDirectory()){
                    deleteFolder(file);
                }else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }
}
