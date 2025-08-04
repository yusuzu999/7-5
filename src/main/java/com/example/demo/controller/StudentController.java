package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 標記這個類別為 RESTful Web API 的控制器，所有方法預設會回傳 JSON 格式資料
@RestController

// 建立一個控制器類別，用來處理與學生資料有關的 HTTP 請求
public class StudentController {

    // 使用 Spring 的「依賴注入」（Dependency Injection）機制
    // 自動將 StudentService 實例注入進來，不需要手動 new
    // 好處：鬆耦合、方便測試與維護
    @Autowired
    private StudentService studentService;


    // 處理「建立資源」的請求
    // 對應 RESTful API 中的「Create」動作，使用 HTTP POST 動詞
    // 當收到 POST /students 的請求時，會執行這個方法
    @PostMapping("/students")

    // 宣告 create 方法，回傳 ResponseEntity<Student> 代表 HTTP 回應中包含一個學生資料
    // @RequestBody 表示將 request 的 JSON 內容轉成 Student 物件（由 Spring 自動完成資料綁定與反序列化）
    public ResponseEntity<Student> create(@RequestBody Student student) {

        // 呼叫 service 層的 insert 方法，將學生資料寫入資料庫
        // 回傳值是新資料在資料庫中的主鍵（studentId）
        Integer studentId = studentService.insert(student);

        // 根據剛剛產生的 studentId，再從資料庫撈出完整的學生資料
        // 目的是讓回傳的資料是最新、最完整的（可能有系統自動填的欄位）
        Student newStudent = studentService.getById(studentId);

        // 建立 HTTP 回應，狀態碼為 201 Created（代表資源建立成功）
        // 並把剛剛建立好的學生物件 newStudent 放進 response body 裡
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    // 處理「更新資源」的請求
    // 對應 RESTful API 中的「Update」動作，使用 HTTP PUT 動詞
    // 當收到 PUT /students/{studentId} 的請求時，執行這個方法
    @PutMapping("/students/{studentId}")
    public ResponseEntity<?> update(@PathVariable Integer studentId,
                                    @RequestBody Student student) {

        // 把從 URL 路徑取得的 studentId 設定到 student 物件上
        // 這樣可以確保更新的是正確的那一筆資料，避免 client 傳錯 ID
        student.setId(studentId);

        // 呼叫 service 層進行資料更新（通常會連接 DAO 或 Repository）
        studentService.update(student);

        // 回傳 HTTP 200 OK，代表更新成功，但不需要回傳資料內容（可依需求調整）
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 處理「刪除資源」的請求
    // 對應 RESTful API 中的「Delete」動作，使用 HTTP DELETE 動詞
    // 當收到 DELETE /students/{studentId} 時執行
    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<?> delete(@PathVariable Integer studentId) {

        // 呼叫 service 層，依照指定的 ID 刪除資料
        studentService.deleteById(studentId);

        // 回傳 HTTP 204 No Content，表示刪除成功但不需要回傳內容
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 處理「讀取單一資源」的請求
    // 對應 RESTful API 中的「Read」動作，使用 HTTP GET 動詞
    // 當收到 GET /students/{studentId} 時執行
    @GetMapping("/students/{studentId}")
    public ResponseEntity<Student> read(@PathVariable Integer studentId) {

        // 呼叫 service 層，根據 ID 取得對應的學生資料
        Student student = studentService.getById(studentId);

        // 回傳 HTTP 200 OK，並將查詢結果放入 response body 中
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }
}