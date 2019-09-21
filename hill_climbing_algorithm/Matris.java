import java.util.ArrayList;
import java.util.Random;

public class Matris {

	int[][] matrix = new int[5][9];
	//int [] sayilar = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
	ArrayList<Integer> arr = new ArrayList<>();
	
	int sa = 0;
	public int[][] getMatris()
	{
		//RandomSayi rndm = new RandomSayi();
		//rndm.list();
		
		for(int i=1;i<20;i++)
		{
			arr.add(i);
		}
		
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<9;j++)
			{
				if(((i==0)||(i==4))&&((j==0)||(j==1)||(j==3)||(j==5)||(j==7)||(j==8)))
				{
					matrix[i][j] = 00;
				}
				else if(((i==1)||(i==3))&&((j==0)||(j==2)||(j==4)||(j==6)||(j==8)))
				{
					matrix[i][j] = 00 ;
				}
				else if((i==2)&&((j==1)||(j==3)||(j==5)||(j==7)))
				{
					matrix[i][j] = 00 ;
				}
				else
				{
					//matrix[i][j] = rndm.getSayi();
					Random rnd = new Random();
					int sayi1 = Math.abs(rnd.nextInt())%(arr.size());
					matrix[i][j] = arr.get(sayi1);
					arr.remove(sayi1);
					//System.out.println(matrix[i][j] );
				}
			}
		}
		
		
		return matrix;
	}

}
