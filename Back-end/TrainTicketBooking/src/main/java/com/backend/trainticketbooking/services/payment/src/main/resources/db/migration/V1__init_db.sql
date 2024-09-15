CREATE TABLE payment (
                        id SERIAL PRIMARY KEY,
                        payment_method VARCHAR(255),
                        order_number BIGINT,
                        total_amount DOUBLE PRECISION,
                        created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);