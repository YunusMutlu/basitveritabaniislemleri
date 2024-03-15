import java.sql.*;
import java.util.ArrayList;

public class calisanislemleri {
    private Connection connection=null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    public void calisansSil(int id){
        String sorgu="Delete From calisanlar where id=?";
        try {
            preparedStatement= connection.prepareStatement(sorgu);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void calisanguncelle(int id,String yeni_ad,String yeni_soyad,String yeni_departman,String yeni_maas){
        String sorgu="Update calisanlar set ad = ? , soyad = ? , departman = ? , maas = ? where id = ?";
        try {
            preparedStatement= connection.prepareStatement(sorgu);
            preparedStatement.setString(1,yeni_ad);
            preparedStatement.setString(2,yeni_soyad);
            preparedStatement.setString(3,yeni_departman);
            preparedStatement.setString(4,yeni_maas);
            preparedStatement.setInt(5,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void  calisanEkle(String ad,String soyad,String departman,String maas){
        String sorgu="Insert Into calisanlar(ad,soyad,departman,maas) VALUES(?,?,?,?)";
        try {
            preparedStatement= connection.prepareStatement(sorgu);
            preparedStatement.setString(1,ad);
            preparedStatement.setString(2,soyad);
            preparedStatement.setString(3,departman);
            preparedStatement.setString(4,maas);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<calisan> calisanlarigetir(){
        ArrayList<calisan> cikti = new ArrayList<calisan>();
        try {
            statement = connection.createStatement();
            String sorgu="Select * From calisanlar";
            ResultSet rs = statement.executeQuery(sorgu);
            while(rs.next()){
                int id =rs.getInt("id");
                String ad =rs.getString("ad");
                String soyad =rs.getString("soyad");
                String dept =rs.getString("departman");
                String maas =rs.getString("maas");
                cikti.add(new calisan(id,ad,soyad,dept,maas));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return cikti;
    }
    public calisanislemleri(){
        String url = "jdbc:mysql://" + database.host + ":"+ database.port+ "/" + database.db_ismi;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver bulunamadi.");
        }

        try{
            connection= DriverManager.getConnection(url,database.kullanici_adi, database.parola);
            System.out.println("Bağlanti basarili.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean girisyap(String kullaniciadi,String sifre){
        String sorgu="Select * From adminler where username = ? and password = ?";
        try {
            preparedStatement = connection.prepareStatement(sorgu);
            preparedStatement.setString(1,kullaniciadi);
            preparedStatement.setString(2,sifre);
            ResultSet rs = preparedStatement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        calisanislemleri islemler = new calisanislemleri();
    }
}
