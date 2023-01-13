CREATE TABLE man (id REAL, name TEXT PRIMARY KEY, age INTEGER, driver_license BOOLEAN, car_id TEXT REFERENCES car(id));
CREATE TABLE car (id REAL, model TEXT PRIMARY KEY, brand TEXT, price NUMERIC);