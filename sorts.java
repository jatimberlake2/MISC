public class sorts {

	public static void main(String[] args) {
		int[] list = {12251925, 8012000, 6041968, 6042006, 5261986, 3121968, 3041948, 3041968, 3041968, 1021970};
		int[] list2 = {9,8,7,6,5,4,3,2,1,0};
		for (int i = 0; i < list.length; i++) {
			System.out.print(list[i] + " ");
		}
		System.out.println();
		System.out.println();
		
		//InsertionSort(list);
		//QuickSort(list, 0, list.length - 1);
		//SelectionSort(list);
		//BubbleSort(list);
		list2  = CountingSort(list,12251925);
		//list = RadixSort(list);
		
		for (int i = 0; i < list2.length; i++) {
			System.out.print(list2[i] + " ");
		}
		
	}
	
	public static int[] InsertionSort(int[] list) {
		for (int j = 1; j < list.length; j++) {
			int key = list[j];
			int i = j - 1;
			while (i >= 0 && list[i] >key) {
				list[i + 1] = list[i];
				i--;
			}
			list[i+1] = key;
		}
		return list;
	}

	public static int[] QuickSort(int[] list, int low, int high) {
		if (low < high) {
			int pivot_loc = Partition(list, low, high);
			QuickSort(list, low, pivot_loc - 1);
			QuickSort(list, pivot_loc + 1, high);
		}
		return list;
	}
	public static int Partition(int[] list, int low, int high) {
		int pivot = list[high];
		int left = low - 1;
		for (int i = low; i < high; i++) {
			if (list[i] <= pivot) {
				left++;
				int temp = list[i];		//swap list[i] with list[left]
				list[i] = list[left];
				list[left] = temp;
			}
		}
		int temp2 = list[high];		//swap list[i] with list[left]
		list[high] = list[left + 1];
		list[left + 1] = temp2;
		return left + 1;
	}

	public static int[] SelectionSort(int[] list) {
		for (int i = 0; i < list.length; i++) {
			int smallest = i;
			for (int j = i + 1; j < list.length; j++) {
				if (list[j] < list[smallest])
					smallest = j;
			}
			int temp = list[i];
			list[i] = list[smallest];
			list[smallest] = temp;
		}
		return list;
	}
	
	public static int[] BubbleSort(int[] list) {
		for (int i = 0; i < list.length - 1; i++) {
			for (int j = 0; j < list.length - 1; j++) {
				if (list[j] > list[j+1]) {
					int temp = list[j];
					list[j] = list[j + 1];
					list[j + 1] = temp;
				}
			}
		}
		return list;
	}

	private static int[] CountingSort(int[] a, int k) {	
        int[] b = new int[a.length];
	   	int[] c = new int[k+1];
		for (int j = 0; j < a.length; j++){
			c[a[j]] = c[a[j]] + 1;
		}
		for (int i = 1; i <= k; i++){
			c[i] = c[i] + c[i - 1];
		}
		for (int j = a.length - 1; j >= 0; j--){

			b[c[a[j]] - 1] = a[j];
			c[a[j]] = c[a[j]] - 1;
		}
		return b;
    }

    public static int[] RadixSort(int[] input){
        // Largest place for a 32-bit int is the 1 billion's place
        for(int place=1; place <= 12312010; place *= 10){
            // Use counting sort at each digit's place
            input = countingSort(input, place);
        }

        return input;
    }
	
	
    private static int[] countingSort(int[] input, int place){
       
    	int[] out = new int[input.length];
        int[] count = new int[10];

        for(int i=0; i < input.length; i++){
            int digit = getDigit(input[i], place);
            count[digit] += 1;
        }

        for(int i=1; i < count.length; i++){
            count[i] += count[i-1];
        }

        for(int i = input.length-1; i >= 0; i--){
            int digit = getDigit(input[i], place);

            out[count[digit]-1] = input[i];
            count[digit]--;        
        }

        return out;
    }

    private static int getDigit(int value, int digitPlace){
        return ((value/digitPlace ) % 10);
    }
	

}


