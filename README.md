# 🛍️ Product Service — HandMadeHub Microservices

Part of the **HandMadeHub** e-commerce platform built as an event-driven microservices architecture using Spring Boot.

---

## 📌 Overview

The Product Service handles all product management for the HandMadeHub platform. It supports full CRUD operations, category filtering, search, pagination, featured products, soft delete, and Cloudinary image uploads.

---

## 🚀 Tech Stack

| Technology | Purpose |
|---|---|
| Spring Boot 4.x | Backend framework |
| Spring Security | Route protection |
| PostgreSQL (Supabase) | Cloud database |
| Spring Data JPA + Hibernate | ORM & query generation |
| Cloudinary | Image upload & storage |
| JWT (jjwt 0.12.6) | Token validation |
| Lombok | Boilerplate reduction |
| Maven | Dependency management |

---

## 📁 Project Structure

```
src/main/java/com/handmadehub/product_service/
├── config/
│   └── CloudinaryConfig.java      # Cloudinary bean setup
├── controller/
│   ├── ProductController.java     # Product endpoints
│   └── UploadController.java      # Image upload endpoint
├── dto/
│   ├── ProductRequest.java
│   ├── ProductResponse.java
│   └── ProductPageResponse.java
├── entity/
│   └── Product.java               # Product entity with enums
├── repository/
│   └── ProductRepository.java     # JPA queries + filters
├── security/
│   ├── JwtUtil.java               # JWT validation
│   ├── JwtFilter.java             # JWT middleware
│   └── SecurityConfig.java        # Security configuration
├── service/
│   ├── ProductService.java        # Business logic
│   └── CloudinaryService.java     # Image upload logic
├── exception/
│   └── GlobalExceptionHandler.java
└── ProductServiceApplication.java
```

---

## 🔗 API Endpoints

### Product Routes (Public)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/products` | Get all products with filters |
| GET | `/api/products/:id` | Get single product by ID or slug |
| GET | `/api/products/categories/all` | Get all active categories |
| GET | `/api/products/featured/all` | Get featured products |

### Product Routes (Admin Only)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/products` | Create product |
| PUT | `/api/products/:id` | Update product |
| DELETE | `/api/products/:id` | Soft delete product |

### Upload Routes (Admin Only)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/upload` | Upload image to Cloudinary |

---

## 🔍 Query Parameters for GET /api/products

```
?category=BOUQUETS       → filter by category
?search=handmade         → search in name & description
?minPrice=100            → minimum price filter
?maxPrice=500            → maximum price filter
?sort=price-low          → sort by price ascending
?sort=price-high         → sort by price descending
?featured=true           → featured products only
?page=1                  → page number (default: 1)
?limit=12                → items per page (default: 12)
```

---

## 🗂️ Product Categories

```
BOUQUETS | FRAMES | STRING_ART | CLAY_ITEMS | ACCESSORIES
PHONE_CASES | CUPS | MINI_ITEMS | CARDS | HAMPERS
```

---

## 📦 Product Sizes

```
SMALL | MEDIUM | LARGE | A5 | STANDARD | NA
```

---

## ⚙️ Environment Variables

Create a `.env` file in the root directory:

```env
DB_URL=jdbc:postgresql://your-supabase-host:5432/postgres
DB_USERNAME=postgres
DB_PASSWORD=your_password
JWT_ACCESS_SECRET=your_access_secret_min_32_chars
JWT_REFRESH_SECRET=your_refresh_secret_min_32_chars
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret
```

---

## 🏃 Running Locally

```bash
# Clone the repo
git clone https://github.com/Dosscyril/handmadehub-product-service.git
cd handmadehub-product-service

# Create .env file with your credentials
cp .env.example .env

# Run the service
./mvnw spring-boot:run
```

Service runs on **http://localhost:8082**

---

## 📦 Sample Requests

### Create Product (Admin)
```json
POST /api/products
Authorization: Bearer <accessToken>

{
  "name": "Handmade Bouquet",
  "description": "Beautiful handmade flower bouquet",
  "price": 599.00,
  "category": "BOUQUETS",
  "stock": 10,
  "images": ["https://res.cloudinary.com/yourcloud/image/upload/sample.jpg"],
  "featured": true,
  "packSize": 1,
  "isCustomizable": true,
  "size": "MEDIUM"
}
```

### Get Products with Filters
```
GET /api/products?category=BOUQUETS&minPrice=100&maxPrice=1000&sort=price-low&page=1&limit=12
```

### Upload Image (Admin)
```
POST /api/upload
Authorization: Bearer <accessToken>
Content-Type: multipart/form-data
file: <image file>
```

---

## 🔒 Auth & Role Flow

```
Public routes     → No token needed
Admin routes      → Bearer token with ROLE_ADMIN
SuperAdmin routes → Bearer token with ROLE_SUPERADMIN

Token comes from auth-service (port 8081)
```

---

## 🗄️ Database Tables (Auto Created)

```
products       → main product table
product_images → stores image URLs per product
```

---

## 🌐 Part of HandMadeHub Microservices

| Service | Port | Status |
|---|---|---|
| auth-service | 8081 | ✅ Live |
| **product-service** | 8082 | ✅ Live |
| cart-service | 8083 | 🔨 Building |
| order-service | 8084 | 🔨 Building |
| payment-service | 8085 | 🔨 Building |
| api-gateway | 8080 | 🔨 Building |

---

## 👨‍💻 Author

**Doss Cyril (Bunty)**
- GitHub: [@Dosscyril](https://github.com/Dosscyril)
- Email: dosscyrill@gmail.com
