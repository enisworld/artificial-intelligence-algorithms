import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random; 
import java.util.Scanner;
public class Main 
{
	public static ArrayList<Matris> olusturulan = new ArrayList<>();//parentlarýn bulundugu arraylist.
	public static ArrayList<Matris> olusturulan2 = new ArrayList<>();//childlarýn bulunduðu arraylist.
	public static ArrayList<Integer> iyilikm = new ArrayList<>();//child ve parentlarýn iyilik degelerinin bulundugu arraylist.
	public static ArrayList<YeniMtr> secimArray = new ArrayList<>();
	public static int  satir1,satir2,sutun1,sutun2;//rastgele degiþecek olan satýr ve sutun degerlerini tutuyorlar.
	static Matris [] gecici_dizi;
	static int derinlik = 20;//her bir parenttan kac adet child uretileceði bilgisini tutuyor.
	static int k;// Cluster sayisi. her bir adýmdaki parent sayisi.
	static int indexOfTheBest;// en son durumda  elde edilen matrislerden en iyi iyilik degerine sahip matrisin indeks bilgisini tutuyor.
	public static void main(String [] args)
	{
		 k = 5 ;
		System.out.println("Cluster degerini girin = ");
		Scanner veriAl = new Scanner(System.in);
		k = veriAl.nextInt();// k degerini oku.		
		  gecici_dizi = new Matris[k];  // Matris tipindeki nesneleri tutmek için kullanýlýyor.
		  int temp=0;
	//for(int s=0;s<100;s++)
	//{
		olusturulan.clear();
			  for(int cout=0; cout<k;cout++)
		{
			gecici_dizi[cout] = new Matris();
		}
		for(int i =0 ;i<k;i++)
		{
			gecici_dizi[i].getMatris();
			olusturulan.add(gecici_dizi[i]);
		}
		//int count=0;
		olusturulan2.clear();
		//System.out.println("ilk matrisler : ");
		/*while(count<olusturulan.size())
		{
			for(int i =0;i<5;i++)
			{
				for(int j=0;j<9;j++)
				{
					System.out.print("     " + olusturulan.get(count).matrix[i][j]);
				}
				System.out.println();
			}
			System.out.println();
			System.out.println();
			count++;
		}*/
		iyilik_Degeri_Hesapla(olusturulan);
		for(int a=0;a<iyilikm.size();a++)
		{
			System.out.println(iyilikm.get(a));
		}
		int iteration =0 ;
		
		
			while((iteration <=10000))
			{
				//System.out.println("iterasyon while " + iteration);
				olustur();
				iyilik_Degeri_Hesapla(olusturulan2);
				rastgeleSec();
				en_iyi_matris(olusturulan);
				iteration++;
			}
			en_iyi_matris(olusturulan);
			// yüzdeyi hesaplamak için kullanýlmaktadýr.
			//if(iyilikm.get(indexOfTheBest) == 0)
			//{
			//	temp++;
			//}
	//}
	System.out.println();
		//System.out.println("çozumun bulunma yuzdesi = " + temp/100);
		en_iyi_matris(olusturulan);
		System.out.println("en son matris");
		for(int i =0;i<5;i++)
		{
			for(int j=0;j<9;j++)
			{
				System.out.print("     " + olusturulan.get(indexOfTheBest).matrix[i][j]);
			}
			System.out.println();
		}
			
	}

