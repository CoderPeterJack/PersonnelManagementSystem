package com.jp.vhr_2020;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @program: vhr_2020
 * @description: Java异常练习
 * @author: CoderPengJiang
 * @create: 2020-02-24 16:09
 **/
public class ExceptionTest {
    public static void main(String[] args) {
//        一、使用 try 和 catch 关键字可以捕获异常。
//        try/catch 代码块放在异常可能发生的地方。
//        try/catch代码块中的代码称为保护代码，使用
//        try/catch 的语法如下：
//        try {
////            int[] a=new int[2];
////            System.out.println("Access element three:"+a[3]);
////        }catch (ArrayIndexOutOfBoundsException e) {
////            System.out.println("Exception thrown:"+e);
////        }
////        System.out.println("Out of the block");

//        二、多重捕获块
//        一个 try 代码块后面跟随多个 catch 代码块的情况就叫多重捕获。
//        多重捕获块的语法如下所示：

        //三、throws/throw 关键字：
        //如果一个方法没有捕获到一个检查性异常，那么该方法必须使用 throws 关键字来声明。throws 关键字放在方法签名的尾部。
        //也可以使用 throw 关键字抛出一个异常，无论它是新实例化的还是刚捕获到的。
        //下面方法的声明抛出一个 RemoteException 异常：

//        四、finally关键字
//finally 关键字用来创建在 try 代码块后面执行的代码块。
//        无论是否发生异常，finally 代码块中的代码总会被执行。
//        在 finally 代码块中，可以运行清理类型等收尾善后性质的语句。
//finally 代码块出现在 catch 代码块最后，语法如下
        //注意下面事项：
        //catch 不能独立于 try 存在。
        //在 try/catch 后面添加 finally 块并非强制性要求的。
        //try 代码后不能既没 catch 块也没 finally 块。
        //try, catch, finally 块之间不能添加任何代码。
//        int[] a=new int[2];
//        try {
//           System.out.println("Access element three:"+a[3]);
//       }catch (ArrayIndexOutOfBoundsException e) {
//           System.out.println("Exception thrown:"+e);
//       }
//        finally {
//            a[0]=6;
//            System.out.println("First element value: " +a[0]);
//            System.out.println("The finally statement is executed");
//        }

        //五、声明自定义异常
        //在 Java 中你可以自定义异常。编写自己的异常类时需要记住下面的几点。
        //所有异常都必须是 Throwable 的子类。
        //如果希望写一个检查性异常类，则需要继承 Exception 类。
        //如果你想写一个运行时异常类，那么需要继承 RuntimeException 类。

//        下面的 BankDemo 程序示范了如何调用
//        CheckingAccount 类的 deposit() 和
//        withdraw() 方法。
//        CheckingAccount c = new CheckingAccount(101);
//        System.out.println("Depositing $500...");
//        c.deposit(500.00);
//        try
//        {
//            System.out.println("\nWithdrawing $100...");
//            c.withdraw(100.00);
//            System.out.println("\nWithdrawing $10000...");
//            c.withdraw(10000.00);
//        }catch(InsufficientFundsException e)
//        {
//            System.out.println("Sorry, but you are short $"
//                    + e.getAmount());
//            e.printStackTrace();
//        }
        
    }
}

//五、声明自定义异常
//在 Java 中你可以自定义异常。编写自己的异常类时需要记住下面的几点。
//所有异常都必须是 Throwable 的子类。
//如果希望写一个检查性异常类，则需要继承 Exception 类。
//如果你想写一个运行时异常类，那么需要继承 RuntimeException 类。
class InsufficientFundsException extends Exception{
    //此处的amount用来储存当出现异常（取出钱多于余额时）所缺乏的钱
    private double amount;
    public InsufficientFundsException(double amount)
    {
        this.amount = amount;
    }
    public double getAmount()
    {
        return amount;
    }
}

//    在下面的 CheckingAccount 类中
//    包含一个 withdraw() 方法抛出一个
//    InsufficientFundsException 异常

class CheckingAccount{
    //balance为余额，number为卡号
    private double balance;
    private int number;
    public CheckingAccount(int number)
    {
        this.number = number;
    }
    //方法：存钱
    public void deposit(double amount)
    {
        balance += amount;
    }
    //方法：取钱
    public void withdraw(double amount) throws
            InsufficientFundsException
    {
        if(amount <= balance)
        {
            balance -= amount;
        }
        else
        {
            double needs = amount - balance;
            throw new InsufficientFundsException(needs);
        }
    }
    //方法：返回余额
    public double getBalance()
    {
        return balance;
    }
    //方法：返回卡号
    public int getNumber()
    {
        return number;
    }
}
