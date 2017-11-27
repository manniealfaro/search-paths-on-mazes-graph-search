package problems.maze;

import java.util.ArrayList;

import problems.maze.MazeAction;
import problems.maze.MazeState;

import search.Action;
import search.Node;
import search.SearchProblem;
import search.State;

/**
 * Extends SearchProblem and implements the functions which define
 * the maze problem.
 */
public class MazeProblem implements SearchProblem {

    // Penalty factor for each wood wall bitten.
    private static final double PENALTY = 3;
    // Maximum number of bites.
    private static final int MAX_BITES = 4;

    /* Maze */
    private Maze maze;


    /** Constructors */
    public MazeProblem(int size, int seed){ this.maze = new Maze(size,seed);}
    public MazeProblem(Maze maze){ this.maze = maze;}


    /** Returns an state representing the initial position of the hamster */
    @Override
    public State initialState() {return new MazeState(0, maze.inputX, 0, false);}


    /**
     * Returns the result of applying an action.
     * If action movement goes to a wall, returns null, as the action should not be allowed.
     */
    @Override
    public State applyAction(State state, Action action) {
        // Casts the parameters
        MazeState mazeState = (MazeState) state;
        MazeAction mazeAction = (MazeAction) action;

        int newPosX = mazeState.x; // New positions
        int newPosY = mazeState.y; // New positions

        // If the action is EAT
        if ((mazeAction==MazeAction.EAT) && (maze.cells[newPosY][newPosX] == maze.CHEESE)){
            mazeState.hasEaten = true;

            return new MazeState(newPosY, newPosX, mazeState.getNumBites(), mazeState.getHasEaten());
        }


        // If the action is a MOVEMENT
        switch(mazeAction){
            case LEFT:
                newPosX--;
                break;
            case RIGHT:
                newPosX++;
                break;
            case UP:
                newPosY--;
                break;
            case DOWN:
                newPosY++;
                break;
        }

        // Returns null if trying to move out of the maze
        if ((newPosX<0) || (newPosX>maze.size-1) || (newPosY<0) || (newPosY>maze.size-1))
            return null;

        // If the new position is empty or cheese, returns the new state.
        if ((maze.cells[newPosY][newPosX] == maze.EMPTY) || (maze.cells[newPosY][newPosX] == maze.CHEESE)){
            return new MazeState(newPosY, newPosX, mazeState.getNumBites(), mazeState.getHasEaten());
        }
        //
        // If the new position is wood, depends on the bites remaining
        if ((maze.cells[newPosY][newPosX] == maze.WOOD) && (mazeState.numBites < MAX_BITES)){
            return new MazeState(newPosY, newPosX, mazeState.numBites + 1, mazeState.hasEaten);
        }


        // Otherwise is a WALL or a WOOD that can't be bitten
        return null;
    }


