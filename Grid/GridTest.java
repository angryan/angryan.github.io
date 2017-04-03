/**
 * Created by Ryan on 3/28/2017.
 */
import org.junit.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GridTest {
    @Test
    public void testing() {
        Grid test = new Grid(5, 5);
        ArrayList<Integer> endpointone = new ArrayList<>();
        endpointone.add(0, 0);
        endpointone.add(1, 3);
        ArrayList<Integer> startpoint = new ArrayList<>();
        startpoint.add(0, 2);
        startpoint.add(1, 1);
        HashMap<List<Integer>, List<Integer>> jumpsStartEnd = new HashMap<>();
        HashMap<List<Integer>, List<Integer>> jumpsEndStart = new HashMap<>();
        HashMap<Integer, List<Integer>> blocked = new HashMap<>();
        jumpsEndStart.put(endpointone, startpoint);
        jumpsStartEnd.put(startpoint, endpointone);
        ArrayList<Integer> a = new ArrayList<>();
        a.add(0, 1);
        blocked.put(2, a);
        test.blockedpoints = blocked;
        System.out.println(test.ways());
        ArrayList<Integer> point = new ArrayList<>();
        point.add(0, 4);
        point.add(1, 3);

    }
}
