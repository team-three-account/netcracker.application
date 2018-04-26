package com.gmail.netcracker.application.dto.dao.imp;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@PropertySource("classpath:sql_statements.properties")
public abstract class ModelDao {
    private final String SQL_STATEMENTS_PATH;
    private final String SQL_LIMIT_OFFSET;

    protected final JdbcTemplate jdbcTemplate;
    private final ResourceLoader resourceLoader;

    protected ModelDao(DataSource dataSource, ResourceLoader resourceLoader, Environment environment) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.resourceLoader = resourceLoader;
        SQL_STATEMENTS_PATH = environment.getRequiredProperty("statements.path");
        SQL_LIMIT_OFFSET = environment.getRequiredProperty("statements.limit.offset");
    }

    //sql_path is relative to db/statements
    private String loadSqlStatement(String sql_path) {
        String sql_statement = null;
        try {
            sql_statement = IOUtils.toString(resourceLoader.getResource(SQL_STATEMENTS_PATH + sql_path).getInputStream(),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return sql_statement.concat(" ");
        }
    }

    protected Long insertEntity(String sql_path, String pkColumnName, Object... params) {
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(loadSqlStatement(sql_path),
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

    protected int updateEntity(String sql_path, Object... params) {
        return jdbcTemplate.update(loadSqlStatement(sql_path), params);
    }

    protected int deleteEntity(String sql_path, String id) {
        return jdbcTemplate.update(loadSqlStatement(sql_path), id);
    }

    protected int deleteEntity(String sql_path, Long id) {
        return jdbcTemplate.update(loadSqlStatement(sql_path), id);
    }

    protected int deleteEntity(String sql_path, Object... params) {
        return jdbcTemplate.update(loadSqlStatement(sql_path), params);
    }

    protected <E> E findEntity(String sql_path, RowMapper<E> rowMapper, Object... params) {
        List<E> query = jdbcTemplate.query(loadSqlStatement(sql_path), rowMapper, params);
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
    protected <E> List<E> findEntityList(String sql_path, RowMapper<E> rowMapper) {
        return jdbcTemplate.query(loadSqlStatement(sql_path), rowMapper);
    }

    //It's findAll!!! Try to not use it
    protected <E> List<E> findEntityList(String sql_path, RowMapper<E> rowMapper, Object... params) {
        return jdbcTemplate.query(loadSqlStatement(sql_path), rowMapper, params);
    }

    protected <E> List<E> findEntityList(String sql_path, RowMapper<E> rowMapper, Integer limit, Integer offset) {
        return jdbcTemplate.query(loadSqlStatement(sql_path) + SQL_LIMIT_OFFSET, rowMapper, limit, offset);
    }

    protected <E> List<E> findEntityList(String sql_path, RowMapper<E> rowMapper, Integer limit, Integer offset, Object... params) {
        Object[] newParams = Arrays.copyOf(params, params.length + 2);
        newParams[params.length] = limit;
        newParams[params.length + 1] = offset;
        return jdbcTemplate.query(loadSqlStatement(sql_path) + SQL_LIMIT_OFFSET, rowMapper, newParams);
    }

    //It's findAll!!! Try to not use it
    protected <E> Set<E> findEntitySet(String sql_path, RowMapper<E> rowMapper) {
        return new HashSet<>(findEntityList(sql_path, rowMapper));
    }

    protected <E> Set<E> findEntitySet(String sql_path, RowMapper<E> rowMapper, Integer limit, Integer offset) {
        return new HashSet<>(findEntityList(sql_path, rowMapper, limit, offset));
    }

    protected <E> Set<E> findEntitySet(String sql_path, RowMapper<E> rowMapper, Integer limit, Integer offset, Object... params) {
        return new HashSet<>(findEntityList(sql_path, rowMapper, limit, offset, params));
    }
}