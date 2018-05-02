package com.gmail.netcracker.application.dto.dao.imp;

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

    protected Long insertEntity(String sql, String pkColumnName, Object... params) {
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            if (params != null && params.length > 0) {
                new ArgumentPreparedStatementSetter(params).setValues(ps);
            }
            return ps;
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        Object id = keyHolder.getKeys().get(pkColumnName);
        if (id instanceof Number) return ((Number) id).longValue();
        return null;
    }

    protected int updateEntity(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

    protected int deleteEntity(String sql, String id) {
        return jdbcTemplate.update(sql, id);
    }

    protected int deleteEntity(String sql, Long id) {
        return jdbcTemplate.update(sql, id);
    }

    protected int deleteEntity(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

    protected <E> E findEntity(String sql, RowMapper<E> rowMapper, Object... params) {
        List<E> query = jdbcTemplate.query(sql, rowMapper, params);
        switch (query.size()) {
            case 0:
                return null;
            case 1:
                return query.get(0);
            default:
                throw new IncorrectResultSizeDataAccessException(1);
        }
    }

    //It's findAll!!! Try to not use it
    protected <E> List<E> findEntityList(String sql, RowMapper<E> rowMapper) {
        return jdbcTemplate.query(sql, rowMapper);
    }

    //It's findAll!!! Try to not use it
    protected <E> List<E> findEntityList(String sql, RowMapper<E> rowMapper, Object... params) {
        return jdbcTemplate.query(sql, rowMapper, params);
    }

    protected <E> List<E> findEntityList(String sql, RowMapper<E> rowMapper, Integer limit, Integer offset) {
        return jdbcTemplate.query(sql + SQL_LIMIT_OFFSET, rowMapper, limit, offset);
    }

    protected <E> List<E> findEntityList(String sql, RowMapper<E> rowMapper, Integer limit, Integer offset, Object... params) {
        Object[] newParams = Arrays.copyOf(params, params.length + 2);
        newParams[params.length] = limit;
        newParams[params.length + 1] = offset;
        return jdbcTemplate.query(sql + SQL_LIMIT_OFFSET, rowMapper, newParams);
    }

    //It's findAll!!! Try to not use it
    protected <E> Set<E> findEntitySet(String sql, RowMapper<E> rowMapper) {
        return new HashSet<>(findEntityList(sql, rowMapper));
    }

    protected <E> Set<E> findEntitySet(String sql, RowMapper<E> rowMapper, Integer limit, Integer offset) {
        return new HashSet<>(findEntityList(sql, rowMapper, limit, offset));
    }

    protected <E> Set<E> findEntitySet(String sql, RowMapper<E> rowMapper, Integer limit, Integer offset, Object... params) {
        return new HashSet<>(findEntityList(sql, rowMapper, limit, offset, params));
    }

    protected int countRows(String sql, int id) {
        return jdbcTemplate.queryForObject(sql, new Object[] { id }, Integer.class);
    }
}