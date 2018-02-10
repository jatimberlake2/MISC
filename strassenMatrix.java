class strassenMatrix2 {

	private int adds, mults;
	private int[][] matrix;
	
	strassenMatrix2(){
		adds = 0;
		mults = 0;
		matrix = new int[0][0];
	}
	
	strassenMatrix2(int[][] C, int add, int mult){
		matrix = C;
		adds = add;
		mults = mult;
	}
	
	int[][] get_matrix(){
		return matrix;
	}
	
	
	int get_adds(){
		return adds;
	}
	
	int get_mults(){
		return mults;
	}
	
	void increment_adds(){
		adds++;
	}
	
	void increment_mults(){
		mults++;
	}
	
	void set_matrix(int[][] C){
		matrix = C;
	}

}
