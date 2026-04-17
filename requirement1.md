# Requirement 1 — 功能扩展 + 接入数据库

## 学生 A — 接入真实数据库

1. `pom.xml` 加入以下依赖：
   - `spring-boot-starter-data-jpa`
   - H2 数据库

2. `application.properties` 配置 H2：
   ```properties
   spring.datasource.url=jdbc:h2:mem:bankdb
   spring.h2.console.enabled=true
   spring.jpa.hibernate.ddl-auto=create-drop
   ```

3. 给 `User` 和 `Account` 加 JPA 注解：
   - `@Entity`, `@Id`, `@GeneratedValue`
   - `User` → `@OneToMany` 关联 `Account`
   - `Account` → `@ManyToOne` 关联 `User`

4. `UserRepository` 和 `AccountRepository` 改为继承 `JpaRepository<T, Long>`，删掉 HashMap 逻辑和 `StoreConfig`，用 `data.sql` 或 `CommandLineRunner` 初始化测试数据

---

## 学生 B — Service 业务扩展

1. **存款**
   ```
   deposit(Long accountId, double amount)
   ```
   - 账户不存在 → 抛出异常
   - 成功则增加余额并返回最新余额

2. **转账**
   ```
   transfer(Long fromId, Long toId, double amount)
   ```
   - 加 `@Transactional`
   - 任意账户不存在 → 抛出异常
   - 余额不足 → 抛出异常，事务回滚
   - 成功则从 fromId 扣款，toId 加款

---

## 学生 C — Controller 补全

1. **查看所有账户**
   ```
   GET /users/{userId}/accounts
   ```
   返回该用户所有账户列表

2. **存款接口**
   ```
   POST /accounts/{accountId}/deposit
   ```
   Body: `{ "amount": 100.0 }`

3. **转账接口**
   ```
   POST /accounts/transfer
   ```
   Body: `{ "fromId": 101, "toId": 102, "amount": 200.0 }`

---

## 验收标准

| 接口 | 正常返回 | 异常返回 |
|------|----------|----------|
| 存款 | 200 + 最新余额 | 404 账户不存在 |
| 转账 | 200 成功 | 404 账户不存在 / 400 余额不足 |
| 查看账户 | 200 + 账户列表 | 404 用户不存在 |