	public static void rastgeleSec()//olusturulmus olan matrislerden  k tane seciyor bu metot
	{
		int iyilik_degeri ;//minimum iyilik degerini hesaplarken kullanýlan geçici degiþken.
		int row1= 0;//minimum iyilik degerine sahip olan matrisin arraylistteki indeks bilgisini tutmak için kullanýlýyor.
			ArrayList<Matris> all_in_one = new ArrayList<>();
			all_in_one.clear();
			//Asagýdaki iki satýrda parent matrisler ile child matristleri tek bir
			//arraylistte birleþtirmeye çalýþýlmýþtýr.
			all_in_one.addAll(olusturulan);
			all_in_one.addAll(olusturulan2);
			
			//parent ve child matrislerin iyilik degerleri hesaplanýyor.
		    int matris_sayisi = 0;
		    olusturulan.clear();
		    // K sayisý kadar yeni parent matrisler seciliyor.
			if(k>3)
			{
				while(matris_sayisi<k-2)
				{
					iyilik_Degeri_Hesapla(all_in_one);
					iyilik_degeri =200;
					for(int j=0; j<iyilikm.size();j++)
					{
						if(iyilik_degeri>iyilikm.get(j))
						{
							iyilik_degeri = iyilikm.get(j);
							row1 = j;
						}
					}
					Matris mrt34 = all_in_one.get(row1);
					olusturulan.add(mrt34);
					all_in_one.remove(row1);
					matris_sayisi++;
				}
				//yeni parentlardan rastgele iki tane üretilmesini saglamak için yapýlmýþtýr.
				for(int i = 0; i<2 ; i++)
				{
					Random rnd = new Random();
					int sayi = Math.abs(rnd.nextInt())%all_in_one.size();
						 Matris mrt2 = all_in_one.get(sayi);
							olusturulan.add(mrt2);
				}
			}
			else
			{
				while(matris_sayisi<k)
				{
					iyilik_degeri =200;
					for(int j=0; j<iyilikm.size();j++)
					{
						if(iyilik_degeri>iyilikm.get(j))
						{
							iyilik_degeri = iyilikm.get(j);
							row1 = j;
						}
					}
					Matris mrt = all_in_one.get(row1);
					olusturulan.add(mrt);
					all_in_one.remove(row1);
					matris_sayisi++;
				}
			}
			iyilik_Degeri_Hesapla(olusturulan);
			int ortalama = 0;
			for(int a=0;a<iyilikm.size();a++)
			{
				ortalama = ortalama + iyilikm.get(a);
			}
			ortalama = ortalama/iyilikm.size();
			System.out.println("ortalama  = " + ortalama);
			//System.out.println("bir sonraki iterasyondaki olusturulan matris");
			/*for(int i = 0; i<2 ; i++)
			{
				Random rnd = new Random();
				int sayi = Math.abs(rnd.nextInt())%olusturulan2.size();
					 sayi = rnd.nextInt()%all_in_one.size();
				olusturulan.add(all_in_one.get(sayi));
			}*/
	}
	// secilmiþ olan matrislerden rastgele bazý elemanlarýnýn 
	//yerin degiþtirerek olusturulan2 arraylisti yaratýlýyor.
	public static void olustur()
	{   
		int deger,deger2;
		olusturulan2.clear();
		int say=0;
		for(int i=0; i<olusturulan.size();i++)
		{
			for(int j =0; j<derinlik; j++)
			{
				secim();
				Matris m =new Matris();
				olusturulan2.add(m);
				for(int g=0;g<5;g++)
				{
					for(int h =0;h<9;h++)
					{
						olusturulan2.get(say).matrix[g][h] = olusturulan.get(i).matrix[g][h];
					}
				}
				deger = olusturulan2.get(say).matrix[satir1][sutun1];
				deger2 = olusturulan2.get(say).matrix[satir2][sutun2];
				olusturulan2.get(say).matrix[satir1][sutun1] = deger2 ;
				olusturulan2.get(say).matrix[satir2][sutun2] = deger;
				say++;
			}
		}
		
	}

