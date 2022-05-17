package com.innotech.data;

import com.innotech.model.Date;
import com.innotech.model.Dates;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DateDAO {
    @Autowired
    DataSource dataSource;

    private final String GET_DATE_COUNT = "SELECT COUNT(*) FROM DATE";
    private final String GET_DATE = "SELECT d.value FROM DATE d WHERE d.date_id=%d";
    private final String GET_ALL_DATES = "SELECT d.value FROM DATE d";
    private final String GET_DATE_BY_VALUE = "SELECT d.value FROM DATE d WHERE d.value='%s'";

    public int getDatesCount() throws SQLException {
        int records = 0;
        try (PreparedStatement stmt = new PreparedStatementCreatorFactory(GET_DATE_COUNT)
                .newPreparedStatementCreator(new Object[]{})
                .createPreparedStatement(dataSource.getConnection())) {

            if (!stmt.execute()) {
            }

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                records = rs.getInt(1);
            }
            return records;
        }
    }

    public Date getDateById(long date_id) throws SQLException {
        Date date = new Date();
        try (PreparedStatement stmt = new PreparedStatementCreatorFactory(String.format(GET_DATE, date_id))
                .newPreparedStatementCreator(new Object[]{})
                .createPreparedStatement(dataSource.getConnection())) {

            if (!stmt.execute()) {
            }

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                String value = rs.getString(1);
                date.setDate(value);
            }
            return date;
        }
    }

    public Dates getAllDates() throws SQLException {
        List<Date> datesList = new ArrayList<>();
        try (PreparedStatement stmt = new PreparedStatementCreatorFactory(GET_ALL_DATES)
                .newPreparedStatementCreator(new Object[]{})
                .createPreparedStatement(dataSource.getConnection())) {

            if (!stmt.execute()) {
            }

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                Date date = new Date(rs.getString(1));
                datesList.add(date);
            }
            return new Dates(datesList);
        }
    }

    public Date getDateByValue(String value) throws SQLException {
        String dbValue = null;
        try (PreparedStatement stmt = new PreparedStatementCreatorFactory(String.format(GET_DATE_BY_VALUE, value))
                .newPreparedStatementCreator(new Object[]{})
                .createPreparedStatement(dataSource.getConnection())) {

            if (!stmt.execute()) {
            }

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                dbValue = rs.getString(1);
            }

            return new Date(dbValue);
        }
    }


//    public Date getDate() throws SQLException {
//        QueryRunner queryRunner = new QueryRunner(dataSource);
//        ResultSetHandler<Date> h = new BeanHandler<Date>(Date.class);
//        Date date = queryRunner.query(GET_DATE, h);
//        return date;
//    }

    }