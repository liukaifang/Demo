package at.home.thread;

public class TraditionalThreadCommunication {

    /**
     * @param args
     */
    public static void main(String[] args) {

        final Business business = new Business();
        new Thread(new Runnable() {

            @Override
            public void run() {

                for (int i = 1; i <= 50; i++) {
                    business.sub(i);
                }

            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {

                for (int i = 1; i <= 50; i++) {
                    business.main(i);
                }

            }
        }).start();
        // for (int i = 1; i <= 50; i++) {
        // business.main(i);
        // }
        new Thread(new Runnable() {

            @Override
            public void run() {

                for (int i = 1; i <= 50; i++) {
                    business.main(i);
                }

            }
        }).start();
    }

}

class Business {
    private boolean bShouldSub = true;

    public synchronized void sub(int i) {
        while (!bShouldSub) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("sub thread sequence of " + i);
        bShouldSub = false;
        this.notify();
    }

    public synchronized void main(int i) {
        while (bShouldSub) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("main thread sequence of " + i);
        bShouldSub = true;
        this.notify();
    }
}
