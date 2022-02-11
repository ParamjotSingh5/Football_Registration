package Utilities;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Extensions {

    public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData metadata = rs.getMetaData();
        int columns = metadata.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(metadata.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }
}
