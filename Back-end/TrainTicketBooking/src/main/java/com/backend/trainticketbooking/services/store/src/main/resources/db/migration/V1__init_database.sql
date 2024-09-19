CREATE TABLE address (
                         id SERIAL PRIMARY KEY,
                         street VARCHAR(255),
                         city VARCHAR(255),
                         state VARCHAR(255),
                         postal_code VARCHAR(20),
                         created_date TIMESTAMP,
                         last_modified_date TIMESTAMP
);

CREATE TABLE train (
                       id SERIAL PRIMARY KEY,
                       train_number VARCHAR(255) NOT NULL UNIQUE ,
                       train_name VARCHAR(255) NOT NULL UNIQUE ,
                       train_type VARCHAR(50),
                       capacity INTEGER NOT NULL,
                       total_rail_cars INTEGER NOT NULL,
                        created_date TIMESTAMP,
                       last_modified_date TIMESTAMP
);

CREATE TABLE railcar (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL UNIQUE,
                         railcar_type VARCHAR(50) NOT NULL,
                         capacity INTEGER NOT NULL,
                         seat_per_row INTEGER NOT NULL,
                         is_have_floor BOOLEAN NOT NULL,
                         train_id INTEGER,
                         created_date TIMESTAMP,
                         last_modified_date TIMESTAMP

);

CREATE TABLE seat (
                      id SERIAL PRIMARY KEY,
                      seat_number VARCHAR(50) NOT NULL,
                      seat_class VARCHAR(50),
                      seat_type VARCHAR(50),
                      is_available BOOLEAN NOT NULL DEFAULT TRUE,
                      price NUMERIC(10, 2) NOT NULL,
                      ticket_id INTEGER,
                      railcar_id INTEGER NOT NULL,
                      created_date TIMESTAMP,
                      last_modified_date TIMESTAMP

);

CREATE TABLE route (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL UNIQUE,
                       created_date TIMESTAMP,
                       last_modified_date TIMESTAMP
);

CREATE TABLE station (
                         id SERIAL PRIMARY KEY,
                         address_id INTEGER UNIQUE REFERENCES address(id) ON DELETE SET NULL,
                         name VARCHAR(255) NOT NULL,
                         created_date TIMESTAMP,
                         last_modified_date TIMESTAMP
);

CREATE TABLE schedule (
                          id SERIAL PRIMARY KEY,
                          train_id INTEGER NOT NULL REFERENCES train(id) ON DELETE CASCADE,
                          route_id INTEGER NOT NULL REFERENCES route(id) ON DELETE CASCADE,
                          created_date TIMESTAMP,
                          last_modified_date TIMESTAMP
);



CREATE TABLE schedule_station (
                                  id SERIAL PRIMARY KEY,
                                  schedule_id INTEGER NOT NULL REFERENCES schedule(id) ON DELETE CASCADE,
                                  station_id INTEGER NOT NULL REFERENCES station(id) ON DELETE CASCADE,
                                  departure_time TIMESTAMP NOT NULL,
                                  arrival_time TIMESTAMP NOT NULL,
                                  stop_sequence INTEGER NOT NULL,
                                  actual_departure_time TIMESTAMP,
                                  actual_arrival_time TIMESTAMP,
                                  actual_travel_time  BIGINT,
                                  created_date TIMESTAMP,
                                  last_modified_date TIMESTAMP,
                                  CONSTRAINT unique_schedule_station UNIQUE (schedule_id, station_id) -- Đảm bảo không có ga trùng lặp cho cùng một lịch trình
);

CREATE TABLE route_station (
                               id SERIAL PRIMARY KEY,
                               route_id INTEGER NOT NULL REFERENCES route(id) ON DELETE CASCADE,
                               station_id INTEGER NOT NULL REFERENCES station(id) ON DELETE CASCADE,
                               stop_order INTEGER NOT NULL,
                               created_date TIMESTAMP,
                               last_modified_date TIMESTAMP,
                               CONSTRAINT unique_route_station UNIQUE (route_id, station_id)
);

CREATE TABLE ticket (
                        id SERIAL PRIMARY KEY,
                        customer_id INTEGER,
                        price DECIMAL(10, 2),
                        created_date TIMESTAMP,
                        last_modified_date TIMESTAMP,
                        departure_station_id INTEGER NOT NULL REFERENCES station(id) ON DELETE SET NULL,
                        arrival_station_id INTEGER NOT NULL REFERENCES station(id) ON DELETE SET NULL,
                        schedule_id INTEGER NOT NULL REFERENCES schedule(id) ON DELETE CASCADE
);



