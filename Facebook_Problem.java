public class Facebook_Problem {
	public static void main(String[] args) {
		double BangHead = 0, TapLeftFoot = 0, TapRightFoot = 0;	
		for (int iCnt = 0; iCnt < 9000; iCnt++)
		{
		if (iCnt % 3 == 0) {
		BangHead++;
			}
		else {
		if(iCnt % 2 == 0) {
		TapLeftFoot++;
			}
		else {
		TapRightFoot++;
			}
		}
	}

		System.out.println("Left! :" + (int) TapLeftFoot);
		System.out.println("Right! :" +(int) TapRightFoot);	
		System.out.println("Now, bang your head " + (int) BangHead + " times!");

	}
}