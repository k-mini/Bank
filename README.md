# Junit Bank App

### Jpa LocalDateTime 자동으로 생성하는 법

- @EnableJpaAuditing (Main 클래스)
- @EntityListeners (AuditingEntityListener.class)
- @CreatedDate, @LastModifiedBy

### 예시

```java
    @CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;
```