#简易双线程完成图书馆的借还书操作
##MainLibrry
* Main

完成了图书馆的初始化，以及借书和还书两个线程的创建和启动
```
public static void main(String[] args) {
        Library L=new Library(true,2);//初始化图书馆，并且先执行一次还书；
        ReturnBook Return = new ReturnBook(L);//初始化ReturnBook对象，并且将图书馆对象赋值给它；
        RentBook rent = new RentBook(L);//初始化RentBook对象，并且将图书馆对象赋值给它；
        Thread returnbook = new Thread(Return);//创建线程returnbook
        Thread rentbook = new Thread(rent);//创建线程rentbook
        rentbook.start();//线程returnbook启动
        returnbook.start();//线程rentbook启动
    }
```
###Library
* Library 

存储图书馆的book总数，图书馆的借还书标志，以及图书馆的状态；
```
public static int book = 1;//初始化图书馆中原本书目的数量为1；
    static boolean flag=true;//初始化图书馆运行的标志；
    public static int flog=0;//初始化一个用于表明借书还是还书的标志；
    static int Max = 3;//设置图书馆中最多存放的书目数量为3；
    
Library(boolean flag,int flog){
        //构造函数，给运行标志和借还书标志赋值；
        this.flag=flag;
        this.flog=flog;
    }
```
###RentBook
* 基本数据
````
 Library L;
    Scanner sc = new Scanner(System.in);//输入的方式；
    int flog;//临时借还书标志
    RentBook(Library L) {
        this.L=L;//构造函数，将Library对象代入
    }
````
* run() 实现了借书线程的操作

完成了同步线程的设置，使借书和还书不会同时进行，造成影响
```
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
```
当图书馆中没有书了，需要等待还书操作的执行，或者直接退出
```
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
```
图书馆中还有图书，正常执行借书的操作
```
                //如果图书还有，则完成借书操作；
                else {
                    Library.book--;
                    System.out.println("借书成功！");
                    System.out.println("当前图书馆中剩余书本数量：" + Library.book);
````
继续询问用户接下来的操作并转换到借还书标志中
````                    
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
````
###ReturnBook
* 基本数据
````
Library L;
    Scanner sc = new Scanner(System.in);//输入的方式；
    int flog;//临时借还书标志
    ReturnBook(Library L) {
        this.L=L;//构造函数，将Library对象代入
    }
````
* run() 实现了还书的操作
````
public void run() {
        //循环，在图书馆运行标志为true时，两个线程总是运行
        while (Library.flag) {
            //同步代码块，保证借书和还书总是分别进行，不会造成影响
            synchronized (L) {
                //当借还书标志表现为借书时，线程等待
                if (Library.flog == 1) {
                    try {
                        L.wait();
                    } catch (InterruptedException e) {
                    }
                }
````
如果图书馆中书已经满了，不用还书，直接结束线程
````
                //如果图书馆中图书是满的，则退出线程；
                if (Library.book == Library.Max) {
                    System.out.println("图书馆中库存已满，不需要还书！");
                    Library.flag = false;
                    L.notifyAll();
                } 
````
如果图书馆中，书不满，正常执行还书操作
````
                //如果图书不是满的，则完成还书操作；
                else {
                    Library.book++;
                    System.out.println("还书成功！");
                    System.out.println("当前图书馆中剩余书本数量：" + Library.book);
````   
继续询问用户接下来的操作并转换到借还书标志中
````                 
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
````