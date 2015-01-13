package com.peach.os.util;

public class Collision {
	
    public static final int BLOCKED = 0x100;
    public static final int INVALID = 0x200000 | 0x40000;

    public static final int WALL_NORTH_WEST = 0x1 | 0x200;
    public static final int WALL_NORTH = 0x2 | 0x400;
    public static final int WALL_NORTH_EAST = 0x4 | 0x800;
    public static final int WALL_EAST = 0x8 | 0x1000;
    public static final int WALL_SOUTH_EAST = 0x10 | 0x2000;
    public static final int WALL_SOUTH = 0x20 | 0x4000;
    public static final int WALL_SOUTH_WEST = 0x40 | 0x8000;
    public static final int WALL_WEST = 0x80 | 0x10000;
    
    public static final int ALL_BLOCKED = BLOCKED | INVALID;
    public static final int ALL_WALLS = WALL_NORTH_WEST | WALL_NORTH | WALL_NORTH_EAST | WALL_EAST | WALL_SOUTH_EAST |
    		WALL_SOUTH | WALL_SOUTH_WEST | WALL_WEST;
    
    public static final int LANDSCAPE_BLOCKED = 0x1;
    public static final int LANDSCAPE_BRIDGE = 0x2;
    public static final int LANDSCAPE_FLOOR = 0x4;
    public static final int LANDSCAPE_TREE_CANOPY = 0x8;
    public static final int LANDSCAPE_BLOCKED_2 = 0x16;
    public static final int LANDSCAPE_BLOCKED_3 = 0x17;
    public static final int LANDSCAPE_WALL_BOTTOM = 0x20;
    public static final int LANDSCAPE_WALL_TOP = 0x24;
    public static final int LANDSCAPE_WALL_TOP_2 = 0x28;
  
    public static final int LANDSCAPE_ALL_BLOCKED = LANDSCAPE_BLOCKED | LANDSCAPE_TREE_CANOPY | LANDSCAPE_BLOCKED_2 | LANDSCAPE_BLOCKED_3 |
    		LANDSCAPE_WALL_BOTTOM | LANDSCAPE_WALL_TOP | LANDSCAPE_WALL_TOP_2;
}


