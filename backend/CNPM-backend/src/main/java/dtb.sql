CREATE
DATABASE cafe_menu;
USE
cafe_menu;

CREATE TABLE drink_category
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50),
    description TEXT
);

CREATE TABLE drink_item
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100),
    description TEXT,
    price DOUBLE,
    image_url   VARCHAR(255),
    category_id INT,
    available   BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (category_id) REFERENCES drink_category (id)
);

CREATE TABLE Owner
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    phone      VARCHAR(15),
    email      VARCHAR(100),
    shift      VARCHAR(50),
    account_id INT,
    FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
);


CREATE TABLE account
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50) UNIQUE NOT NULL,
    password  VARCHAR(100)       NOT NULL,
    role      VARCHAR(20) CHECK (role IN ('Owner', 'Staff')),
    is_active BOOLEAN DEFAULT TRUE
);


CREATE TABLE voucher
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    code          VARCHAR(20) UNIQUE NOT NULL,
    description   TEXT,
    discount DOUBLE NOT NULL,
    discount_type VARCHAR(10) CHECK (discount_type IN ('percentage', 'fixed')),
    max_uses      INT     DEFAULT 1,
    used_count    INT     DEFAULT 0,
    valid_from    DATETIME,
    valid_to      DATETIME,
    is_active     BOOLEAN DEFAULT TRUE
);

CREATE TABLE toppings
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    description TEXT,
    price DOUBLE NOT NULL,
    available   BOOLEAN DEFAULT TRUE
);

CREATE TABLE drink_toppings
(
    drink_id   INT,
    topping_id INT,
    FOREIGN KEY (drink_id) REFERENCES drink_item (id) ON DELETE CASCADE,
    FOREIGN KEY (topping_id) REFERENCES toppings (id) ON DELETE CASCADE,
    PRIMARY KEY (drink_id, topping_id)
);

INSERT INTO drink_category (name, description)
VALUES ('Cà phê', 'Các loại cà phê truyền thống và hiện đại'),
       ('Trà', 'Trà các loại, từ trà sữa đến trà trái cây'),
       ('Sinh tố', 'Sinh tố trái cây tươi'),
       ('Nước ép', 'Nước ép hoa quả nguyên chất'),
       ('Đồ uống đặc biệt', 'Đồ uống sáng tạo của quán');

INSERT INTO drink_item (name, description, price, image_url, category_id, available)
VALUES ('Cà phê đen', 'Cà phê nguyên chất không đường', 20000, '/images/cf_den.png', 1, TRUE),
       ('Cà phê sữa', 'Cà phê pha với sữa đặc', 25000, '/images/cf_sua.png', 1, TRUE),
       ('Cappuccino', 'Cà phê bọt sữa kiểu Ý', 35000, '/images/cappuccino.png', 1, TRUE),
       ('Latte', 'Cà phê sữa tươi', 35000, '/images/latte.png', 1, TRUE),
       ('Trà sữa trân châu', 'Trà sữa với trân châu đen', 30000, '/images/trasua_tc.png', 2, TRUE),
       ('Trà đào', 'Trà đào tươi mát', 28000, '/images/tradao.png', 2, TRUE),
       ('Trà xanh matcha', 'Trà xanh Nhật Bản', 32000, '/images/matcha.png', 2, TRUE),
       ('Trà chanh', 'Trà chanh tươi mát', 25000, '/images/trachanh.png', 2, TRUE),
       ('Sinh tố dâu', 'Sinh tố từ dâu tươi', 40000, '/images/sinh_to_dau.png', 3, TRUE),
       ('Sinh tố bơ', 'Sinh tố từ bơ nguyên chất', 45000, '/images/sinh_to_bo.png', 3, TRUE),
       ('Sinh tố xoài', 'Sinh tố từ xoài chín', 42000, '/images/sinh_to_xoai.png', 3, TRUE),
       ('Sinh tố chuối', 'Sinh tố chuối sữa', 39000, '/images/sinh_to_chuoi.png', 3, TRUE),
       ('Nước ép cam', 'Cam tươi ép nguyên chất', 35000, '/images/ep_cam.png', 4, TRUE),
       ('Nước ép táo', 'Táo ép nguyên chất', 37000, '/images/ep_tao.png', 4, TRUE),
       ('Nước ép dứa', 'Dứa tươi ép lạnh', 36000, '/images/ep_dua.png', 4, TRUE),
       ('Nước ép cà rốt', 'Cà rốt tươi ép nguyên chất', 34000, '/images/ep_carot.png', 4, TRUE),
       ('Mocha đá xay', 'Mocha với đá xay mịn', 45000, '/images/mocha.png', 5, TRUE),
       ('Matcha đá xay', 'Matcha mát lạnh với đá xay', 46000, '/images/matcha_daxay.png', 5, TRUE),
       ('Smoothie việt quất', 'Smoothie từ quả việt quất', 48000, '/images/smoothie_vietquat.png', 5, TRUE),
       ('Bạc xỉu', 'Cà phê sữa với nhiều sữa', 28000, '/images/bac_xiu.png', 1, TRUE);

