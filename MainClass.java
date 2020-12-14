import java.util.Scanner;

public class MainClass{ 

  public static void main(String args[]){

  Scanner _key = new Scanner(System.in);
	double  a;
	double  b;
	double  c;
	double  d;
	double  e;
	String  t1;
	a= _key.nextDouble();
	b= _key.nextDouble();
	a = 1+2*3/b;
	if (a>b) {
		System.out.println(a);
	}
	else {
		System.out.println(b);
	}

	c = 3;
	d = 1;
	while(c>d){
		System.out.println(d);
		d = d+1;
	}

	}
}