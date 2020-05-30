package Package;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author TangTianrui
 * @create 2020-05-28-22:19
 */
public class Library {
    public static int book = 1;//初始化图书馆中原本书目的数量为1；
    static boolean flag=true;//初始化图书馆运行的标志；
    public static int flog=0;//初始化一个用于表明借书还是还书的标志；
    static int Max = 3;//设置图书馆中最多存放的书目数量为3；
    Library(boolean flag,int flog){
        //构造函数，给运行标志和借还书标志赋值；
        this.flag=flag;
        this.flog=flog;
    }
}