CREATE TABLE Orders (
                        id SERIAL PRIMARY KEY,
                        order_number BIGINT NOT NULL UNIQUE,
                        order_type VARCHAR(255) NOT NULL,
                        status VARCHAR(255) NOT NULL,
                        customer_id INT NOT NULL,
                        payment_id INT not null,
                        total_price DECIMAL(19, 2) NOT NULL,
                        payment_method VARCHAR(255) NOT NULL,
                        created_date TIMESTAMP,
                        last_modified_date TIMESTAMP,
                        UNIQUE (order_number)
);
CREATE TABLE order_items (
                             id SERIAL PRIMARY KEY,
                             order_id INT NOT NULL,
                             book_id INT NOT NULL,
                             total_quantity INT NOT NULL,
                             price DECIMAL(19, 2) NOT NULL,
                             last_modified_date TIMESTAMP,
                             created_date TIMESTAMP,
                             CONSTRAINT fk_order_items_orders FOREIGN KEY (order_id) REFERENCES Orders (id) ON DELETE CASCADE

);
