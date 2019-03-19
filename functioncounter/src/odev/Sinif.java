


package odev;

public class Sinif 
{
    public int OperatorSayisi;
    public int FonksiyonSayisi;
    public int ParametreSayisi;    
    public String[] FonksiyonAdi;    
    public String[] ParametreAdi;    
    
    public void Yazdir()
    {
        System.out.println("Toplam Operator Sayisi: "+OperatorSayisi);
        System.out.println("Toplam Fonksiyon Sayisi: "+FonksiyonSayisi);
        System.out.println("Toplam Parametre Sayisi: "+ParametreSayisi);
        System.out.println("Fonksiyon isimleri: ");
        int k =0;
        for(int i=0; i<FonksiyonSayisi; i++)
        {
            System.out.print(FonksiyonAdi[i] + " - Parametreler: ");
            
            for(;k<ParametreAdi.length-1; k++)
            {
                if(ParametreAdi[k].equals("--"))
                {
                    k++;
                    break;                    
                }
                else
                {
                    System.out.print(ParametreAdi[k] + ", ");
                }
                               
            }
            System.out.println();
        }        
    }
}