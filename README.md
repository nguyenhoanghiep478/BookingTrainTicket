# Booking Train Ticket


# README: Hướng Dẫn Chạy Ứng Dụng

## Yêu cầu hệ thống
Trước khi bắt đầu, bạn cần cài đặt các công cụ và dịch vụ sau:

1. **Java Development Kit (JDK)**: Phiên bản 11 hoặc mới hơn.
2. **Apache Kafka**: Để xử lý các sự kiện bất đồng bộ.
3. **Redis**: Để lưu trữ cache hoặc xử lý các tác vụ liên quan.
4. **Spring Boot**: Phiên bản 3.2.x.
5. **Docker** và **Docker Compose** (không bắt buộc nhưng khuyến nghị để đơn giản hóa việc chạy các dịch vụ).
6. **Python**: Phiên bản 3.8 trở lên.
7. **Pip**: Trình quản lý thư viện Python.
8. **Virtual Environment** (khuyến nghị): Để tránh xung đột thư viện.
---

## Cấu trúc dịch vụ
Ứng dụng sử dụng kiến trúc microservices. Các thành phần chính:

1. **Config Service**: Cung cấp cấu hình trung tâm cho các dịch vụ khác.
2. **Discovery Service**: Đăng ký và khám phá các dịch vụ.
3. **Các Microservices**: Bao gồm các dịch vụ chính (auth-service, store-service, order-service, v.v.).

---

## Cách chạy ứng dụng

### 1. Bắt đầu Kafka và Redis
Bạn cần chạy **Kafka** và **Redis** trước. Nếu sử dụng Docker, bạn có thể dùng file `docker-compose.yml` để khởi động.

#### Docker Compose:
Tạo file `docker-compose.yml` với nội dung sau:

```yaml
version: '3.8'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    container_name: zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
    ports:
      - "2181:2181"

  kafka:
    image: confluentinc/cp-kafka:latest
    container_name: kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

  redis:
    image: redis:latest
    container_name: redis
    ports:
      - "6379:6379"
```

Chạy lệnh sau:

```bash
docker-compose up -d
```

### 2. Khởi động Config Service
Config Service cần được chạy trước để các dịch vụ khác có thể lấy cấu hình.

#### Cách chạy:

```bash
cd config-service
./mvnw spring-boot:run
```

Kiểm tra xem Config Service đã chạy thành công trên cổng mặc định (ví dụ: `http://localhost:8888`).

### 3. Khởi động Discovery Service
Sau khi Config Service đã sẵn sàng, khởi động Discovery Service.

#### Cách chạy:

```bash
cd discovery-service
./mvnw spring-boot:run
```

Discovery Service thường chạy trên cổng `8761`. Kiểm tra giao diện Eureka tại `http://localhost:8761`.

### 4. Khởi động các Microservices
Lần lượt chạy từng microservice khác, ví dụ:

#### Auth Service:

```bash
cd auth-service
./mvnw spring-boot:run
```

#### Store Service:

```bash
cd store-service
./mvnw spring-boot:run
```

#### Order Service:

```bash
cd order-service
./mvnw spring-boot:run
```

---
#### Chatbot Service:
```bash
cd AI
python -m venv venv
.\venv\Scripts\activate
pip install -r
python main.py

## Kiểm tra ứng dụng

### 1. Swagger API Documentation
Từng dịch vụ có thể cung cấp tài liệu Swagger để kiểm tra API. Truy cập tài liệu Swagger của từng dịch vụ tại:

- **Auth Service**: `http://localhost:{port}/swagger-ui.html`
- **Store Service**: `http://localhost:{port}/swagger-ui.html`
- **Order Service**: `http://localhost:{port}/swagger-ui.html`

### 2. Tích hợp Gateway (nếu có)
Nếu có API Gateway, đảm bảo tất cả các dịch vụ đã đăng ký thành công với Discovery Service trước khi khởi động Gateway.

---

## Ghi chú

1. **Cấu hình tệp `application.yml`**:
   - Đảm bảo tất cả các microservices có cấu hình đúng địa chỉ của Config Service và Discovery Service.
   - Ví dụ:

```yaml
spring:
  application:
    name: auth-service
  cloud:
    config:
      uri: http://localhost:8888
    discovery:
      enabled: true
      service-id: discovery-service
```

2. **Kiểm tra Logs**:
   - Nếu dịch vụ không kết nối được với Kafka, Redis, hoặc Config Service, kiểm tra log để xử lý lỗi.

---

Chúc bạn thành công!