INSERT INTO account (username, password, role, is_active)
VALUES ('owner01', 'hashed_password_01', 'Owner', TRUE),
       ('staff01', 'hashed_password_02', 'Staff', TRUE),
       ('staff02', 'hashed_password_03', 'Staff', TRUE),
       ('staff03', 'hashed_password_04', 'Staff', TRUE),
       ('owner02', 'hashed_password_05', 'Owner', TRUE);

INSERT INTO Owner (name, phone, email, shift, account_id)
VALUES ('Nguyễn Văn A', '0901234567', 'owner01@cafe.com', 'Ca sáng', 1),
       ('Trần Thị B', '0907654321', 'staff01@cafe.com', 'Ca chiều', 2),
       ('Lê Văn C', '0934567890', 'staff02@cafe.com', 'Ca tối', 3),
       ('Phạm Thị D', '0923456789', 'staff03@cafe.com', 'Ca sáng', 4),
       ('Hoàng Minh E', '0912345678', 'owner02@cafe.com', 'Ca chiều', 5);

INSERT INTO toppings (name, description, price)
VALUES ('Trân châu đen', 'Trân châu đen truyền thống', 5000),
       ('Trân châu trắng', 'Trân châu trắng dẻo', 6000),
       ('Thạch dừa', 'Thạch dừa dai', 7000),
       ('Thạch cà phê', 'Thạch hương vị cà phê', 8000),
       ('Hạt chia', 'Hạt chia dinh dưỡng', 4000),
       ('Rau câu trái cây', 'Rau câu vị trái cây', 9000),
       ('Kem cheese', 'Kem phô mai béo ngậy', 10000),
       ('Nha đam', 'Nha đam tươi mát', 6000),
       ('Pudding trứng', 'Pudding béo ngậy', 12000),
       ('Thạch phô mai', 'Thạch với nhân phô mai', 10000);

INSERT INTO drink_toppings (drink_id, topping_id)
VALUES (5, 1),
       (5, 2),
       (5, 3),
       (6, 4),
       (6, 5),
       (7, 6),
       (7, 8),
       (8, 3),
       (8, 7),
       (9, 9),
       (9, 10);

INSERT INTO voucher (code, description, discount, discount_type, max_uses, valid_from, valid_to, is_active)
VALUES ('SUMMER20', 'Giảm 20% cho hóa đơn trên 100k', 20, 'percentage', 50, '2025-06-01', '2025-08-31', TRUE),
       ('WELCOME50', 'Giảm 50k cho đơn đầu tiên', 50000, 'fixed', 100, '2025-05-12', '2025-12-31', TRUE),
       ('HAPPYHOUR', 'Giảm 10% từ 14h-17h', 10, 'percentage', 30, '2025-05-12 14:00:00', '2025-05-12 17:00:00', TRUE),
       ('VIP30', 'Giảm 30% cho khách VIP', 30, 'percentage', 20, '2025-01-01', '2025-12-31', TRUE),
       ('BLACKFRIDAY', 'Giảm 70% toàn bộ menu', 70, 'percentage', 10, '2025-11-29', '2025-11-29', TRUE);