    /** Returns a list of the actions that can be applied over an state. */
    @Override
    public ArrayList<Action> getPossibleActions(State state) {
        // Casts the state
        MazeState mazeState = (MazeState) state;

        ArrayList<Action> possibleActions = new ArrayList<Action>();

        if(maze.cells[mazeState.getY()][mazeState.getX()] == maze.CHEESE)
            possibleActions.add(MazeAction.EAT);

        // Checks if it is possible to go UP
        if((mazeState.getY() - 1) >= 0) // Does not go outside the maze
            if((maze.cells[mazeState.getY() - 1][mazeState.getX()] == maze.EMPTY) || (maze.cells[mazeState.getY() - 1][mazeState.getX()] == maze.CHEESE) || (maze.cells[mazeState.getY() - 1][mazeState.getX()] == maze.WOOD && mazeState.getNumBites() < MAX_BITES) && (maze.cells[mazeState.getY() - 1][mazeState.getX()] != maze.WALL))
                possibleActions.add(MazeAction.UP);

        // Checks if it is possible to go LEFT
        if((mazeState.getX() - 1) >= 0) // Does not go outside the maze
            if((maze.cells[mazeState.getY()][mazeState.getX() - 1] == maze.EMPTY) || (maze.cells[mazeState.getY()][mazeState.getX() - 1] == maze.CHEESE) || (maze.cells[mazeState.getY()][mazeState.getX() - 1] == maze.WOOD && mazeState.getNumBites() < MAX_BITES) && (maze.cells[mazeState.getY()][mazeState.getX() - 1] != maze.WALL))
                possibleActions.add(MazeAction.LEFT);

        // Checks if it is possible to go RIGHT
        if((mazeState.getX() + 1) < maze.size) // Does not go outside the maze
            if((maze.cells[mazeState.getY()][mazeState.getX() + 1] == maze.EMPTY) ||(maze.cells[mazeState.getY()][mazeState.getX() + 1] == maze.CHEESE) || (maze.cells[mazeState.getY()][mazeState.getX() + 1] == maze.WOOD && mazeState.getNumBites() < MAX_BITES) && (maze.cells[mazeState.getY()][mazeState.getX() + 1] != maze.WALL))
                possibleActions.add(MazeAction.RIGHT);

        // Checks if it is possible to go DOWN
        if((mazeState.getY() + 1) < maze.size) // Does not go outside the maze
            if((maze.cells[mazeState.getY() + 1][mazeState.getX()] == maze.EMPTY) || (maze.cells[mazeState.getY() + 1][mazeState.getX()] == maze.CHEESE) || (maze.cells[mazeState.getY() + 1][mazeState.getX()] == maze.WOOD && mazeState.getNumBites() < MAX_BITES) && (maze.cells[mazeState.getY() + 1][mazeState.getX()] != maze.WALL))
                possibleActions.add(MazeAction.DOWN);

        return possibleActions;
    }

    /**
     *  Here, the cost depends on the state.
     *  If the new cell is wood, the cost corresponds to three times the number of bites so far.
     *  Otherwise, it returns 1. For eating, the cost is five */
    @Override
    public double cost(State state, Action action) {
        // Casts the parameters
        MazeState mazeState = (MazeState) state;
        MazeAction mazeAction = (MazeAction) action;

        // If the action is EAT
        if (mazeAction == MazeAction.EAT)
            return 5.0;

        // Otherwise, it is a MOVEMENT.
        int newPosX = mazeState.x;
        int newPosY = mazeState.y;
        // Calculates the new position.
        switch(mazeAction){
            case LEFT:
                newPosX--;
                break;
            case RIGHT:
                newPosX++;
                break;
            case UP:
                newPosY--;
                break;
            case DOWN:
                newPosY++;
                break;
        }
        // Returns infinity when moving out the maze if trying to move out of the maze
        if ((newPosX<0) || (newPosX>maze.size-1) || (newPosY<0) || (newPosY>maze.size-1))
            return Double.POSITIVE_INFINITY;


        if ((maze.cells[newPosY][newPosX] == Maze.EMPTY) || (maze.cells[newPosY][newPosX] == Maze.CHEESE)){
            return 1;
        }

        if (maze.cells[newPosY][newPosX] == maze.WOOD)
            if (mazeState.numBites < MAX_BITES)
                return (mazeState.getNumBites() + 1) * PENALTY;

        // Otherwise is a WALL or a WOOD that can't be bitten
        return Double.POSITIVE_INFINITY;
    }


    /** Tests if a state is the goal. Tests the position and hasEaten.*/
    public boolean testGoal(State state) {

        MazeState mazeState = (MazeState) state;

        MazeState finish = new MazeState(maze.size - 1,maze.outputX);

        if (mazeState.getY() == finish.getY() && mazeState.getX() == finish.getX() && mazeState.getHasEaten()) {
            return true;
        }


        return false;

    }


    /** Returns the heuristic value of an state. */
    @Override
    public double heuristic(State state) {

        MazeState mazeState = (MazeState) state;
        MazeState finish = new MazeState(maze.size-1, maze.outputX);

        double euclidean = Integer.MAX_VALUE;

        double difX = finish.x - mazeState.x;
        double difY = finish.y - mazeState.y;

        euclidean = Math.sqrt((difX * difX) + (difY * difY));

        return euclidean;

    }
}


