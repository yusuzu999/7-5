package com.example.demo.mapper;

import com.example.demo.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// 這是一個實作 RowMapper 的類別，用來告訴 Spring JDBC：
// 查詢結果的每一列 (row) 要如何轉換成 Student 物件
public class StudentRowMapper implements RowMapper<Student> {

    // 實作 RowMapper 介面的 mapRow 方法
    // Spring JDBC 查詢時會自動呼叫這個方法，將每一列資料轉成一個 Student 物件
    // resultSet: 查詢結果集（游標會指向當前 row）
    // i: 當前 row 的索引（第幾列，通常用不到）
    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {

        // 建立一個新的 Student 實體，用來存放轉換後的資料
        Student student = new Student();

        // 從結果集中取出欄位值，設定到 Student 物件的屬性中
        // resultSet.getXXX("欄位名稱") 會讀取該列該欄位的資料
        // 取得資料表中的 id 欄位，設到 student 的 id 屬性
        student.setId(resultSet.getInt("id"));

        // 取得 name 欄位（學生姓名），設到 student 的 name 屬性
        student.setName(resultSet.getString("name"));

        // 取得 score 欄位（學生分數），設到 student 的 score 屬性
        student.setScore(resultSet.getDouble("score"));

        // 取得 graduate 欄位（是否畢業，布林值），設到 student 的 graduate 屬性
        student.setGraduate(resultSet.getBoolean("graduate"));

        // 取得 create_date 欄位（建立時間），設到 student 的 createDate 屬性
        student.setCreateDate(resultSet.getTimestamp("create_date"));

        // 回傳組裝好的 Student 物件，讓 Spring JDBC 把它放進查詢結果清單
        return student;
    }
}
