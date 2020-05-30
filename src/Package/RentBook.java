package Package;

import java.util.Scanner;

/**
 * @author TangTianrui
 * @create 2020-05-29-23:11
 */
public class RentBook implements Runnable {
    Library L;
    Scanner sc = new Scanner(System.in);//输入的方式；
    int flog;//临时借还书标志
    RentBook(Library L) {
        this.L=L;//构造函数，将Library对象代入
    }
    public void run() {
        //循环，在图书馆运行标志为true时，两个线程总是运行
        while (Library.flag) {
            //同步代码块，保证借书和还书总是分别进行，不会造成影响
            synchronized (L) {
                //当借还书标志表现为还书时，线程等待
                if (Library.flog == 2) {
                    try {
                        L.wait();
                    } catch (InterruptedException e) {
                    }
                }
                if(Library.flag == false)//如果图书馆运行标志为false，循环结束，线程结束；
                    break;
                //如果图书馆中图书没有了，则等待还书，或者退出，根据用户的反应，决定将借还书标志转换为还书还是结束线程；
                if (Library.book == 0) {
                    System.out.println("图书馆中没有图书了，暂时无法借书！");
                    do {
                        System.out.println("您需要还书吗？(输入2进行还书，输入0退出)");
                        flog = sc.nextInt();//接收用户的反馈
                    }while(flog!=0&&flog!=2);
                        //如果用户返回0，则结束线程
                        if (flog == 0) {
                            Library.flag = false;
                            L.notifyAll();
                        }
                        //返回2，将借还书标志转换为还书
                        else {
                            Library.flog = flog;
                        }
                }
                //如果图书还有，则完成借书操作；
                else {
                    Library.book--;
                    System.out.println("借书成功！");
                    System.out.println("当前图书馆中剩余书本数量：" + Library.book);
                    System.out.println("您还需要借书或者还书吗？(1.借书/2.还书/0.退出)" );
                    flog=sc.nextInt();//接收用户的反馈
                    //如果用户返回0，则结束线程
                    if(flog==0){
                        Library.flag = false;
                        L.notifyAll();
                    }
                    //返回1/2，将借还书标志转换为借书/还书
                    else {
                        Library.flog = flog;
                    }
                    }
                L.notify();//线程唤醒
                }
            }
        }
    }
