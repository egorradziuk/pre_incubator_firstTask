import java.util.concurrent.locks.Lock;

public class MyThread extends Thread {

    CommonResource res;
    Lock locker;

    MyThread(CommonResource res, Lock locker){
        this.res = res;
        this.locker = locker;
    }

    public void run(){
        locker.lock();
        System.out.println(Thread.currentThread().getName() + " started.");
        if (Validation.isCorrect(res.readFromFile().toString().toCharArray())) {
            System.out.println("Your string is correct.");
        } else {
            System.out.println("Your string isn't correct.");
        }
        System.out.println(Thread.currentThread().getName() + " finished.");
        locker.unlock();
    }

}



