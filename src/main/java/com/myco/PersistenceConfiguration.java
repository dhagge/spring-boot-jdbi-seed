package com.myco;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;
import org.skife.jdbi.v2.tweak.ResultColumnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Configuration for JDBI and related persistence.
 */
@Configuration
public class PersistenceConfiguration {

    @Autowired
    DataSource dataSource;

    @Bean
    public DBI dbiBean() {
        DBI dbi = new DBI(dataSource);
        dbi.registerArgumentFactory(new DateTimeArgumentFactory());
        dbi.registerArgumentFactory(new LocalDateArgumentFactory());
        dbi.registerColumnMapper(new JodaDateTimeMapper());
        return dbi;
    }

    private static Calendar getUtcCalendar() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    /**
     * DBI argument factory for converting joda DateTime to sql timestamp
     */
    public static class DateTimeArgumentFactory implements ArgumentFactory<DateTime> {

        @Override
        public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
            return value != null && DateTime.class.isAssignableFrom(value.getClass());
        }

        @Override
        public Argument build(Class<?> expectedType, final DateTime value, StatementContext ctx) {
            return new Argument() {
                @Override
                public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
                    long millis = value.withZone(DateTimeZone.UTC).getMillis();
                    statement.setTimestamp(position, new Timestamp(millis), getUtcCalendar());
                }
            };
        }
    }

    /**
     * DBI argument factory for converting joda LocalDate to sql timestamp
     */
    public static class LocalDateArgumentFactory implements ArgumentFactory<LocalDate> {
        @Override
        public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
            return value != null && LocalDate.class.isAssignableFrom(value.getClass());
        }

        @Override
        public Argument build(Class<?> expectedType, final LocalDate value, StatementContext ctx) {
            return new Argument() {
                @Override
                public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
                    statement.setString(position, value.toString("yyyy-MM-dd"));
                }
            };
        }
    }

    /**
     * A {@link ResultColumnMapper} to map Joda {@link DateTime} objects.
     */
    public static class JodaDateTimeMapper implements ResultColumnMapper<DateTime> {

        @Override
        public DateTime mapColumn(ResultSet rs, int columnNumber, StatementContext ctx) throws SQLException {
            final Timestamp timestamp = rs.getTimestamp(columnNumber, getUtcCalendar());
            if (timestamp == null) {
                return null;
            }
            return new DateTime(timestamp.getTime());
        }

        @Override
        public DateTime mapColumn(ResultSet rs, String columnLabel, StatementContext ctx) throws SQLException {
            final Timestamp timestamp = rs.getTimestamp(columnLabel, getUtcCalendar());
            if (timestamp == null) {
                return null;
            }
            return new DateTime(timestamp.getTime());
        }
    }

}
