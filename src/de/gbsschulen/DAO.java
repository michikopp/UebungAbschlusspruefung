package de.gbsschulen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private List<Gegenstand> gegenstaende;

    public DAO() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gegenstaende", "root", "mysql");
        this.gegenstaende = new ArrayList<>();
        this.preparedStatement = connection.prepareStatement("Select bezeichnung, preis FROM  gegenstand WHERE bezeichnung LIKE ? ORDER BY bezeichnung");
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void findeArtikel(String bezeichnung) throws SQLException {
        preparedStatement.setString(1, bezeichnung);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Gegenstand gegenstand = new Gegenstand();
            gegenstand.setBezeichnung(resultSet.getString(1));
            gegenstand.setEinzelPreis(resultSet.getDouble(2));
            gegenstaende.add(gegenstand);
        }
    }

    public List<Gegenstand> getGegenstand() {
        return gegenstaende;
    }
}
