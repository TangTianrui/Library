package Package;

import java.util.Scanner;

/**
 * @author TangTianrui
 * @create 2020-05-28-22:05
 */
public class MainLibrary {
    public static void main(String[] args) {
        Library L=new Library(true,2);//初始化图书馆，并且先执行一次还书；
        ReturnBook Return = new ReturnBook(L);//初始化ReturnBook对象，并且将图书馆对象赋值给它；
        RentBook rent = new RentBook(L);//初始化RentBook对象，并且将图书馆对象赋值给它；
        Thread returnbook = new Thread(Return);//创建线程returnbook
        Thread rentbook = new Thread(rent);//创建线程rentbook
        rentbook.start();//线程returnbook启动
        returnbook.start();//线程rentbook启动
    }
}
