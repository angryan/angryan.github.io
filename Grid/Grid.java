
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ryan on 3/28/2017.
 */
/** Note: This is a recursive solution **/
public class Grid {
    int n;
    int m;
    HashMap<Integer, List<Integer>> blockedpoints; /** HashMap of the points that are blocked **/
    HashMap<List<Integer>, List<Integer>> jumpsStartEnd; /** HashMap of the jumping points with the start as the keys and end as the values.**/
    HashMap<List<Integer>, List<Integer>> jumpsEndStart; /** HashMap of the jumping points with the end as the keys and start as the values.**/
    HashMap<List<Integer>, Integer> memo = new HashMap<>(); /** Used to optimize the recursion.**/
    public Grid(int n, int m) {
        this.n = n;
        this.m = m;
        blockedpoints = new HashMap<>();
        jumpsEndStart = new HashMap<>();
        jumpsStartEnd = new HashMap<>();
        if (n <= 0 || m <= 0) { /** Checks if dimensions are valid. **/
            throw new IllegalArgumentException("Invalid dimensions.");
        }
        ArrayList<Integer> zero = new ArrayList<>(); /** Adds the point (0,0) to the memo, stating that there is 1 path there **/
        zero.add(0, 0);
        zero.add(1, 0);
        memo.put(zero, 1);
    }

    private int calculate(int n, int m) {
        ArrayList<Integer> point = new ArrayList<>();
        point.add(0, n);
        point.add(1, m);
        for (List<Integer> s: jumpsStartEnd.keySet()) { /**Checks if the jumping points dont loop forever.**/
            if (s.get(0) >= jumpsStartEnd.get(s).get(0) && s.get(1) >= jumpsStartEnd.get(s).get(1)) {
                throw new IllegalArgumentException("Invalid jumping points");
            }
        }
        if (memo.containsKey(point)) {
            return memo.get(point);
        }
        if (blockedpoint(n, m)) { /**Checks if the point is blocked**/
            memo.put(point, 0);
            return 0;
        } else if (jumpend(n, m)) { /**Checks if the point is the end of a jump**/
            ArrayList<Integer> a = (ArrayList<Integer>) correspondingJumpStart(n, m);
            if (jumpstart(n - 1, m) && jumpstart(m, n - 1)) {
                memo.put(point, calculate(a.get(0), a.get(1)));
                return memo.get(point);
            } else if (jumpstart(n - 1, m)) { /**Checks if there is a starting jump point next to or above it**/
                memo.put(point, calculate(a.get(0), a.get(1)) + calculate(n, m - 1));
                return memo.get(point);
            } else if (jumpstart(n, m - 1)) {
                memo.put(point, calculate(a.get(0), a.get(1)) + calculate(n - 1, m));
                return memo.get(point);
            }
            memo.put(point, calculate(a.get(0), a.get(1)) + calculate(n - 1, m) + calculate(n, m - 1));
            return memo.get(point);
        } else if (n == 0 && m == 0) { /**Base cases**/
            return memo.get(point);
        } else if (n < 0 || m < 0) {
            return 0;
        }
        else {
            if (jumpstart(n - 1, m) && jumpstart(m, n - 1)) {
                memo.put(point, 0);
                return memo.get(point);
            } else if (jumpstart(n - 1, m)) {/**Checks if there is a starting jump point next to or above it**/
                memo.put(point, calculate(n, m - 1));
                return memo.get(point);
            } else if (jumpstart(n, m - 1)) {
                memo.put(point, calculate(n - 1, m));
                return memo.get(point);
            }
            memo.put(point, calculate(n - 1, m) + calculate(n, m - 1));
            return memo.get(point);
        }
    }

    private boolean blockedpoint(int n, int m) {/** Helper function: return whether or not a point is blocked.**/
        if (blockedpoints.containsKey(n)) {
            List yvalues = blockedpoints.get(n);
            return yvalues.contains(m);
        }
        return false;
    }

    private boolean jumpend(int n, int m) {/**Helper function: return whether or not a point ia the end of a jump.**/
        ArrayList<Integer> endpoint = new ArrayList<>();
        endpoint.add(0, n);
        endpoint.add(1, m);
        return jumpsStartEnd.containsValue(endpoint);
    }

    private boolean jumpstart(int n, int m) {/**Helper function: return whether or not a point is the start of a jump.**/
        ArrayList<Integer> startpoint = new ArrayList<>();
        startpoint.add(0, n);
        startpoint.add(1, m);
        return jumpsStartEnd.containsKey(startpoint);
    }

    private List correspondingJumpStart(int n, int m) {/**Helper function: return the start point that corresponds the the appropriate end point**/
        ArrayList<Integer> endpoint = new ArrayList<>();
        endpoint.add(0, n);
        endpoint.add(1, m);
        return jumpsEndStart.get(endpoint);
    }

    public int ways() {/**return the number of ways to reach the bottom right corner of the grid.**/
        try {
            return calculate(n - 1, m - 1);
        } catch (StackOverflowError e) {
            throw new StackOverflowError("Invalid jumping points.");
        }
    }

    public void setBlockedPoints(HashMap blocked) { /** Setter method for setting blocked points.**/
        this.blockedpoints = blocked;
    }

    public void setJumpsStartEnd(HashMap jumpsStartEnd) { /** Setter method for setting the first set of jump points.**/
        this.jumpsStartEnd = jumpsStartEnd;
    }

    public void setJumpsEndStart(HashMap jumpsEndStart) { /** Setter method for the second set.**/
        this.jumpsEndStart = jumpsEndStart;
    }
}
