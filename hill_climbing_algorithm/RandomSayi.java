import java.util.ArrayList;
import java.util.Random; 
public class RandomSayi {
	
	int [] sayilar = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
	ArrayList<Integer> arr = new ArrayList<>();
	public int sayi ;
	ArrayList<Integer> secilen = new ArrayList<>();
	public void list()
	{
		for(int i=1;i<20;i++)
		{
			arr.add(i);
		}
	}
	public int getSayi()
	{
		Random rnd = new Random();
		boolean bulundu = true;
		while(bulundu == true)
		{
			System.out.println("while a girdi");
			sayi = Math.abs(rnd.nextInt())%(arr.size());
			bulundu = secilen.contains(arr.get(sayi));
			secilen.add(arr.get(sayi));	
		}
		arr.remove(sayi);
		return arr.get(sayi);
	}

}
