package com.practice.sonarqube_actions_demo.persistence;

import com.practice.sonarqube_actions_demo.exception.DaoException;
import com.practice.sonarqube_actions_demo.model.entities.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDaoImpl implements UserDao {

    final DataSource dataSource;

    public Optional<User> getUserById(Integer id) {
        String query = "SELECT id, username, email FROM users WHERE id = " + id;
        try (
                Connection connection = dataSource.getConnection();
                Statement ps = connection.createStatement()) {
            var rs = ps.executeQuery(query);
            if (rs.next()) {
                User user = buildUserEntity(rs);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (NullPointerException | SQLException e) {
            throw new DaoException("SQL error", e);
        }
    }

    private User buildUserEntity(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("email"));
    }
}
