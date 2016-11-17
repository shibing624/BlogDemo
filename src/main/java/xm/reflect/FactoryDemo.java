    package xm.reflect;

    /**
     * Created by xuming on 2016/7/21.
     */

    interface Fruit {
        void eat();
    }

    class Apple implements Fruit {
        public void eat() {
            System.out.println("吃苹果。");
        }

    }

    class Orange implements Fruit {
        public void eat() {
            System.out.println("吃橘子。");
        }

    }

    class Factory {
        public static Fruit getInstance(String className) {
            Fruit f = null;
            try {
                f = (Fruit) Class.forName(className).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return f;
        }
    }

    public class FactoryDemo {
        public static void main(String[] args) {
            Fruit f = Factory.getInstance("com.xm.reflect.Orange");
            f.eat();
        }
    }

