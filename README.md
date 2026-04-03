<<<<<<< HEAD
# 银行系统项目 — 学生任务书

## 项目说明
用 Spring Boot 构建一个简单的银行系统 REST API。**不使用数据库**，用 `HashMap` 模拟数据存储。

## 包结构
```
com.yourname.bank/
├── entity/
│   ├── User.java
│   └── Account.java
├── repository/
│   ├── UserRepository.java
│   └── AccountRepository.java
├── service/
│   └── AccountService.java
├── controller/
│   └── AccountController.java
└── dto.WithdrawRequest/
    └── WithdrawRequest.java
```

---

## 学生 A — Entity + Repository

**任务：设计数据结构并模拟数据库**

**Entity 要求：**
- `User`：字段 `id`, `name`, `List<Account> accounts`
- `Account`：字段 `id`, `userId`, `balance`（类型用 `double`）

**Repository 要求（用 Map 替代数据库）：**

```java
// 伪代码思路，让学生自己实现
Map<Long, User> userStore = new HashMap<>();
Map<Long, Account> accountStore = new HashMap<>();
```

- `UserRepository`：提供 `findById(Long id)`、`save(User user)` 方法
- `AccountRepository`：提供 `findById(Long id)`、`findByUserId(Long userId)`、`save(Account account)` 方法
- **在程序启动时预填充测试数据**（至少 3 个用户，每人 2-3 个账户，余额有高有低）

---

## 学生 B — Service

**任务：实现所有业务逻辑**

**Phase 1 — 取款：**
```
AccountService.withdraw(Long accountId, double amount)
```
- 账户不存在 → 抛出异常
- 余额不足 → 抛出异常
- 成功则扣款并返回最新余额

**Phase 2 — Java Streams（重点）：**

```java
// 任务1：过滤出余额超过 1000 的账户，收集成 List
List<Account> filterAccountsOver1000(Long userId)

// 任务2：对余额超过 1000 的账户求余额总和
double sumBalancesOver1000(Long userId)
```

要求：必须使用 Java Stream API（`filter`, `collect`, `mapToDouble`, `sum`），不能用 for 循环。

---

## 学生 C — Controller + DTO

**任务：设计 REST API 接口**

**DTO：**
```java
// WithdrawRequest：包含 amount 字段
```

**接口列表：**

| Method | URL | 说明 |
|--------|-----|------|
| `POST` | `/accounts/{accountId}/withdraw` | 取款 |
| `GET` | `/users/{userId}/accounts/rich` | 获取余额超过1000的账户列表 |
| `GET` | `/users/{userId}/accounts/sum` | 获取余额超过1000的账户总余额 |

- 正常返回 `200 OK` + 结果
- 账户不存在返回 `404`
- 余额不足返回 `400`

---

## 加分挑战（Optional）

- 取款时加入线程安全（`synchronized` 或 `ReentrantLock`）
- 增加存款接口 `POST /accounts/{accountId}/deposit`
- 增加转账接口 `POST /accounts/transfer`（需要原子操作）

---

## 协作建议

三人需要约定好接口（方法签名）。学生 B 和 C 依赖学生 A 的 Entity，建议 A 先把 Entity 写好，让 B 和 C 可以同步开始工作。
=======
oding :(Springboot application) [WHITE BOARD CODING, NO IDE or VS Code] 
You are creating a bank system. Create an API where you want to be able to withdraw money from user account. Upon clarification, each user has one account. Write Entity, Repository, Service, Controller. 
Later, assuming each user may have multiple accounts, use Java Streams to filter a user's accounts for those with a balance exceeding 1,000 and collect them into a list.
Use Java streams to sum the balances of a user's accounts that exceed 1,000
>>>>>>> 961d66acfcfb42126ca4e79b0c6c778cb08f780c
