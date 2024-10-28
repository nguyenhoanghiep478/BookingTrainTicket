CREATE TABLE Orders (
                        id SERIAL PRIMARY KEY,
                        order_number BIGINT NOT NULL UNIQUE,
                        status VARCHAR(255) NOT NULL,
                        customer_id INT,
                        ticket_id INT not null,
                        payment_id INT,
                        total_price DECIMAL(19, 2) NOT NULL,
                        payment_method VARCHAR(255),
                        created_date TIMESTAMP,
                        last_modified_date TIMESTAMP,
                        UNIQUE (order_number,customer_id)
);
CREATE TABLE order_item (
                             id SERIAL PRIMARY KEY,
                             order_id INT NOT NULL,
                             seat_id INT NOT NULL,
                             price DECIMAL(19, 2) NOT NULL,
                             last_modified_date TIMESTAMP,
                             created_date TIMESTAMP,
                             CONSTRAINT fk_order_items_orders FOREIGN KEY (order_id) REFERENCES Orders (id) ON DELETE CASCADE

);
