package com.example.demo.dao;

import com.example.demo.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentDaoImplTest {

    @Autowired
    private StudentDao studentDao;

    @Test
    public void getById() {
        Student student = studentDao.getById(1);
        assertNotNull(student);
        assertEquals("Amy", student.getName());
        assertEquals(90.3, student.getScore());
        assertTrue(student.isGraduate());
        assertNotNull(student.getCreateDate());
    }

    @Transactional
    @Test
    public void deleteById() {
        studentDao.deleteById(3);

        Student student = studentDao.getById(3);
        assertNull(student);
    }

    @Test  // 表示這是一個測試方法，執行測試時會自動執行這個方法
    @Transactional  // 這個測試會包在一個交易中，結束後會自動回滾（不影響真實資料）
    public void insert() {
        Student student = new Student();  // 建立一個新的 Student 物件
        student.setName("Kevin");         // 設定名字為 Kevin
        student.setScore(66.2);           // 設定分數為 66.2
        student.setGraduate(true);        // 設定為已畢業

        Integer studentId = studentDao.insert(student);  // 呼叫 insert 方法，將 student 寫入資料庫，並取得自動產生的 id

        Student result = studentDao.getById(studentId);  // 根據剛剛產生的 id 查出 student 資料

        assertNotNull(result);                           // 確認 result 不是 null（確保資料有寫入且能查出）
        assertEquals("Kevin", result.getName());         // 確認名字是 Kevin
        assertEquals(66.2, result.getScore());           // 確認分數是 66.2
        assertTrue(result.isGraduate());                 // 確認是已畢業狀態（true）
        assertNotNull(result.getCreateDate());           // 確認建立時間不是 null（代表有成功寫入建立時間）
    }

    @Test  // 表示這是一個測試方法
    @Transactional  // 測試方法會被包在一個交易（transaction）中，執行完會自動回滾（不寫入真實資料庫）
    public void update() {

        // 從資料庫查出 id = 3 的學生資料
        Student student = studentDao.getById(3);

        // 將學生姓名改成 "John"
        student.setName("John");

        // 將修改後的資料寫回資料庫（執行更新）
        studentDao.update(student);

        // 再次從資料庫查出 id = 3 的學生，確認資料有被更新
        Student result = studentDao.getById(3);

        // 確認查詢結果不是 null（代表這筆資料存在）
        assertNotNull(result);

        // 確認這筆資料的名字已經成功改成 "John"
        assertEquals("John", result.getName());
    }



}