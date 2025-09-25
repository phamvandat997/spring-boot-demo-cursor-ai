# Spring Boot Demo Application

Ứng dụng Spring Boot với JWT Authentication, Spring Security, và H2 Database.

## Tính năng

- ✅ JWT Authentication
- ✅ Spring Security với role-based access control
- ✅ H2 Database (in-memory)
- ✅ RESTful APIs
- ✅ User Management
- ✅ Password encryption với BCrypt
- ✅ CORS support

## Cấu trúc dự án

```
src/main/java/com/example/
├── SpringBootDemoApplication.java    # Main application class
├── entity/
│   ├── User.java                    # User entity với UserDetails
│   └── Role.java                    # Enum cho user roles
├── repository/
│   └── UserRepository.java          # JPA Repository
├── dto/
│   └── UserDto.java                 # Data Transfer Object
├── service/
│   ├── UserService.java             # User business logic
│   └── AuthService.java            # Authentication logic
├── controller/
│   ├── AuthController.java          # Authentication endpoints
│   ├── UserController.java          # User management endpoints
│   └── PublicController.java        # Public endpoints
└── security/
    ├── JwtUtil.java                 # JWT utility
    ├── JwtAuthenticationFilter.java # JWT filter
    └── SecurityConfig.java          # Security configuration
```

## API Endpoints

### Public Endpoints
- `GET /api/public/info` - Thông tin ứng dụng

### Authentication Endpoints
- `POST /api/auth/register` - Đăng ký user mới
- `POST /api/auth/login` - Đăng nhập

### User Endpoints (Yêu cầu authentication)
- `GET /api/users` - Lấy danh sách users (ADMIN only)
- `GET /api/users/{id}` - Lấy user theo ID
- `GET /api/users/username/{username}` - Lấy user theo username
- `GET /api/users/active` - Lấy danh sách users đang hoạt động
- `GET /api/users/role/{role}` - Lấy users theo role (ADMIN only)
- `GET /api/users/stats/count` - Đếm số users (ADMIN only)
- `POST /api/users` - Tạo user mới (ADMIN only)
- `PUT /api/users/{id}` - Cập nhật user
- `DELETE /api/users/{id}` - Xóa user (ADMIN only)

## Cách chạy ứng dụng

### Yêu cầu
- Java 11+
- Maven 3.6+

### Chạy ứng dụng
```bash
# Clone repository
git clone <repository-url>
cd spring-boot-project

# Chạy ứng dụng
mvn spring-boot:run
```

Ứng dụng sẽ chạy tại: http://localhost:8080

### Truy cập H2 Database Console
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (để trống)

## Test API

### 1. Đăng ký user mới
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123",
    "email": "admin@example.com",
    "firstName": "Admin",
    "lastName": "User",
    "role": "ADMIN"
  }'
```

### 2. Đăng nhập
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### 3. Sử dụng JWT token để truy cập protected endpoints
```bash
# Lấy danh sách users (cần ADMIN role)
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Cấu hình

### Database
- Sử dụng H2 in-memory database
- Tự động tạo tables khi khởi động
- Có thể truy cập qua H2 console

### Security
- JWT token có thời hạn 24 giờ
- Password được mã hóa bằng BCrypt
- CORS được cấu hình cho tất cả origins
- CSRF protection bị tắt cho API

### Roles
- `ADMIN`: Có quyền truy cập tất cả endpoints
- `USER`: Chỉ có quyền truy cập một số endpoints cơ bản

## Dependencies

- Spring Boot 2.7.18
- Spring Security 5.7.11
- Spring Data JPA
- H2 Database
- JWT (jjwt)
- Lombok
- Validation API

## Lưu ý

- Ứng dụng sử dụng Java 11
- Spring Boot version được downgrade từ 3.2.0 xuống 2.7.18 để tương thích với Java 11
- Sử dụng `javax` namespace thay vì `jakarta` cho Spring Boot 2.x