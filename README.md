# Redemption - RPG Game

A Java-based 2D RPG game featuring combat, exploration, NPCs, and various game mechanics.

## Features

- **Character System**: Player character with multiple stats and abilities
- **Combat System**: Real-time combat with weapons, shields, and projectiles
- **NPC Interactions**: Talk to merchants and other NPCs
- **Monster AI**: Intelligent enemies with pathfinding
- **Inventory System**: Collect items, weapons, and equipment
- **Sound Effects**: Immersive audio experience
- **Map System**: Explore different areas including house interiors

## Game Objects

- **Weapons**: Various swords including Bloodthirst and Nightfall
- **Shields**: Normal and metal shields for defense
- **Items**: Hearts for health, meat for healing, coins for currency
- **Projectiles**: Arrows and fireballs for ranged combat

## Monsters

- Skeleton warriors
- Bone skeletons
- Bow skeletons with ranged attacks

## How to Run

1. Make sure you have Java installed on your system
2. Compile the Java files in the `src` directory
3. Run the `Main` class to start the game

```bash
# Navigate to the src directory
cd src

# Compile all Java files
javac main/*.java Characters/*.java monster/*.java object/*.java ai/*.java

# Run the game
java main.Main
```

## Controls

- Use arrow keys or WASD to move
- Attack with designated combat keys
- Interact with NPCs and objects

## Project Structure

- `src/main/`: Core game engine and main classes
- `src/Characters/`: Player and NPC character classes
- `src/monster/`: Enemy monster classes
- `src/object/`: Game items and weapons
- `src/ai/`: Artificial intelligence for NPCs and monsters
- Various image directories for sprites and graphics
- `src/sound/`: Audio files
- `src/maps/`: Level layout files

## Development

This game is built using Java Swing for the GUI and follows object-oriented programming principles with separate classes for different game entities.
