public class Queens {
	private static int size = 8;
	
	public static void main(String[] args) {
		Queens q = new Queens();
		System.out.println("All possible cases for board " + size + " * " + size + ": ");
		q.setQueens(size);
	}
	
	public void setQueens(int n) {
		int[] coordsY = getIndexArray(n);
		for(int i = 0; i < n; i++) coordsY[i] = i;
		int count = 0;		
		for(int i = 0; i < factor(n); i++) {
			boolean mark = true;
			for(int j = 0; j < n - 1; j++) {
				for(int k = j + 1; k < n; k++){
					if(k - j == Math.abs(coordsY[j] - coordsY[k])) {
						mark = false;
					}
				}
			}
			
			if(mark) {
				System.out.println(++count + ")");
				String[][] board = new String[n][n];
				for(int index = 0; index < n; index++) {
					board[coordsY[index]][index] = "Q|";
				}
				
				for(int y = 0; y < n; y++) {
					for(int x = 0; x < n; x++) {
						if(board[y][x] == null) board[y][x] = " |";
					}
				}
				
				for(String[] line : board) {
					System.out.print("|");
					for(String cell : line) {
						System.out.print(cell);
					}
					System.out.println();
				}
				System.out.println();
			}
			coordsY = swapNext(coordsY);
		}
	}
		
	public int[] swapNext(int[] a) {
		int i = a.length - 2;
		while(i >= 0) {
			if(a[i] < a[i + 1]) break;
			i--;
		}
		
		if(i >= 0) {
			int j = a.length - 1;
			while(j > i) {
				if(a[j] > a[i]) break;
				j--;
			}
			int buf = a[i];
			a[i] = a[j];
			a[j] = buf;
			for(int k = 0; k < (a.length - 1 - i) / 2; k++) {
				buf = a[i + 1 + k];
				a[i + 1 + k] = a[a.length - 1 - k];
				a[a.length - 1 - k] = buf;
			}
		}
		return a;
	}
	
	public int factor(int n) {
        int f = 1;
        for (int i = 2; i <= n; i++) f *= i;
        return f;
    }
	
	public int[] getIndexArray(int n) {
		int[] index = new int[n];
		for(int i = 0; i < n; i++) index[i] = i;
		return index;
	}
}
