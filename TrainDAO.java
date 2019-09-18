package trainticket;

import java.sql.*;

public class TrainDAO {
    private static Connection con;
    private static String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
    private static String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static String USERNAME = "hr";
    private static String PASSWORD = "hr";

    static {
        try {
            Class.forName(DRIVER_NAME);
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public TrainDAO() throws SQLException {
    }

    public static Statement createStatement() throws SQLException {
        return con.createStatement();
    }
    public static Train findTrain(int trainNum) throws SQLException {

        ResultSet rs = createStatement().executeQuery("select * from trains where train_no =" + trainNum);
        rs.next();
        return new Train(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5));
    }
}
