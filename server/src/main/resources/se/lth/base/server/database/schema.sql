-- This is the schema file that the database is initialized with. It is specific to the H2 SQL dialect.
-- Author: Rasmus Ros, rasmus.ros@cs.lth.se
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS rides;
DROP TABLE IF EXISTS ride_passengers;
DROP TABLE IF EXISTS session;
DROP TABLE IF EXISTS foo;

-- User roles describe what each user can do on a generic level.
CREATE TABLE user_role(role_id TINYINT,
                       role VARCHAR(10) NOT NULL UNIQUE,
                       PRIMARY KEY (role_id));

CREATE TABLE users(user_id INT AUTO_INCREMENT NOT NULL,
                   role_id TINYINT,
                   username VARCHAR_IGNORECASE NOT NULL UNIQUE, -- username should be unique
                   email TEXT,
                   salt BIGINT NOT NULL,
                   password_hash UUID NOT NULL,
                   PRIMARY KEY (user_id),
                   FOREIGN KEY (role_id) REFERENCES user_role (role_id),
                   CHECK (LENGTH(username) >= 4)); -- ensures that username have 4 or more characters

CREATE TABLE locations (location_name VARCHAR NOT NULL,
                        latitude      FLOAT NOT NULL,
                        longitude     FLOAT NOT NULL);

CREATE TABLE rides (ride_id            INT AUTO_INCREMENT NOT NULL,
                    departure_time     DATETIME NOT NULL,
                    arrival_time       DATETIME NOT NULL,
                    nbr_seats          INT NOT NULL,
                    driver_id          INT AUTO_INCREMENT NOT NULL,
                    departure_location VARCHAR NOT NULL,
                    destination        VARCHAR NOT NULL,
                    PRIMARY KEY (ride_id),
                    FOREIGN KEY (driver_id) REFERENCES users (user_id),
                    FOREIGN KEY (departure_location) REFERENCES locations (location_name),
                    FOREIGN KEY (destination) REFERENCES locations (location_name));

CREATE TABLE ride_passengers (ride_id INT AUTO_INCREMENT NOT NULL,
                              passenger_id INT AUTO_INCREMENT NOT NULL,
                              PRIMARY KEY (ride_id, passenger_id),
                              FOREIGN KEY (ride_id) REFERENCES rides (ride_id),
                              FOREIGN KEY (passenger_id) REFERENCES users (user_id));


-- Sessions are indexed by large random numbers instead of a sequence of integers, because they could otherwise
-- be guessed by a malicious user.¥
CREATE TABLE session(session_uuid UUID DEFAULT RANDOM_UUID(),
                     user_id INT NOT NULL,
                     last_seen TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                     PRIMARY KEY(session_uuid),
                     FOREIGN KEY(user_id) REFERENCES users (user_id) ON DELETE CASCADE);


INSERT INTO user_role(role_id, role)
VALUES               (1, 'ADMIN'), (2, 'USER');

INSERT INTO users(role_id, username, email, salt, password_hash)
VALUES            (1, 'Admin', 'sven.svensson@gmail.com', -2883142073796788660, '8dc0e2ab-4bf1-7671-c0c4-d22ffb55ee59'),
                  (2, 'Test', 'robert@hotmail.com', 5336889820313124494, '144141f3-c868-85e8-0243-805ca28cdabd');

INSERT INTO locations(location_name, latitude, longitude)
VALUES               ('Stockholm', 59.329323, 18.068581),
                     ('Göteborg', 57.708870, 11.974560),
                     ('Malmö', 55.604980, 13.003822);
-- Example table containing some data per user, you are expected to remove this table in your project.
CREATE TABLE foo(
    -- First the four columns are specified:
    foo_id INT AUTO_INCREMENT,
    -- foo_id is the first column with type INT. This is used to uniquely identify each foo. In this way, the foo can be
    -- deleted or updated by referring only to its foo_id. The AUTO_INCREMENT keyword is H2 specific and indicates
    -- that the column is supplied with a default value that is incremented for each row. The first row
    -- will automatically get foo_id = 1, the second one will get foo_id = 2, and so on.

    payload VARCHAR NOT NULL,
    -- payload is the second column with type VARCHAR. This is the data that is typed in the input field
    -- on the foo tab in the front end. There is no limit to how long the string can be.
    -- NOT NULL specifies that the row must have a payload.

    user_id INT NOT NULL,
    -- user_id is the third column with type INT. This keeps track of who the user is, so that each user has their own
    -- foos only.

    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL,
    -- created is the fourth and final column with type TIMESTAMP. This column also has a default value
    -- which is created by the function CURRENT_TIMESTAMP() if not supplied during creation.

    -- Here are some additional constraints that the data must relate to:

    PRIMARY KEY(foo_id),
    -- This defines foo_id as the unique identifier of the table. It adds NOT NULL to the column and
    -- enforces that the values rows all have a unique identifier.

    FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE
    -- This informs that the column user_id is a relation to another table's primary key. In combination
    -- with the NOT NULL constraint above it is not possible to enter data that is not connected to a user.
    -- Note that there can be multiple rows with the same user_id (but the foo_id is unique for each row).
    -- The ON DELETE CASCADE ensures that when a user is deleted then all their foo data will also be deleted.
);
