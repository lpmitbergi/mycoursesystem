package dataaccess;

import domain.Student;

import java.util.List;

public interface MyStudentRepository extends BaseRepository <Student, Long>{
    List<Student> findByVn(String vn);
    List<Student> searchStudentByBirthdate(java.sql.Date birthdate);




}