public class FizzBuzz 
{
	public static void main(String[] args) 
		{
		
			String str = "";
			for (int i=1; i<101; i++)
			{
				str = i + " ";
				
				if (i%3 == 0)
					str += "Fizz";
				
				if (i%5==0)
					str+="Buzz";
				
				System.out.println(str);
			}
			
		}

}
