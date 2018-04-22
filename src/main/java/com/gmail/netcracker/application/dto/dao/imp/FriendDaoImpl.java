package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.FriendDao;
import com.gmail.netcracker.application.dto.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FriendDaoImpl extends ModelDao implements FriendDao  {

    private static final String GET_FRIENDS = "select name, surname, person_id\n"+
            "from public.\"person\"\n"+
            "where person_id = (select DISTINCT sender\n"+
            "                   from public.\"Friend\"\n"+
            "                   where recipient = ?\n"+
            "                   and \"isAccepted\" = TRUE)\n"+
            "or person_id = (select DISTINCT recipient\n"+
            "                   from public.\"Friend\"\n"+
            "                   where sender = ?\n"+
            "                   and \"isAccepted\" = TRUE)";



    @Override
    public List<User> friendList(String ids) {
        String id = "c14307e9-ab2d-4ad4-9cec-c02b6141cd86";

        List<User> friendList = jdbcTemplate.query(GET_FRIENDS, new Object[] { id, id }, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                User user = new User();
                user.setId(resultSet.getString("person_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                return user;
            }
        });
        return friendList;
    }
}
