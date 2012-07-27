package test;

/**
 * 类加载顺序
 * 
 * @Author: Ryan
 * 
 * 2012-7-28
 */
public class Admin {

    public static void main(String... args) {
        new Father();
        System.out.println("--------------");
        Child son = new Child();
        son.toString();
        System.out.println("--------------");
        System.out.println(Child.B);
    }
}

class Father {
	
	public static int A = 1;
	
    static {
    	A=2;
        System.out.println("父类静态块");
    }
    
    {
    	System.out.println("父类代码块");
    }
    
    public Father() {
        System.out.println("父类构造方法");
    }
}

class Child extends Father {
	public static int B = A; 
	
    static {
        System.out.println("子类静态块");
    }

    {
    	System.out.println("子类代码块");
    }
    
    public Child() {
        System.out.println("子类构造方法");
    }
}
