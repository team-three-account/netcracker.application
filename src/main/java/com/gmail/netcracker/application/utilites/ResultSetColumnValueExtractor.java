package com.gmail.netcracker.application.utilites;

import java.sql.*;

public class ResultSetColumnValueExtractor {
    private static boolean isColumnPresent(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            if (columnName.equals(rsmd.getColumnName(i))) {
                return true;
            }
        }
        return false;
    }

    public static Long getLong(ResultSet rs, String columnName) throws SQLException {
        return isColumnPresent(rs, columnName) ? rs.getLong(columnName) : null;
    }

    public static String getString(ResultSet rs, String columnName) throws SQLException {
        return isColumnPresent(rs, columnName) ? rs.getString(columnName) : null;
    }

    public static Date getDate(ResultSet rs, String columnName) throws SQLException {
        return isColumnPresent(rs, columnName) ? rs.getDate(columnName) : null;
    }

    public static Integer getInt(ResultSet rs, String columnName) throws SQLException {
        return isColumnPresent(rs, columnName) ? rs.getInt(columnName) : null;
    }

    public static Boolean getBoolean(ResultSet rs, String columnName) throws SQLException {
        return isColumnPresent(rs, columnName) ? rs.getBoolean(columnName) : null;
    }

    public static Double getDouble(ResultSet rs, String columnName) throws SQLException {
        return isColumnPresent(rs, columnName) ? rs.getDouble(columnName) : null;
    }

    public static Timestamp getTimestamp(ResultSet rs, String columnName) throws SQLException {
        return isColumnPresent(rs, columnName) ? rs.getTimestamp(columnName) : null;
    }
}
