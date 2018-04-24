package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.FriendDao;
import com.gmail.netcracker.application.dto.model.Friend;
import com.gmail.netcracker.application.dto.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FriendDaoImpl extends ModelDao implements FriendDao  {

    @Autowired
    Friend friendship;

    private static final String GET_ALL_FRIENDS = "select name, surname, person_id\n"+
            "from public.\"person\"\n"+
            "where person_id = (select DISTINCT sender\n"+
            "                   from public.\"Friend\"\n"+
            "                   where recipient = ?\n"+
            "                   and \"isAccepted\" = TRUE)\n"+
            "or person_id = (select DISTINCT recipient\n"+
            "                   from public.\"Friend\"\n"+
            "                   where sender = ?\n"+
            "                   and \"isAccepted\" = TRUE)";

    private static final String GET_FRIENDS_BY_NAME_OR_SURNAME = "select name, surname, person_id\n" +
            "from public.\"person\"\n" +
            "where lower(name) = ?\n" +
            "      or lower(surname) = ?";

    private static final String GET_FRIENDS_BY_NAME_AND_SURNAME = "select name, surname, person_id\n" +
            "from public.\"person\"\n" +
            "where lower(name) in (?, ?)\n" +
            "      and lower(surname) in (?, ?)";

    private static final String GET_FRIEND_BY_ID= "select sender, recipient,\"isAccepted\"\n" +
            "from public.\"Friend\"\n"+
            "where sender in (?,?)\n" +
            "      and recipient in (?,?)";

    private static final String ADD_FRIEND = "INSERT INTO public.\"Friend\"\n" +
            "(sender, recipient, \"isAccepted\")\n" +
            "VALUES (?, ?, FALSE )";
    @Override
    public List<User> friendList(String id) {

        List<User> friendList = jdbcTemplate.query(GET_ALL_FRIENDS, new Object[] { id, id }, new FriendMapper() );
        return friendList;
    }

    @Override
    public void deleteFriend(String person, String friend) {

    }

    @Override
    public List<User> getFriendsByNameOrSurname(String input) {
        List<User> friendList = jdbcTemplate.query(GET_FRIENDS_BY_NAME_OR_SURNAME, new Object[] { input, input }, new FriendMapper() );
        return friendList;
    }

    @Override
    public List<User> getFriendsByNameAndSurname(String name, String surname) {
        List<User> friendList = jdbcTemplate.query(GET_FRIENDS_BY_NAME_AND_SURNAME, new Object[] { name, surname, name,surname }, new FriendMapper() );
        return friendList;
    }

    @Override
    public Friend getFriendshipById(String person_id, String friend_id) {
        jdbcTemplate.query(GET_FRIEND_BY_ID, new Object[] { person_id, friend_id, person_id, friend_id }, resultSet -> {
            while (resultSet.next()) {
                friendship.setRecipient(resultSet.getString("recipient"));
                friendship.setSender(resultSet.getString("sender"));
                friendship.setAccepted(resultSet.getBoolean("isAccepted"));
            }
            return friendship;
        });
        return friendship;
    }

    @Override
    public void addFriend(String person_id, String friend_id) {
        jdbcTemplate.update(ADD_FRIEND, person_id, friend_id );
    }

    private static final class FriendMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setId(resultSet.getString("person_id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            return user;
        }
    }

   //private static final class FriendshipMapper implements RowMapper<Friend> {

    //    @Override
    //    public Friend mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    //        friendship.setRecipient(resultSet.getString("recipient"));
    //        friendship.setSender(resultSet.getString("sender"));
     //       friendship.setAccepted(resultSet.getBoolean("isAccepted"));
    //        return friendship;
    //    }
    //}
}
