insert into genres (name) values ('Action');
insert into genres (name) values ('Adventure');
insert into genres (name) values ('RPG');
insert into genres (name) values ('Platform');


insert into platforms (name) values ('Playstation 4');
insert into platforms (name) values ('Playstation 3');


insert into games (name, release_year, platform_id) values ('Uncharted 2', 2009, 2);
insert into games (name, release_year, platform_id) values ('Uncharted 3', 2011, 2);
insert into games (name, release_year, platform_id) values ('Uncharted 4', 2016, 1);
insert into games (name, release_year, platform_id) values ('Kingdom Hearts 3', 2020, 1);

insert into game_genres (game_id, genre_id) values (1, 1);
insert into game_genres (game_id, genre_id) values (1, 2);
insert into game_genres (game_id, genre_id) values (2, 1);
insert into game_genres (game_id, genre_id) values (2, 2);
insert into game_genres (game_id, genre_id) values (3, 1);
insert into game_genres (game_id, genre_id) values (3, 2);
insert into game_genres (game_id, genre_id) values (4, 1);
insert into game_genres (game_id, genre_id) values (4, 3);