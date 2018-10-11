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
                   email VARCHAR NOT NULL, -- should be NOT NULL, change later
                   salt BIGINT NOT NULL,
                   password_hash UUID NOT NULL,
                   failed_logins INT, -- counter for failed logins
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
                              user_id INT AUTO_INCREMENT NOT NULL,
                              PRIMARY KEY (ride_id, user_id),
                              FOREIGN KEY (ride_id) REFERENCES rides (ride_id),
                              FOREIGN KEY (user_id) REFERENCES users (user_id));


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
VALUES            (1, 'Admin', 'adminmail', -2883142073796788660, '8dc0e2ab-4bf1-7671-c0c4-d22ffb55ee59'),
                  (2, 'Test', 'testmail', 5336889820313124494, '144141f3-c868-85e8-0243-805ca28cdabd');

INSERT INTO locations(location_name, latitude, longitude)
VALUES               ('Stockholm', 59.329323, 18.068581),
                     ('Göteborg', 57.708870, 11.974560),
                     ('Malmö', 55.604980, 13.003822),
                     ('Uppsala', 59.878266, 17.643702),
                     ('Upplands Väsby', 59.538308, 17.926775),
                     ('Västerås', 59.630936, 16.546521),
                     ('Örebro', 59.299903, 15.212260),
                     ('Linköping', 58.431197, 15.630243),
                     ('Helsingborg', 56.056734, 12.692919),
                     ('Jönköping', 57.803792, 14.163872),
                     ('Norrköping', 58.611097, 16.189953),
                     ('Lund', 55.717485, 13.188418),
                     ('Umeå', 63.842679, 20.270986),
                     ('Gävle', 60.690043, 17.138849),
                     ('Borås', 57.743832, 12.946563),
                     ('Södertälje', 59.201368, 17.626400),
                     ('Eskilstuna', 59.372188, 16.512083),
                     ('Halmstad', 56.694877, 12.861213),
                     ('Växjö', 56.879191, 14.806427),
                     ('Karlstad', 59.404193, 13.508499),
                     ('Sundsvall', 62.394233, 17.308315),
                     ('Östersund', 63.176907, 14.639859),
                     ('Trollhättan', 58.291553, 12.286609),
                     ('Lidingö', 59.366828, 18.151430),
                     ('Borlänge', 60.488560, 15.435402),
                     ('Tumba', 59.199231, 17.829442),
                     ('Kristianstad', 56.031165, 14.156673),
                     ('Kalmar', 56.664621, 16.357218),
                     ('Falun', 60.606313, 15.636798),
                     ('Skövde', 58.391492, 13.847495),
                     ('Karlskrona', 56.162302, 15.588118),
                     ('Skellefteå', 64.751892, 20.950252),
                     ('Uddevalla', 58.352622, 11.931904),
                     ('Varberg', 57.107896, 12.252010),
                     ('Åkersberga', 59.479832, 18.311296),
                     ('Örnsköldsvik', 63.292100, 18.719549),
                     ('Landskrona', 55.873192, 12.830797),
                     ('Nyköping', 58.753309, 17.008488),
                     ('Vallentuna', 59.586934, 18.206049),
                     ('Motala', 58.537195, 15.047099),
                     ('Trelleborg', 55.377146, 13.159269),
                     ('Ängelholm', 56.246497, 12.863712),
                     ('Karlskoga', 59.330226, 14.538729),
                     ('Märsta', 59.619842, 17.857571),
                     ('Lerum', 57.805208, 12.302310),
                     ('Alingsås', 57.931159, 12.535707),
                     ('Sandviken', 60.623032, 16.775531),
                     ('Kungälv', 57.872428, 11.975062),
                     ('Enköping', 59.635881, 17.077615),
                     ('Hässleholm', 56.159872, 13.765966);

INSERT INTO rides(departure_time, arrival_time, nbr_seats, driver_id, departure_location, destination)
VALUES           ('2018-01-01 12:00:00', '2018-01-01 15:00:00', 4, 1, 'Helsingborg', 'Göteborg'),
                 ('2018-01-02 14:00:00', '2018-01-02 20:00:00', 4, 2, 'Hässleholm', 'Stockholm'),
                 ('2018-01-03 12:00:00', '2018-01-01 13:00:00', 4, 1, 'Malmö', 'Helsingborg');

INSERT INTO ride_passengers(ride_id, user_id)
VALUES                      (1, 2),
                            (2, 1),
                            (3, 2);

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
