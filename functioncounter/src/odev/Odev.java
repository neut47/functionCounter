

package odev;

//import java.io.*;
//import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Odev 
{    
    //yazıların tek tek tutulması için arraylist tanımlandı.
    static Sinif sinif =new Sinif();
    static ArrayList kelime = new ArrayList();
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        //Dosya adı tanımlanıp okutuldu
        File file = new File("Program.c");
        
        BufferedReader br = new BufferedReader(new FileReader(file));
        
        String st;
        //Çoklu yorum satırlarını algılamak için boolean tanımlandı ve şartları belirtildi
        boolean yorum=false;
        while ((st = br.readLine()) != null) 
        {
            // // ile başlayan yorumların alınmaması için tanımlandı
            if(st.contains("//"))
            {
                st=st.split("//")[0];
            }
            if (st.contains("/*")) 
            {
                yorum = true;
            }
            if(st.contains("*/"))
            {                
                yorum = false;                
            }
             // ayırma metodları tanımlandı
            for (int i = 0; i < st.split("[)};\\s]").length; i++) 
            {
                
                if (!st.split("[)};\\s]")[i].isEmpty() && yorum == false && !st.contains("*/")) 
                {
                    kelime.add(st.split("[)};\\s]")[i]);
                }
                
            }            
                                    
        }   //System.out.println(kelime);     buradaki komut kontrol için kullanıldı. ArrayListin düzgün çalışıp çalışmadığını kontrol ettik.
                
                
        sinif.FonksiyonAdi=new String[100];
        sinif.ParametreAdi = new String[100];
        
        ToplamOperatorSayisi(kelime);
        ToplamFonksiyonSayisi(kelime);
        sinif.Yazdir(); 
        
    }
    
    static int ToplamOperatorSayisi(ArrayList<String> kelime) 
    {
        int toplam = 0;
        for (int i = 0; i < kelime.size(); i++) 
        {
            //belirli operatorler yazdırılıp aratıldı
            for (int j = 0; j < kelime.get(i).split("").length; j++) 
            {
                String ch = kelime.get(i).split("")[j];
                if (ch.equals("+") || ch.equals("-") || ch.equals("/") || ch.equals("*") || ch.equals("&") || ch.equals("=") || ch.equals("|")) 
                {
                    if (j<kelime.get(i).split("").length-1)// sonraki operator arandığından dolayı kelime dizisinin boyutu 1 azaltıldı 
                    {
                        
                        if (!kelime.get(i).split("")[j].equals(kelime.get(i).split("")[j+1]))// sonraki string eğer yukarıda sağlananlardan birisine eşit değilse toplam bir arttırıldı 
                        {
                            toplam++;
                        }                        
                    }
                    
                    else
                    {
                        toplam++;
                    }
                }
                // sonuna = gelmesi durumunda 2 sayılmaması için ayrıca arandı
                else if(ch.equals("!") || ch.equals("<") || ch.equals(">"))
                //else if(ch.equals("!"))
                {
                    if (j<kelime.get(i).split("").length-1) 
                    {
                        
                        if (!kelime.get(i).split("")[j+1].equals("=")) 
                        {
                            toplam++;                            
                        }
                    }                    
                }
                
            }
        }
        sinif.OperatorSayisi = toplam;
        return toplam;
    }    
        
    static int ToplamFonksiyonSayisi(ArrayList<String> kelime)
    {
        // çift boolean parametrelerin saydırılabilmesi için tanımlandı
        int toplam = 0;
        int tmp = 0;
        boolean sayac=false;
        boolean sayac2;
        int param = 0;
        
        int j;
        for (int i = 0; i < kelime.size()-1; i++)
        {
            if(kelime.get(i).charAt(0) != '{') // parametreler için şartlar belirtildi
                {                    
                    sayac2=true;
                }
                else
                {                    
                    sayac=false;
                    sayac2=false;
                    sinif.ParametreAdi[param] = "--";                 
                    param++;
                    
                }
            //tmp 1 olması durumunda kelimenin ( sahip olup olmadığına bakılıyor
            if(tmp==1 && kelime.get(i).contains("(") || kelime.get(i+1).charAt(0)=='(')
            {
                j=i+1;
                
                    while(kelime.get(j).charAt(0)!='{' && j<kelime.size()-1)
                    {
                        
                        if(kelime.get(j).contains(",") && kelime.get(j).charAt(0)!=',')//bitişik olması durumunda kelimeler split edildi
                        {
                            if(sayac==true && sayac2==true)
                            {
                                sinif.ParametreAdi[param] = kelime.get(j).split(",")[0];
                                param++;
                            }                                                        
                            sinif.ParametreSayisi++;
                        }
                        else
                        {
                            if(kelime.get(j).charAt(0)=='(' || kelime.get(j).charAt(0)== ',' || kelime.get(j).contains("int") || kelime.get(j+1).charAt(0) == '(' || kelime.get(j).contains("string") || kelime.get(j).contains("char") || kelime.get(j).contains("float") || kelime.get(j).contains("double") || kelime.get(j).contains("mstring"))
                            {
                                
                            }
                            else
                            {
                                if(sayac==true && sayac2==true)
                                {
                                    sinif.ParametreAdi[param] = kelime.get(j).split(",")[0];
                                    param++;
                                }                               
                                sinif.ParametreSayisi++;
                            }
                        }                                        
                        j++;
                    }
                tmp+=1;
            }            
            else
            {
                tmp=0;
            }
            //gerekli iki kontrolde sağlandıktan sonra fonksiyon olarak yazdırılıyor.
            if(tmp==2)
            {
                String[] tmp1=kelime.get(i).split("[(]");
                sinif.FonksiyonAdi[toplam] = tmp1[0];
                toplam++;
                tmp=0;
            }
            // tmp koşulu ile belirtilen ifadeler fonksiyon tanımlayıp tanımlamadığı kontrol ediliyor
            if(kelime.get(i).equals("int") || kelime.get(i).equals("void") || kelime.get(i).equals("string") || kelime.get(i).equals("float") || kelime.get(i).equals("double") || kelime.get(i).equals("mstring"))
            {
                sayac=true;
                tmp+=1;
            }
        }
        sinif.FonksiyonSayisi = toplam;
        return toplam;        
    }
}
