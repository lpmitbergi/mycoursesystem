package dataaccess;

import domain.Course;
import domain.CourseType;
import util.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlCourseRepository implements MyCourseRepository{

    private Connection con;

    public MySqlCourseRepository() throws SQLException, ClassNotFoundException {
        this.con = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem", "root", "");
    }

    @Override
    public List<Course> findAllCoursesByName(String name) {
        return List.of();
    }

    @Override
    public List<Course> findAllCoursesByDescription(String description) {
        return List.of();
    }

    @Override
    public List<Course> findAllCoursesByNameOrDescription(String searchText) {
        return List.of();
    }

    @Override
    public List<Course> findAllCoursesByCourseType(CourseType courseType) {
        return List.of();
    }

    @Override
    public List<Course> findAllCoursesByStartDate(Date startDate) {
        return List.of();
    }

    @Override
    public List<Course> findAllRunningCourses() {
        return List.of();
    }

    @Override
    public Optional<Course> insert(Course entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Course> getById(Long id) {
        Assert.notNull(id);


        return Optional.empty();
    }

    private int countCoursesInDbWithId(Long id)
    {
        String countSql = "SELECT COUNT(*) FROM `courses` WHERE `id`=?";
        PreparedStatement preparedStatementCount = con.prepareStatement(countSql);
        preparedStatementCount.setLong(1,id);
        ResultSet resultSetCount = preparedStatementCount.executeQuery();
        resultSetCount.next();
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT * FROM `courses`";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Course> courseList = new ArrayList<>();
            while(resultSet.next())
            {
                courseList.add( new Course(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("hours"),
                        resultSet.getDate("begindate"),
                        resultSet.getDate("enddate"),
                        CourseType.valueOf(resultSet.getString("coursetype"))
                        )
                );
            }
            return courseList;
        } catch (SQLException e) {
            throw new DatabaseException("Database error occured!");
        }
    }

    @Override
    public Optional<Course> update(Course entity) {
        return Optional.empty();
    }

    @Override
    public void deletById(Long id) {

    }
}
