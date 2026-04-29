# Requirement 3 — JWT 登录认证

## 已完成（本次不需要再做）

- `transfer()` 加减方向 — 已修复 ✅
- `withdraw()` 改用 `InsufficientBalanceException` — 已修复 ✅
- Controller 重复的 `@ExceptionHandler` — 已删除 ✅
- `getTransactions` 接口 — 已改用 `@RequestBody` 方式 ✅
- Swagger 文档 — 已加入 ✅

---

## 所有人都需要做：在 `pom.xml` 加入以下依赖

```xml
<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
```

---

## 学生 A — JwtUtil（Token 生成与验证）

### Bug 修复

**1. `sumBalancesOver1000` 逻辑错误**

当前把所有账户余额加总再判断，应改为只统计余额 > 1000 的账户：
```java
return accountRepository.findByUser_Id(userId).stream()
        .mapToDouble(Account::getBalance)
        .filter(balance -> balance > 1000)
        .sum();
```
同时删掉方法签名上多余的 `throws Exception`。

**2. 删掉 `AccountService` 未使用的 import**
```java
import java.sql.Time;        // 删掉
import java.sql.Timestamp;   // 删掉
import java.util.Optional;   // 删掉
```

### 新功能：`JwtUtil`

新建 `src/main/java/.../security/JwtUtil.java`

`application.properties` 加入：
```properties
jwt.secret=your-256-bit-secret-key-here-make-it-long-enough
jwt.expiration=86400000
```

```java
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    String generateToken(String email)   // 生成 token，payload 存 email，设置过期时间
    String extractEmail(String token)    // 从 token 提取 email
    boolean isTokenValid(String token)   // 验证 token 是否有效且未过期
}
```

使用 `Jwts.builder()` 生成，`Jwts.parser()` 解析。

---

## 学生 B — SecurityConfig（Spring Security 配置）

### Bug 修复

**1. `deposit()` 错误信息写错**

```java
// AccountService.deposit() 里
throw new UnauthorizedAccessException("User is not allowed to withdraw");  // ❌
// 改为
throw new UnauthorizedAccessException("User is not allowed to deposit");
```

**2. `AccountRepository` 里有一个无意义的方法**

```java
Long user(User user);  // ❌ 删掉，这个方法没有任何意义
```

### 新功能：`SecurityConfig`

新建 `src/main/java/.../security/SecurityConfig.java`

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. 关闭 csrf（REST API 不需要）
        // 2. session 设为 STATELESS（JWT 无状态）
        // 3. 放行 /auth/** 路径（登录注册不需要 token）
        // 4. 其他所有路径都需要认证
        // 5. 把 JwtFilter 加在 UsernamePasswordAuthenticationFilter 之前
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
```

---

## 学生 C — AuthService + AuthController（登录注册）

### Bug 修复

**1. `filterAccountsOver1000` 用错了异常类型**

```java
// 当前
.orElseThrow(() -> new AccountNotFoundException("User not found"));  // ❌ 语义不对
// 改为
.orElseThrow(() -> new RuntimeException("User not found"));  // 暂时用这个，等有 UserNotFoundException 再换
```

**2. `AccountController.deposit` 用了错误的 DTO**

```java
@PostMapping("/accounts/deposit")
public ResponseEntity<Double> deposit(@RequestBody WithdrawRequest request)  // ❌ 用了 WithdrawRequest
```
已有 `DepositRequest`，改为：
```java
public ResponseEntity<Double> deposit(@RequestBody DepositRequest request)
```
`DepositRequest` 里加上 `userId` 和 `accountId` 字段（目前只有 `amount`）。

### 新功能：`AuthService` + `AuthController`

新建 DTO：
- `RegisterRequest`：`name, email, password, phone`
- `LoginRequest`：`email, password`
- `AuthResponse`：`token`（String）

新建 `AuthService`：
```java
AuthResponse register(RegisterRequest request)
// 1. 检查 email 是否已存在，存在则抛异常
// 2. BCryptPasswordEncoder 加密 password 存库
// 3. role 默认 "USER"，status 默认 "ACTIVE"
// 4. 生成 JWT token 返回

AuthResponse login(LoginRequest request)
// 1. 根据 email 查用户，不存在则抛异常
// 2. passwordEncoder.matches() 验证密码
// 3. 生成 JWT token 返回
```

新建 `AuthController`：
```
POST /auth/register  → 201 + AuthResponse
POST /auth/login     → 200 + AuthResponse
```

---

## 学生 D — JwtFilter（请求拦截与 Token 验证）

### Bug 修复

**1. `UserRepository` 里加一个按 email 查询的方法**

`AuthService` 需要通过 email 查 User，但目前 `UserRepository` 只有 `findById`，加上：
```java
Optional<User> findByEmail(String email);
```

### 新功能：`JwtFilter`

新建 `src/main/java/.../security/JwtFilter.java`

```java
@Component
public class JwtFilter extends OncePerRequestFilter {

    // 注入 JwtUtil

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        // 1. 从 Header 取 Authorization 字段
        // 2. 检查是否以 "Bearer " 开头，不是则直接放行
        // 3. 截取 token，调用 jwtUtil.isTokenValid() 验证
        // 4. 验证通过则提取 email，构建 UsernamePasswordAuthenticationToken
        // 5. 设置到 SecurityContextHolder
        // 6. filterChain.doFilter() 继续
    }
}
```

---

## 依赖关系

```
学生A (JwtUtil) ←── 学生C (AuthService 调用 generateToken)
学生A (JwtUtil) ←── 学生D (JwtFilter 调用 isTokenValid / extractEmail)
学生D (JwtFilter) ←── 学生B (SecurityConfig 注入 JwtFilter)
学生D (UserRepository.findByEmail) ←── 学生C (AuthService 使用)
```

建议开发顺序：**A 先做 → C 和 D 并行 → B 最后整合**

---

## 验收标准

| 接口 | 测试 | 预期结果 |
|------|------|---------|
| `POST /auth/register` | 传 name/email/password/phone | 201 + token |
| `POST /auth/login` | 传 email/password | 200 + token |
| `POST /auth/login` | 密码错误 | 401 |
| `POST /accounts/withdraw`（不带 token） | 直接请求 | 401 Unauthorized |
| `POST /accounts/withdraw`（带 token） | Header 加 `Authorization: Bearer <token>` | 200 + 余额 |