	//Bu fonksiyon o andaki minimum iyilik degerini hesaplamaya yarýyor.
	public static void en_iyi_matris(ArrayList<Matris> matrisBul)
	{
		int yakinlik =300;
		int row2=0;
		for(int j=0; j<iyilikm.size();j++)
		{
			if(yakinlik>iyilikm.get(j))
			{
				yakinlik = iyilikm.get(j);
				row2 = j;
			}
		}
		System.out.println("EN ÝYÝ  iyilik degeri = " + yakinlik);
		indexOfTheBest = row2;
	}
	// Matriste rastgele degiþecek olan iki gözün hangileri oldugunu buluyor.
	public static void  secim()
	{
		Random rnd = new Random();
		secimArraylistim();
		int rndsayi1 = Math.abs(rnd.nextInt())%(secimArray.size());
		satir1 = secimArray.get(rndsayi1).row;
		sutun1 = secimArray.get(rndsayi1).col;
		secimArray.remove(rndsayi1);
		int rndsayi2 = Math.abs(rnd.nextInt())%(secimArray.size());
		satir2 = secimArray.get(rndsayi2).row;
		sutun2 = secimArray.get(rndsayi2).col;
		secimArray.remove(rndsayi2);
		secimArray.clear();	
	}
	// Bu fonksiyon 1-19 arasý sayýlarýn hangi gözlerde olduðunu tutan bir arraylist 
	// hazýrlýyor. Bu arraylist'n adý 'secimArray'. Daha sonra bu fonksiyon secim()
	// adlý fonksiyonda çaðrýlýyor.

