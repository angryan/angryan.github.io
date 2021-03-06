import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Ryan on 4/3/2017.
 */
/** This main file is the file you want to run. It parses the input file given and forwards the inputs to the actual program.
 *  The inputs into the program should be put into a file called input.txt put into the same folder as the program.'
 *  HOW TO RUN: Create a file called input.txt that is in the same format as the examples given. Put it in the same folder as Main.java. Then run.**/
public class Main {
    public static void main(String[] args) {
        File newFile = new File("input.txt");/** Reads the file **/
        try {
            Scanner s = new Scanner(newFile);
            String dimensions = s.nextLine();
            String[] dimensionsSplit = dimensions.split(" ");
            Grid puzzle = new Grid(Integer.parseInt(dimensionsSplit[0]), Integer.parseInt(dimensionsSplit[1]));
            HashMap<Integer, List<Integer>> blocked = new HashMap<>();
            String blockedpoints = s.nextLine();
            ArrayList<Integer> digits = new ArrayList<>();
            for (int i = 0; i < blockedpoints.length(); i++) { /**Reads the blocked points from the file and puts it into a variable */
                char current = blockedpoints.charAt(i);
                if (Character.isDigit(current)) {
                    int digit = Character.getNumericValue(current);
                    digits.add(digit);
                }
            }
            for (int i = 0; i < digits.size(); i = i + 2) {
                if (blocked.keySet().contains(digits.get(i))) {
                    blocked.get(digits.get(i)).add(digits.get(i + 1));
                } else {
                    ArrayList<Integer> newList = new ArrayList<>();
                    newList.add(digits.get(i + 1));
                    blocked.put(digits.get(i), newList);
                }
            }
            puzzle.setBlockedPoints(blocked); /** Sets the blocked points **/
            HashMap<List<Integer>, List<Integer>> StartEnd = new HashMap<>();
            HashMap<List<Integer>, List<Integer>> EndStart = new HashMap<>();
            digits.clear();
            String jumpPoints = s.nextLine();
            for (int i = 0; i < jumpPoints.length(); i++) { /**Reads the jumping points**/
                char current = jumpPoints.charAt(i);
                if (Character.isDigit(current)) {
                    int digit = Character.getNumericValue(current);
                    digits.add(digit);
                }
            }
            for (int i = 0; i < digits.size(); i = i + 4) {
                ArrayList<Integer> start = new ArrayList<>();
                ArrayList<Integer> end = new ArrayList<>();
                start.add(0, digits.get(i));
                start.add(1, digits.get(i + 1));
                end.add(0, digits.get(i + 2));
                end.add(1, digits.get(i + 3));
                StartEnd.put(start, end);
                EndStart.put(end, start);
            }
            puzzle.setJumpsEndStart(EndStart);/**Sets the jumping points.**/
            puzzle.setJumpsStartEnd(StartEnd);
            System.out.println(puzzle.ways());
        } catch (FileNotFoundException e) { /**Catches the exception that the file does not exist**/
            System.out.println("Cannot find file");
        }
    }
}
