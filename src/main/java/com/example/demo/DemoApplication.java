package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 這是一個複合註解（Composite Annotation）
// 等同於 @Configuration + @EnableAutoConfiguration + @ComponentScan
// 功能：
// - 告訴 Spring Boot 這是一個應用程式的啟動類（Configuration 類）
// - 自動載入 Spring Boot 的設定（自動配置）
// - 自動掃描目前 package 與子 package 中的所有元件（@Component、@Service、@Repository、@Controller）

@SpringBootApplication
public class DemoApplication {

    // 主方法（main method），是 Java 程式的進入點
    public static void main(String[] args) {
        // 使用 SpringApplication 類啟動 Spring Boot 應用程式
        // 它會完成以下事情：
        // 1. 啟動 Spring 容器（建立 ApplicationContext）
        // 2. 啟動自動配置機制（Auto-configuration）
        // 3. 掃描所有 Spring 元件並註冊 Bean（Component Scan）
        // 4. 啟動內建的 Web 伺服器（預設是 Tomcat）
        SpringApplication.run(DemoApplication.class, args);
    }

}
