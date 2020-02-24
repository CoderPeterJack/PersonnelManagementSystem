package com.jp.vhr_2020;
import	java.util.Scanner;

import net.minidev.json.JSONUtil;

import java.util.Scanner;

/**
 * @program: vhr_2020
 * @description:
 * @author: CoderPengJiang
 * @create: 2020-02-24 14:57
 **/
public class JavaScannerTest {
    public static void main(String[] args) {
        //    一、使用 next 方法：
//        //从键盘接收数据
//        Scanner scanner=new Scanner(System.in);
//        // next方式接收字符串
//        System.out.println("next方式接收：");
//        //判断是否有输入
//        if (scanner.hasNext()){
//            String str1= scanner.next();
//            System.out.println("输入的数据为："+str1);
//        }
//        scanner.close();
//    }

        //二、使用 nextLine 方法：
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("nextLine方式接收：");
//        //判断是否还有数据
//        if (scanner.hasNext()) {
//            String str2 = scanner.nextLine();
//            System.out.println("输入的数据为：" + str2);
//        }
//        scanner.close();

//        三、next() 与 nextLine() 区别
//        next():
//        1、一定要读取到有效字符后才可以结束输入。
//        2、对输入有效字符之前遇到的空白，next() 方法会自动将其去掉。
//        3、只有输入有效字符后才将其后面输入的空白作为分隔符或者结束符。
//        next() 不能得到带有空格的字符串。
//        nextLine()：
//        1、以Enter为结束符,也就是说 nextLine()方法返回的是输入回车之前的所有字符。
//        2、可以获得空白。

        //四、int或者float
//        如果要输入 int 或 float 类型的数据，
//        在 Scanner 类中也有支持，但是在输入
//        之前最好先使用 hasNextXxx() 方法进行
//        验证，再使用 nextXxx() 来读取：
//        Scanner scan = new Scanner(System.in);
//        // 从键盘接收数据
//        int i = 0;
//        float f = 0.0f;
//        System.out.print("输入整数：");
//        if (scan.hasNextInt()) {
//            // 判断输入的是否是整数
//            i = scan.nextInt();
//            // 接收整数
//            System.out.println("整数数据：" + i);
//        } else {
//            // 输入错误的信息
//            System.out.println("输入的不是整数！");
//        }
//        System.out.print("输入小数：");
//        if (scan.hasNextFloat()) {
//            // 判断输入的是否是小数
//            f = scan.nextFloat();
//            // 接收小数
//            System.out.println("小数数据：" + f);
//        } else {
//            // 输入错误的信息
//            System.out.println("输入的不是小数！");
//        }
//        scan.close();

//        五、以下实例我们可以输入
//        多个数字，并求其总和与平均数，
//        每输入一个数字用回车确认，通
        Scanner scan = new Scanner(System.in);

        double sum = 0;
        int m = 0;

        while (scan.hasNextDouble()) {
            double x = scan.nextDouble();
            m = m + 1;
            sum = sum + x;
        }

        System.out.println(m + "个数的和为" + sum);
        System.out.println(m + "个数的平均值是" + (sum / m));
        scan.close();
    }
}