	public static void secimArraylistim()
	{
	YeniMtr ymtr7 = new YeniMtr(); ymtr7.row=0; ymtr7.col=2;
	secimArray.add(ymtr7);
	YeniMtr ymtr6 = new YeniMtr(); ymtr6.row=0; ymtr6.col=4;
	secimArray.add(ymtr6);
	YeniMtr ymtr5 = new YeniMtr(); ymtr5.row=0; ymtr5.col=6;
	secimArray.add(ymtr5);
	YeniMtr ymtr4 = new YeniMtr(); ymtr4.col=1;	ymtr4.row=1;
	secimArray.add(ymtr4);
	YeniMtr ymtr3 = new YeniMtr(); ymtr3.col=3;	ymtr3.row=1;
	secimArray.add(ymtr3);
	YeniMtr ymtr2 = new YeniMtr(); ymtr2.row=1; ymtr2.col=5;
	secimArray.add(ymtr2);
	YeniMtr ymtr1 = new YeniMtr(); ymtr1.row=1; ymtr1.col=7;
	secimArray.add(ymtr1);
	YeniMtr ymtre = new YeniMtr(); ymtre.row=2; ymtre.col=0;
	secimArray.add(ymtre);
	YeniMtr ymtrw = new YeniMtr(); ymtrw.row=2; ymtrw.col=0;
	secimArray.add(ymtrw);
	YeniMtr ymtrq = new YeniMtr(); ymtrq.row=2; ymtrq.col=2;
	secimArray.add(ymtrq);
	YeniMtr ymtri = new YeniMtr(); ymtri.row=2; ymtri.col=4;
	secimArray.add(ymtri);
	YeniMtr ymtrl = new YeniMtr(); ymtrl.row=2; ymtrl.col=6;
	secimArray.add(ymtrl);
	YeniMtr ymtrk = new YeniMtr(); ymtrk.row=2; ymtrk.col=8;
	secimArray.add(ymtrk);
	YeniMtr ymtrj = new YeniMtr(); ymtrj.row=3; ymtrj.col=1;
	secimArray.add(ymtrj);
	YeniMtr ymtrh = new YeniMtr(); ymtrh.row=3; ymtrh.col=3;
	secimArray.add(ymtrh);
	YeniMtr ymtrg = new YeniMtr(); ymtrg.row=3; ymtrg.col=5;
	secimArray.add(ymtrg);
	YeniMtr ymtrf = new YeniMtr(); ymtrf.row=3; ymtrf.col=7;
	secimArray.add(ymtrf);
	YeniMtr ymtrd = new YeniMtr(); ymtrd.row=4; ymtrd.col=2;
	secimArray.add(ymtrd);
	YeniMtr ymtrs = new YeniMtr(); ymtrs.row=4; ymtrs.col=4;
	secimArray.add(ymtrs);
	YeniMtr ymtra = new YeniMtr(); ymtra.row=4; ymtra.col=6;
	secimArray.add(ymtra);
	}
	//bu fonksiyonda her bir matrisin iyilik deðerleri hesaplanýp 'iyilikm'arraylist'ine
	//yazýlýyor.
	public static void iyilik_Degeri_Hesapla(ArrayList<Matris> olusturulan5)
{
	int t=0;
	iyilikm.clear();
	// Bu döngüde oluþturulmuþ olan her bir matrisin iyilik deðeri hesaplanýyor.
	while(t<olusturulan5.size())
	{
		//bu kýsýmda matrisin satýrlarýnýn iyilik degeri toplamý hesaplanýyor
		int top = 0;	
		for(int i=0; i<5;i++)
		{
			int satir = 0;
			for(int j=0;j<9;j++)
			{
				satir = satir + olusturulan5.get(t).matrix[i][j];
			}
			top = top + Math.abs(38-satir);
		}
		//bu kýsýmda matrisin üstten sol aþagý çapraz yönundeki iyilik degerleri toplamý hesaplanýyor.
		int sol_capraz = 0;
		int ara_top=  olusturulan5.get(t).matrix[0][2] + olusturulan5.get(t).matrix[1][1] + olusturulan5.get(t).matrix[2][0];
		 sol_capraz = sol_capraz + Math.abs(38-ara_top);
		 ara_top=  olusturulan5.get(t).matrix[0][4] + olusturulan5.get(t).matrix[1][3] +
				 + olusturulan5.get(t).matrix[2][2] + olusturulan5.get(t).matrix[3][1] ;
		 sol_capraz = sol_capraz + Math.abs(38-ara_top);
		 ara_top=  olusturulan5.get(t).matrix[0][6] + olusturulan5.get(t).matrix[1][5] +
				 + olusturulan5.get(t).matrix[2][4] + olusturulan5.get(t).matrix[3][3] + olusturulan5.get(t).matrix[4][2];
		 sol_capraz = sol_capraz + Math.abs(38-ara_top);
		 ara_top=  olusturulan5.get(t).matrix[1][7] + olusturulan5.get(t).matrix[2][6] +
				 + olusturulan5.get(t).matrix[3][5] + olusturulan5.get(t).matrix[4][4];
		 sol_capraz = sol_capraz + Math.abs(38-ara_top);
		 ara_top=  olusturulan5.get(t).matrix[2][8] + olusturulan5.get(t).matrix[3][7] +
				 + olusturulan5.get(t).matrix[4][6] ;
		 sol_capraz = sol_capraz + Math.abs(38-ara_top);
		//bu kýsýmda matrisin üstten sag asagý çapraz yönundeki iyilik degerleri toplamý hesaplanýyor.
		 int sag_capraz = 0;
		 ara_top=  olusturulan5.get(t).matrix[2][0] + olusturulan5.get(t).matrix[3][1] + olusturulan5.get(t).matrix[4][2];
		 sag_capraz = sag_capraz + Math.abs(38-ara_top);
		 ara_top=  olusturulan5.get(t).matrix[4][4] + olusturulan5.get(t).matrix[1][1] +
				 + olusturulan5.get(t).matrix[2][2] + olusturulan5.get(t).matrix[3][3] ;
		 sag_capraz = sag_capraz + Math.abs(38-ara_top);
		 ara_top=  olusturulan5.get(t).matrix[0][2] + olusturulan5.get(t).matrix[1][3] +
				 + olusturulan5.get(t).matrix[2][4] + olusturulan5.get(t).matrix[3][5] + olusturulan5.get(t).matrix[4][6];
		 sag_capraz = sag_capraz + Math.abs(38-ara_top);
		 ara_top=  olusturulan5.get(t).matrix[0][4] + olusturulan5.get(t).matrix[1][5] +
				 + olusturulan5.get(t).matrix[2][6] + olusturulan5.get(t).matrix[3][7];
		 sag_capraz = sag_capraz + Math.abs(38-ara_top);
		 ara_top=  olusturulan5.get(t).matrix[0][6] + olusturulan5.get(t).matrix[1][7] +
				 + olusturulan5.get(t).matrix[2][8] ;
		 sag_capraz = sag_capraz + Math.abs(38-ara_top);
		top = top + sol_capraz + sag_capraz ;
		iyilikm.add(top); 
		t++;
	}

}
}
	


	

