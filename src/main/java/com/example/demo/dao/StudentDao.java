package com.example.demo.dao;

import com.example.demo.model.Student;

// 這是一個資料存取層（DAO, Data Access Object）的介面
// DAO 負責與資料庫進行 CRUD 操作（建立、查詢、更新、刪除）
// 使用介面可以讓實作方式（JDBC、JPA、MyBatis 等）與邏輯層（Service）分離，實現鬆耦合與易於測試
public interface StudentDao {

    // 插入一筆學生資料到資料庫中，並回傳這筆資料產生的主鍵（ID）
    // 對應 SQL: INSERT INTO student (...) VALUES (...);
    Integer insert(Student student);

    // 根據學生物件的 ID 更新資料庫中對應的那一筆資料
    // 對應 SQL: UPDATE student SET name=?, age=? WHERE id=?
    void update(Student student);

    // 根據主鍵 ID 刪除對應的學生資料
    // 對應 SQL: DELETE FROM student WHERE id=?
    void deleteById(Integer id);

    // 根據主鍵 ID 查詢一筆學生資料，並將結果映射成 Student 物件
    // 對應 SQL: SELECT * FROM student WHERE id=?
    Student getById(Integer id);
}
