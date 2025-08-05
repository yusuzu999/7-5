package com.example.demo.dao;

import com.example.demo.mapper.StudentRowMapper;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 標示這是一個 Spring 管理的元件（Component）
// Spring 啟動時會自動將它加入應用程式上下文（ApplicationContext），讓其他類別可以透過 @Autowired 使用
@Component
public class StudentDaoImpl implements StudentDao {

    // Spring 的依賴注入機制，
    // 自動注入 NamedParameterJdbcTemplate 物件（已配置資料來源）
    // NamedParameterJdbcTemplate 是 Spring JDBC 的工具，
    // 允許使用具名參數（如 :name）來取代問號寫法，提升可讀性
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // 實作 DAO 中 insert 方法，負責將一筆學生資料寫入資料庫中
    @Override
    public Integer insert(Student student) {

        // 撰寫 SQL 指令，使用 :name 等具名參數，對應 Java map 中的鍵值
        String sql = "INSERT INTO student(name, score, graduate, create_date) VALUES (:name, :score, :graduate, :createDate)";

        // 建立一個 map（鍵值對）來存放具名參數的值
        /*
            Map 就像一個「字典」或「索引表」
            我建立了一個可以用文字（String）當作鍵，
                          存放任何類型資料（Object）當作值的對應表（Map）
            map.put("name", "Leo");
            map.put("age", 18);
            map.put("isMember", true);
            這樣你就可以透過 key 把資料撈出來：
            System.out.println(map.get("name")); // 輸出：Leo
            System.out.println(map.get("age"));  // 輸出：18

            為什麼用 Object 當 value？
            因為 Object 是所有類別的父類別，
            可以放任何型別的資料（字串、數字、布林值、甚至物件）。
         */
        Map<String, Object> map = new HashMap<>();
        map.put("name", student.getName());    // 設定學生姓名
        map.put("score", student.getScore());  // 設定學生分數
        map.put("graduate", student.isGraduate()); // 設定是否畢業（布林值）
        map.put("createDate", new Date());         // 設定建立時間為現在（系統時間）

        // KeyHolder 用來接收資料庫自動產生的主鍵（通常是 auto_increment 欄位）
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 執行 INSERT 操作，並指定使用 keyHolder 取得自動產生的主鍵
        /*
           public int update(String sql,
                             SqlParameterSource paramSource,
                             KeyHolder generatedKeyHolder) throws DataAccessException {
             return this.update(sql, paramSource, generatedKeyHolder, (String[])null);
           }

           namedParameterJdbcTemplate.update(...)
           明明是 int 回傳值，為什麼這一行沒有接住回傳值？
             Ans: 回答：可以接，也可以不接，但實際上是有回傳值的。
             白話說明：
                  當你呼叫一個有 return 值的方法，
                  如果你不需要那個值，可以不接、不用，Java 編譯器也不會報錯。

           為何可以把 MapSqlParameterSource 傳進 SqlParameterSource？
           Ans: 因為 MapSqlParameterSource 是 SqlParameterSource 的子類別（實作類別）！

                public interface SqlParameterSource {
                     Object getValue(String paramName);
                     boolean hasValue(String paramName);
                    ...其他方法
                }

                而
                public class MapSqlParameterSource implements SqlParameterSource {
                // 實作了所有 SqlParameterSource 規定的方法
                }
         */
        namedParameterJdbcTemplate.update(sql,   // SQL 語句
                new MapSqlParameterSource(map),  // 將 map 包裝成參數來源物件
                keyHolder);                      // 用來取回主鍵

        // 從 keyHolder 取得產生的主鍵值（轉為 int）
        /*
            因為 getKey() 回傳的是 Number，不是基本型別
            所以 所以才需要轉基本型別
            public interface KeyHolder {
               @Nullable
               Number getKey() throws InvalidDataAccessApiUsageException;
               .....
         */
        int id = keyHolder.getKey().intValue();

        // 印出產生的主鍵，方便開發除錯時確認
        System.out.println("mysql 自動生成的 id 為: " + id);

        // 回傳主鍵給 service 層（通常用來查剛建立的完整資料）
        return id;
    }

    // 實作 DAO 中 update 方法，負責更新資料庫中一筆學生資料
    @Override
    public void update(Student student) {

        // 更新語句，依照主鍵 id 更新對應欄位資料
        String sql = "UPDATE student SET name = :name, score = :score, graduate = :graduate WHERE id = :id";

        // 將具名參數與值對應起來
        Map<String, Object> map = new HashMap<>();
        map.put("id", student.getId());            // 要更新的主鍵 id
        map.put("name", student.getName());        // 要更新的名字
        map.put("score", student.getScore());      // 要更新的分數
        map.put("graduate", student.isGraduate()); // 是否畢業

        // 執行 UPDATE 操作
        /*
        public int update(String sql, Map<String, ?> paramMap) throws DataAccessException {
               return this.update(sql,
                                  new MapSqlParameterSource(paramMap));
        }
        */
        namedParameterJdbcTemplate.update(sql, map);
    }

    // 實作 DAO 中 deleteById 方法，根據主鍵刪除資料
    @Override
    public void deleteById(Integer id) {

        // 刪除語句，使用主鍵 id 作為條件
        String sql = "DELETE FROM student WHERE id = :id";

        // 建立參數 map，提供 SQL 中的具名參數
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);   // 要刪除的學生 id

        // 執行 DELETE 操作
        namedParameterJdbcTemplate.update(sql, map);
    }

    // 實作 DAO 中 getById 方法，根據主鍵查詢一筆學生資料
    @Override
    public Student getById(Integer id) {

        // 查詢語句，根據 id 撈取對應欄位
        String sql = "SELECT id, name, score, graduate, create_date FROM student WHERE id = :id";

        // 設定查詢條件
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        // 執行查詢，並透過 RowMapper（StudentRowMapper）將結果轉換成 Student 物件
        /*
        public <T> List<T> query(String sql,
                                 Map<String, ?> paramMap,
                                 RowMapper<T> rowMapper) throws DataAccessException {
               return this.query(sql,
                                 new MapSqlParameterSource(paramMap),
                                 rowMapper);
         }
         */
         /*
           這個 StudentRowMapper 是一個類別，
           是你我自己定義的 class，
           並且實作了 Spring JDBC 的 RowMapper<Student> 介面
         */
         List<Student> list = namedParameterJdbcTemplate.query(sql,
                                                                map,
                                                                new StudentRowMapper());



        // 如果查到資料（list.size > 0），回傳第一筆
        if (list.size() > 0) {
            return list.get(0);
        } else {
            // 如果查不到，回傳 null
            return null;
        }
    }
}
