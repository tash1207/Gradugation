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

Common errors: 

- It might give you an error for not having Android API 17. Even if you have a newer version you must have version 17.
