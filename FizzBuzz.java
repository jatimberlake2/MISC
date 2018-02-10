public class FizzBuzz 
{
	public static void main(String[] args) 
		{
			for (int i=1; i<101; i++)
			{				
				String str = i + "";
				if (i%3 == 0)
					str = "Fizz";
				if (i%5==0)
				{
					if (str.equals("Fizz"))
						str += "Buzz";
					else
						str = "Buzz";
				}
				System.out.println(str);
			}
		}
}
