Gradugation
===========

A UF-themed Android board game where players go through college with the ultimate goal of graduating. With the board representing the UF campus, players will land on a trail of dots representing locations, many of which will contain mini-games that earn them credits towards graduation.


How to run game
---------------
1. Go to the following links and download the zip files:

https://github.com/nicolasgramlich/AndEngine/tree/GLES2-AnchorCenter
https://github.com/nicolasgramlich/AndEngineTMXTiledMapExtension/tree/GLES2-AnchorCenter
https://github.com/nicolasgramlich/AndEnginePhysicsBox2DExtension/tree/GLES2-AnchorCenter

2. Extract the zip files.

3. Open Eclipse and import both projects (File->Import->Android-> Existing Android Code into Workspace)

4. Right click AndEngineTMXTiledMapExtension and open properties. Under Android-> libraries remove the current AndEngine project and add in the correct AndEngine

5. Right click AndEnginePhysicsBox2DExtension and open properties. Under Android-> libraries remove the current AndEngine project and add in the correct AndEngine.

5. Right click on the MainActivity project and open properties. Under Android -> libraries add in AndEngine, AndEngineTMXTiledMapExtension, and AndEnginePhysicsBox2DExtension.

6. Refresh and clean the project.

=================
Database Scheme

create table if not exists game(game_id integer, time_date text, num_of_players integer, current_player integer,
   primary key (game_id)
   );
create table if not exists character (id integer, type text, name text, x_coord integer, y_coord integer, credits integer, coins integer, player_order integer,
   primary key (id)
   );
create table if not exists items (id integer, name text, cost integer, type text, affects text, amount integer, text text,
   primary key (id)
   );
create table if not exists minigame_character (game_id integer, minigame_id integer, char_played(1-4) integer,
   primary key (game_id, minigame_id)
   );

=================

Common errors: 

- It might give you an error for not having Android API 17. Even if you have a newer version you must have version 17.
