CREATE TABLE Actor (
                       ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                       Name VARCHAR(255),
                       surname varchar(255)
);

CREATE TABLE Actor_movie (
                       ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                       actor_id INT,
                       movie_id INT,
                       FOREIGN KEY (actor_id) REFERENCES Actor(ID),
                       FOREIGN KEY (movie_id) REFERENCES Movie(ID)
);
