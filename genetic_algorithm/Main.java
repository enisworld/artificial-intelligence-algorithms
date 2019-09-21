import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.TextArea;

public class Main {
	ArrayList<Items> esyalar = new ArrayList<Items>();
	ArrayList<Kromozom> kromozomlar = new ArrayList<Kromozom>();// parent kromozomlarýn listesi
	ArrayList<Kromozom> yeniNesilKromozomlar1 ;
	ArrayList<Kromozom> yeniNesilKromozomlar2 = new ArrayList<Kromozom>() ;// child kromozomarýn listesi
	ArrayList<Float> degerler = new ArrayList<Float>();
	int []yuzde = new int[100];
	double MAXVALUE = 0;
	int ort = 0;
	double MAXVALUE2 = 0;
	int jenerasyonSayisi=1000;
	Kromozom bestOne ;
	Kromozom bestOne2;
	String cozumyolu = " ";
	private int kromozomSayisi;
	private JFrame frame;
	//private JPanel panel;
	//private JButton buton;
	private JTextField esya_sayisi;
	private JTextField kapasite;
	private JTextField txtKromozomSayisi;
	TextArea textArea;
	private JTextField textField;
	private JTextField txtMaxDeger;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 508, 370);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 482, 320);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		esya_sayisi = new JTextField();
		esya_sayisi.setBounds(134, 46, 86, 20);
		panel.add(esya_sayisi);
		esya_sayisi.setColumns(10);
		
		JLabel lblEsyaSayisi = new JLabel("Esya sayisi");
		lblEsyaSayisi.setBounds(25, 49, 79, 14);
		panel.add(lblEsyaSayisi);
		
		JLabel lblCantaKapasitesi = new JLabel("Canta kapasitesi");
		lblCantaKapasitesi.setBounds(25, 90, 99, 14);
		panel.add(lblCantaKapasitesi);
		
		kapasite = new JTextField();
		kapasite.setBounds(134, 87, 86, 20);
		panel.add(kapasite);
		kapasite.setColumns(10);
		
		JButton btnHesapla = new JButton("Hesapla");
		btnHesapla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int esya = Integer.parseInt(esya_sayisi.getText());
				System.out.println("esya sayisi = " + esya);
				int kapasit = Integer.parseInt(kapasite.getText());
				System.out.println("kapasite = " + kapasit);
				kromozomSayisi = Integer.parseInt(txtKromozomSayisi.getText());
				enIyiyeYerAyir(esya);// sonucu hesaplamak için.
				//Esya_olustur(esya);
				int cvt=0;
				for(int r=0;r<100;r++)
				{
					//System.out.println(r + ". adým");
					kromozomlar.clear();
					yeniNesilKromozomlar2.clear();
					degerler.clear();
					MAXVALUE = 0;
					Esya_olustur(esya);
					//dnmEsyaOlustur();
					//dnmEsyaOlustur2();
					Kromozom_olustur(esya,kapasit);
					degerHesapla(kromozomlar,esya);
					jenerasyon(esya, kapasit);

				}
				 
				//System.out.println("çözüm yüzdesi = " + ort/100);
				txtMaxDeger.setText("" + MAXVALUE2);
				//textField.setText("%" + ort);
				textArea.setText(cozumyolu);
			}
		});
		btnHesapla.setBounds(105, 133, 89, 23);
		panel.add(btnHesapla);
		
		JLabel lblKromozomSayisi = new JLabel("Kromozom Sayisi");
		lblKromozomSayisi.setBounds(260, 49, 99, 14);
		panel.add(lblKromozomSayisi);
		
		txtKromozomSayisi = new JTextField();
		txtKromozomSayisi.setBounds(358, 46, 54, 20);
		panel.add(txtKromozomSayisi);
		txtKromozomSayisi.setColumns(10);
		
		JLabel lblSonuc = new JLabel("Maksimum De\u011Fer");
		lblSonuc.setBounds(47, 180, 102, 14);
		panel.add(lblSonuc);
		
		textArea = new TextArea();
		textArea.setBounds(47, 249, 210, 46);
		panel.add(textArea);
		
		JLabel lblzm = new JLabel("\u00C7\u00F6z\u00FCm Bulma Y\u00FCzdesi :");
		lblzm.setBounds(274, 177, 138, 20);
		panel.add(lblzm);
		
		textField = new JTextField();
		textField.setBounds(414, 177, 46, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		txtMaxDeger = new JTextField();
		txtMaxDeger.setBounds(159, 177, 86, 20);
		panel.add(txtMaxDeger);
		txtMaxDeger.setColumns(10);
		
		JLabel lblMaksimumDegerinBulunduu = new JLabel("Maksimum degerin bulundu\u011Fu kromozom");
		lblMaksimumDegerinBulunduu.setBounds(47, 229, 251, 14);
		panel.add(lblMaksimumDegerinBulunduu);
		
	}
	//Adým 1: rastgele eþyalar ve bu eþyalarýn deðerleri oluþturulup esyalar arraylistinde tutulmaktadýr.
	private void Esya_olustur(int esyaSayisi)
	{
		for(int i=0;i<esyaSayisi;i++)
		{
			Items itm = new Items();
			Random rnd = new Random();
			itm.setAgýrlýk(Math.abs((rnd.nextInt())%10)+1);
			itm.setDeger(Math.abs(rnd.nextInt())%50+1);
			esyalar.add(itm);
		}
		for(int j=0;j<esyaSayisi;j++)
		{
			System.out.println(j + ". eleman agýrlýk = " + esyalar.get(j).getAgýrlýk()
					+ " deger = " + esyalar.get(j).getDeger());
		}
	}
	//Adým 2: girilen çanta kapasitesini aþmayacak þekilde rasgele kromozomlar üretmek için kulanýlmaktadýr.
	private void Kromozom_olustur(int esya,int kapasit)
	{
		for(int j=0;j<kromozomSayisi;j++)
		{
			Kromozom krom = new Kromozom();
			krom.getKromozom(esya);
			kromozomlar.add(krom);
			
			boolean kosul = true;//bu deðiþken kromozomun aðýrlýðý çanta kapasitesini aþtýgý anda FALSE olup yeniden kromozom üretmek için kulanýlmýþtýr.
			//çanta kapasitesini aþmayacak þekilde kromozomlar üretir.
			while(kosul)
			{
				int kromozomAgýrlýgý = 0;
				//System.out.println("while dongusu giris");
				for(int i=0;i<esya;i++)
				{ 
					Random rnd = new Random();
					kromozomlar.get(j).kromozom[i] = Math.abs(rnd.nextInt())%2;
					//kromozomAgýrlýgý = kromozomAgýrlýgý + (kromozomlar.get(j).kromozom[i])*(esyalar.get(i).getAgýrlýk());
				}
				kromozomAgýrlýgý = agýrlýkHesapla(j, kromozomlar, esya);
				if((kromozomAgýrlýgý <= kapasit)&&(kromozomAgýrlýgý != 0))
				{
					//System.out.println("agýrlýk = " + kromozomAgýrlýgý);
					kosul = false;
				}
			}			
		}
	}
	// Bu fonksiyon girdi olarak verilen kromozom listesinin her bir kromozomunun degerini hasaplayýp degerler listesine ekliyor.  
	private void degerHesapla(ArrayList<Kromozom> arr3,int esya)
	{
		for(int i=0;i<kromozomSayisi;i++)
		{
			float top_deger=0;
			for(int j=0;j<esya;j++)
			{
				top_deger =top_deger + arr3.get(i).kromozom[j]*esyalar.get(j).getDeger();
			}
			degerler.add(top_deger);
		}
	}
	// bu fonksiyon girdi olarak verilen iki indisin kromozomlarýnýn
	// yarýsýný karþýlýklý deðiþtirme iþlemini yapmaktadýr.
	// indis1'in ilk yarýsý ile indis2'nin ilk yarýsýnýn yeri deðiþtiriliyor.
	private void crossover(int indis1,int indis2,int esya,int cantaKapasitesi)
	{
		int tmp;
		yeniNesilKromozomlar1 = new ArrayList<Kromozom>();
		yeniNesilKromozomlar1.clear();
		yeniNesilKromozomlar1 = arrCopy(kromozomlar, yeniNesilKromozomlar1, esya);
		for(int i=0;i<esya/2;i++)
		{
			tmp = yeniNesilKromozomlar1.get(indis1).kromozom[i];
			yeniNesilKromozomlar1.get(indis1).kromozom[i] = yeniNesilKromozomlar1.get(indis2).kromozom[i]; 
			yeniNesilKromozomlar1.get(indis2).kromozom[i] = tmp;
		}
		mutasyon(indis1,esya,yeniNesilKromozomlar1);
		mutasyon(indis2,esya,yeniNesilKromozomlar1);
		
		int count = agýrlýkHesapla(indis1, yeniNesilKromozomlar1, esya);
		int count2= agýrlýkHesapla(indis2, yeniNesilKromozomlar1, esya);
		if(((count <= cantaKapasitesi)&&(count2<=cantaKapasitesi))&&((count!=0)&&(count2!=0)))
		{
			yeniNesilKromozomlar2.add(yeniNesilKromozomlar1.get(indis1));
			yeniNesilKromozomlar2.add(yeniNesilKromozomlar1.get(indis2));
		}		
	}
	// girdi olarak verilen indisin içerisideki bir görün rastgele seçilip
	// deðiþtirilmesidir. eðer 0 ise 1, 1 ise 0 yapýlmaktadýr.
	private void mutasyon(int indis12, int esya,ArrayList<Kromozom> array)
	{
		Random rnd = new Random();
		int b = Math.abs((rnd.nextInt())%esya);
		//System.out.println(b+". goz önce = "+yeniNesilKromozomlar1.get(indis12).kromozom[b]);
		int cout=(array.get(indis12).kromozom[b] + 1) % 2;
		array.get(indis12).kromozom[b] = cout;
		//System.out.println("b="+b);
		//System.out.println(b + ". goz sonra = "+yeniNesilKromozomlar1.get(indis12).kromozom[b]);

		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// bu fonksiyona gelen indis kromozomunun aðýrlýðý hesaplanýr.
	private int agýrlýkHesapla(int indis, ArrayList<Kromozom> arr,int esya)
	{
		int kromozomAgýrlýk = 0;
		for(int i=0;i<esya;i++)
		{
			kromozomAgýrlýk = kromozomAgýrlýk + arr.get(indis).kromozom[i]*esyalar.get(i).getAgýrlýk();
		}
		return kromozomAgýrlýk;
	}
	// kromozomlar listesindeki elemanlaýn seçilme yüzdelerini bulmak
	// için kullanýlan bir fonksiyondur.bu fonksiyon ile her bir kromozomun
	//seçilme yüzdesi hesaplanýp 100 elemanlý bir dizide yüzdesi kadar eleman 
	// etiketlenerek yazýlmýþtýr. 1. kromozomun yüzdesi kadar eleman 
	// 1 olarak etiketlenmiþtir.,2. kromozomun 2 þeklinde yüzde[100] dizisi oluþturulmuþtur.
	private void uygunlukFonksiyonu(ArrayList<Kromozom> arr1,int esya, int cantaKapasitesi)
	{
		double [] kromozomY = new double[kromozomSayisi];
		double a,b,tp=0.0;
		
		for(int i=0;i<kromozomSayisi;i++)
		{
			a= Double.parseDouble(Integer.toString(cantaKapasitesi));
			int agýrlýk = agýrlýkHesapla(i, arr1, esya);
			b=Double.parseDouble(Integer.toString(agýrlýk));
			double num =(a/b)*(degerler.get(i));
			BigDecimal big = new BigDecimal(num).setScale(0, BigDecimal.ROUND_HALF_UP);
			num = big.doubleValue();
			kromozomY[i] = num;
			tp=tp + kromozomY[i];
		}
		int t=0;
		double x;
		int s = 0;
		int u=0;
		for(int j=0;j<kromozomSayisi;j++)
		{
			x = (kromozomY[j]/tp)*100;
			if((Math.ceil(x)-(x))<=0.5)
			{
				x = Math.ceil(x);
			}
			else
			{
				x = Math.floor(x);
			}
			int y = (int)x;
			y=y+s;
			if(y<=100)
			{
				for(u=s;u<y;u++)
				{
					yuzde[u] = t;
				}
			}
			t++;
			s=u;
		}
	
	}
	// bu fonksiyon yeni nesli üretmek amacýyla yazýlmýþtýr.
	private void yeniNesilUret(int esya,int cantaKapasitesi)	
	{
		uygunlukFonksiyonu(kromozomlar, esya, cantaKapasitesi);// her bir kromozomun seçilme yüzdesi hesaplanýr.
		yeniNesilKromozomlar2.clear();
		while(yeniNesilKromozomlar2.size()<=kromozomSayisi-2)
		{
			Random rd = new Random();
			// rastgele iki sayý üretilir ve yüzde[] dizisinden hangi
			// elemanlarýn seçileceðini gösterir.
			int inds1 = yuzde[Math.abs(rd.nextInt()%100)];
			int inds2 = yuzde[Math.abs(rd.nextInt()%100)];
			crossover(inds1, inds2, esya, cantaKapasitesi);
		}
		degerHesapla(yeniNesilKromozomlar2, esya);
		degerMax();
		// üretilen yeni nesli eski nesile kopyalamak için arrCopy fonksiyonu çaðrýlýr.
		kromozomlar.clear();
		kromozomlar = arrCopy(yeniNesilKromozomlar2, kromozomlar, esya);
	}
	// bu fonksiyon Jenerasyon sayýsý kadar yeni nesil üretir. her nesilde maksimum kromozom degerini hesaplayýp
	// bir önceki maksimum degrden büyük ise deðiþtirir. en sonda da maksimum deðer kullanýcýya gösterilir. 
	private void jenerasyon(int esya,int cantaKapasitesi)
	{
		for(int i=0;i<jenerasyonSayisi;i++)
		{
			//System.out.println(i + ". jenerasyon");
			enBuyukKromozomuBul(esya);
			yeniNesilUret(esya, cantaKapasitesi);
			//degerMax();
			//System.out.println("MAXDeger = " + MAXVALUE);
		}
		System.out.println("MAXDeger = " + MAXVALUE);
		/*if(MAXVALUE==90)
		{
			ort++; 
		}*/
		bestOne2 = new Kromozom();
		bestOne2.getKromozom(esya);
		if(MAXVALUE2<MAXVALUE)
		{
			MAXVALUE2 = MAXVALUE;
			cozumyolu = " ";
			for(int a=0;a<esya;a++)
			{
				cozumyolu = cozumyolu + bestOne.kromozom[a];
				//System.out.print(bestOne.kromozom[a]);
			}
		}
		/*System.out.println("en iyi kromozom");
		for(int a=0;a<esya;a++)
		{
			System.out.print(bestOne.kromozom[a]);
		}*/
		System.out.println();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println();
	}
	// girdi olarak gelen arraylist' lerden birini digerine kopyalar.
	private ArrayList<Kromozom> arrCopy(ArrayList<Kromozom> arr1,ArrayList<Kromozom> arr2,int esya)
	{
		for(int i=0;i<arr1.size();i++)
		{
			Kromozom kro = new Kromozom();
			kro.getKromozom(esya);
			arr2.add(kro);
			for(int j=0;j<esya;j++)
			{
				arr2.get(i).kromozom[j] = arr1.get(i).kromozom[j];
			}
		}
		return arr2;
	}
	
	
	// en iyi kromozom bulunduðunda bu kromozomu tutmak için ilk baþta yer ayýrmada kullanýlýyor.
	private void enIyiyeYerAyir(int esya)
	{
		bestOne = new Kromozom();
		bestOne.getKromozom(esya);
	}
	private void degerMax()
	{
		double tmp = 0.0;
		for(int i=0;i<kromozomSayisi;i++)
		{
			if(tmp<degerler.get(i))
			{
				tmp = degerler.get(i);
			}
		}
		//System.out.println("maksimum deger = " + tmp);
	}
	private void enBuyukKromozomuBul(int esya)
	{
		degerHesapla(kromozomlar,esya);
		for(int i=0;i<kromozomSayisi;i++)
		{
			double deg = degerler.get(i);
			if(deg>MAXVALUE)
			{
				MAXVALUE = deg;
				for(int j=0;j<esya;j++)
				{
					bestOne.kromozom[j] = kromozomlar.get(i).kromozom[j];
				}
			}
		}
	}
	private void dnmEsyaOlustur2()
	{
		Items itms = new Items();
		itms.setAgýrlýk(5);
		itms.setDeger(10);
		esyalar.add(itms);
		Items itms2 = new Items();
		itms2.setAgýrlýk(4);
		itms2.setDeger(40);
		esyalar.add(itms2);
		Items itms3 = new Items();
		itms3.setAgýrlýk(6);
		itms3.setDeger(30);
		esyalar.add(itms3);
		Items itms4 = new Items();
		itms4.setAgýrlýk(3);
		itms4.setDeger(50);
		esyalar.add(itms4);
		
	}
	private void dnmEsyaOlustur()
	{
		Items itms = new Items();
		itms.setAgýrlýk(2);
		itms.setDeger(12);
		esyalar.add(itms);
		Items itms2 = new Items();
		itms2.setAgýrlýk(1);
		itms2.setDeger(10);
		esyalar.add(itms2);
		Items itms3 = new Items();
		itms3.setAgýrlýk(3);
		itms3.setDeger(20);
		esyalar.add(itms3);
		Items itms4 = new Items();
		itms4.setAgýrlýk(2);
		itms4.setDeger(15);
		esyalar.add(itms4);
		Items itms5 = new Items();
		itms5.setAgýrlýk(2);
		itms5.setDeger(12);
		esyalar.add(itms5);
		Items itms6 = new Items();
		itms6.setAgýrlýk(1);
		itms6.setDeger(1);
		esyalar.add(itms6);
		Items itms7 = new Items();
		itms7.setAgýrlýk(3);
		itms7.setDeger(2);
		esyalar.add(itms7);
		Items itms8 = new Items();
		itms8.setAgýrlýk(2);
		itms8.setDeger(8);
		esyalar.add(itms8);
		Items itms9 = new Items();
		itms9.setAgýrlýk(5);
		itms9.setDeger(9);
		esyalar.add(itms9);
		Items itms10 = new Items();
		itms10.setAgýrlýk(5);
		itms10.setDeger(6);
		esyalar.add(itms10);
		Items itms11 = new Items();
		itms11.setAgýrlýk(9);
		itms11.setDeger(20);
		esyalar.add(itms11);
		Items itms12 = new Items();
		itms12.setAgýrlýk(1);
		itms12.setDeger(18);
		esyalar.add(itms12);
		Items itms13 = new Items();
		itms13.setAgýrlýk(2);
		itms13.setDeger(1);
		esyalar.add(itms13);
		Items itms14 = new Items();
		itms14.setAgýrlýk(3);
		itms14.setDeger(7);
		esyalar.add(itms14);
		Items itms15 = new Items();
		itms15.setAgýrlýk(5);
		itms15.setDeger(20);
		esyalar.add(itms15);
		Items itms16 = new Items();
		itms16.setAgýrlýk(4);
		itms16.setDeger(60);
		esyalar.add(itms16);
	}
	private void kromozomYazdýr(ArrayList<Kromozom> array,int esya)
	{
		for(int a=0;a<array.size();a++)
		{
			for(int b=0;b<esya;b++)
			{
				System.out.print("" + array.get(a).kromozom[b]);
			}
			System.out.println();
		}
	}
}
