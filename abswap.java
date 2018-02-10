
public class abswap {

	public static void main(String[] args) {
		int a, b;
		a=3; b=6;
		//b ^= a;
		//a ^= a;
		b ^= (a ^= b);
		a ^= b;
		System.out.println(a+" "+b);
	}

}
