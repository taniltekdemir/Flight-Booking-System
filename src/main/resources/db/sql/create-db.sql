CREATE TABLE payment (
  id    BIGINT PRIMARY KEY,
  price DECIMAL(30, 8) NOT NULL
);

CREATE TABLE flight (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  flight_name VARCHAR(100),
  decription TEXT,
  departure_time TIMESTAMP,
  arrival_time TIMESTAMP
);

CREATE TABLE seat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    flight_id BIGINT,
    seat_number VARCHAR(10),
    price DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    FOREIGN KEY (flight_id) REFERENCES flight(id)
);



CREATE TABLE flight_payment (
    id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    price DECIMAL(30, 8) NOT NULL,
    seat_id BIGINT,
    user_id BIGINT,
    status VARCHAR(20),
    payment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);