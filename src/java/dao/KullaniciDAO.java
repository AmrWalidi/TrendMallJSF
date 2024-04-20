
package dao;

import entity.Kullanici;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import util.DBConnection;

/**
 *
 * @author HP
 */
public class KullaniciDAO {

    private Connection db;

    public KullaniciDAO() {

    }

    public Connection getConn() {
        if (db == null) {
            DBConnection conn = new DBConnection();
            db = conn.getConnection();
        }
        return db;
    }

    public void create(Kullanici k, String type) {
        try {
            Statement st = this.getConn().createStatement();
            String query = "INSERT INTO " + type + " VALUES (DEFAULT, '" + k.getAd()
                    + "', '" + k.getSoyad() + "', '" + k.getEposta() + "', '" + k.getSifre()
                    + "', '" + k.getAdres() + "', '" + k.getTelNo() + "')";
            st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
