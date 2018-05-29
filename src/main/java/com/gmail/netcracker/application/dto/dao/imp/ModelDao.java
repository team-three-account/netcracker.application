package com.gmail.netcracker.application.dto.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@PropertySource("classpath:sql_statements.properties")
public abstract class ModelDao {
    @Value("${sql.limit.offset}")
    private String SQL_LIMIT_OFFSET;

    protected final JdbcTemplate jdbcTemplate;

    protected ModelDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Inserts entity and returns generated key of entity (if key is instance of Number).
     * If key has another type, null will be returned
     *
     * @param sql          an SQL statement that may contain one or more '?' in parameter placeholders
     * @param pkColumnName primary key column name, which holds generated key value
     * @param args         arguments for sql-statement
     * @return generated number key of inserted entity. If key has another type, null will be returned
     */
    protected Long insertEntity(String sql, String pkColumnName, Object... args) {
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            if (args != null && args.length > 0) {
                new ArgumentPreparedStatementSetter(args).setValues(ps);
            }
            return ps;
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        Object id = keyHolder.getKeys().get(pkColumnName);
        if (id instanceof Number) return ((Number) id).longValue();
        return null;
    }

    protected int updateEntity(String sql, Object... args) {
        return jdbcTemplate.update(sql, args);
    }

    protected int deleteEntity(String sql, String id) {
        return jdbcTemplate.update(sql, id);
    }

    protected int deleteEntity(String sql, Long id) {
        return jdbcTemplate.update(sql, id);
    }

    protected int deleteEntity(String sql, Object... args) {
        return jdbcTemplate.update(sql, args);
    }

    /**
     * @throws IncorrectResultSizeDataAccessException if result size > 1
     */
    protected <E> E findEntity(String sql, RowMapper<E> rowMapper, Object... args) {
        List<E> query = jdbcTemplate.query(sql, rowMapper, args);
        switch (query.size()) {
            case 0:
                return null;
            case 1:
                return query.get(0);
            default:
                throw new IncorrectResultSizeDataAccessException(1);
        }
    }

    protected <E> List<E> findEntityList(String sql, RowMapper<E> rowMapper) {
        return jdbcTemplate.query(sql, rowMapper);
    }

    protected <E> List<E> findEntityList(String sql, RowMapper<E> rowMapper, Object... args) {
        return jdbcTemplate.query(sql, rowMapper, args);
    }

    protected <E> List<E> findEntityList(String sql, RowMapper<E> rowMapper, Integer limit, Integer offset) {
        return jdbcTemplate.query(sql + " " + SQL_LIMIT_OFFSET, rowMapper, limit, offset);
    }

    protected <E> List<E> findEntityList(String sql, RowMapper<E> rowMapper, Integer limit, Integer offset, Object... args) {
        Object[] newArgs = Arrays.copyOf(args, args.length + 2);
        newArgs[args.length] = limit;
        newArgs[args.length + 1] = offset;
        return jdbcTemplate.query(sql + " " + SQL_LIMIT_OFFSET, rowMapper, newArgs);
    }

    protected <E> Set<E> findEntitySet(String sql, RowMapper<E> rowMapper) {
        return new HashSet<>(findEntityList(sql, rowMapper));
    }

    protected <E> Set<E> findEntitySet(String sql, RowMapper<E> rowMapper, Integer limit, Integer offset) {
        return new HashSet<>(findEntityList(sql, rowMapper, limit, offset));
    }

    protected <E> Set<E> findEntitySet(String sql, RowMapper<E> rowMapper, Integer limit, Integer offset, Object... args) {
        return new HashSet<>(findEntityList(sql, rowMapper, limit, offset, args));
    }

    protected Long countRows(String sql, Object... args) {
        return jdbcTemplate.queryForObject(sql, args, Long.class);
    }
}