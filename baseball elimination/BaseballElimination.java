import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;
import java.util.Hashtable;
import java.util.ArrayList;

public class BaseballElimination
{
    private int numberOfTeams = 0;
    private Hashtable<String, Integer> teams;
    private int[] wins;
    private int[] losses;
    private int[] remaining;
    private int[][] against;
    private FlowNetwork flowNetwork;
    private FordFulkerson fordFulkerson;
    
    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename)
    {
        In file = new In(filename);
        
        numberOfTeams = file.readInt();
        
        teams = new Hashtable<String, Integer>();
        wins = new int[numberOfTeams];
        losses = new int[numberOfTeams];
        remaining = new int[numberOfTeams];
        against = new int[numberOfTeams][numberOfTeams];
        
        for (int i = 0; i < numberOfTeams; i++)
        {
            teams.put(file.readString(), i);
            wins[i] = file.readInt();
            losses[i] = file.readInt();
            remaining[i] = file.readInt();
            
            for (int j = 0; j < numberOfTeams; j++)
            {
                against[i][j] = file.readInt();
            }    
        }
    }
    
    // number of teams
    public int numberOfTeams()
    {
        return numberOfTeams;
    }
    
    // all teams
    public Iterable<String> teams()
    {
        return teams.keySet();
    }
    
    // number of wins for given team
    public int wins(String team)
    {
        verifyInput(team);
        
        int teamNumber = teams.get(team);
        return wins[teamNumber];
    }
    
    // number of losses for given team
    public int losses(String team)
    {
        verifyInput(team);
        
        int teamNumber = teams.get(team);
        return losses[teamNumber];
    }
    
    // number of remaining games for given team
    public int remaining(String team)
    {
        verifyInput(team);
        
        int teamNumber = teams.get(team);
        return remaining[teamNumber];
    }
    
    // number of remaining games between team1 and team2
    public int against(String team1, String team2)
    {
        verifyInput(team1);
        verifyInput(team2);
        
        int teamNumber1 = teams.get(team1);
        int teamNumber2 = teams.get(team2);
        return against[teamNumber1][teamNumber2];
    }
    
    // is given team eliminated?
    public boolean isEliminated(String team)
    {
        verifyInput(team);
        
        if (trivialElimination(team) != null)
        {
            return true;
        }
        
        int teamX = teams.get(team);
        int maxWinsforX = wins[teamX] + remaining[teamX];
        
        int matchScenarios = 0;
        for (int i = numberOfTeams - 1; i > 0; i--)
        {
            matchScenarios += i;
        }
        
        int vertices = 2 + numberOfTeams + matchScenarios;
        int startVertex = vertices - 2;
        int endVertex = vertices - 1;
        
        flowNetwork = new FlowNetwork(vertices);
        
        for (int i = 0; i < numberOfTeams; i++)
        {
            flowNetwork.addEdge(new FlowEdge(i, endVertex, maxWinsforX - wins[i]));
        }
        
        int vertexCount = numberOfTeams;
        double capacitySumFromStart = 0;
        for (int i = 0; i < numberOfTeams; i++)
        {
            for (int j = i + 1; j < numberOfTeams; j++)
            {
                flowNetwork.addEdge(new FlowEdge(startVertex, vertexCount, against[i][j]));
                capacitySumFromStart += against[i][j];
                flowNetwork.addEdge(new FlowEdge(vertexCount, i, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(vertexCount, j, Double.POSITIVE_INFINITY));
                vertexCount++;
            } 
        }
        
        fordFulkerson = new FordFulkerson(flowNetwork, startVertex, endVertex);
        
        return Double.compare(fordFulkerson.value(), capacitySumFromStart) < 0;
    }
    
    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team)
    {
        verifyInput(team);
        
        if (!isEliminated(team))
        {
            return null;
        }
        
        if (trivialElimination(team) != null)
        {   
            return trivialElimination(team);
        }
        
        Iterable<String> teamIterable = teams();
        ArrayList<String> eliminatingTeams = new ArrayList<String>();
        
        for (String aTeam: teamIterable)
        {
            int teamNumber = teams.get(aTeam);
            if (fordFulkerson.inCut(teamNumber))
            {
                eliminatingTeams.add(aTeam);
            }
        }
        
        return eliminatingTeams;
    }
    
    private Iterable<String> trivialElimination(String team)
    {
        int teamNumber1 = teams.get(team);
        int maxWins = wins[teamNumber1] + remaining[teamNumber1];
        ArrayList<String> teamList = new ArrayList<String>();
        Iterable<String> allTeams = teams();
        
        for (String aTeam: allTeams)
        {
            int teamNumber2 = teams.get(aTeam);
            if (wins[teamNumber2] > maxWins)
            {
                teamList.add(aTeam);
            }
        }
        
        if (teamList.size() == 0)
        {
            return null;
        }
        
        return teamList;
    }
    
    private void verifyInput(String team)
    {
        if (team == null)
        {
            throw new IllegalArgumentException("must enter team");
        }
        else if (!teams.containsKey(team))
        {
            throw new IllegalArgumentException("must enter valid team");
        }
    }
    
    //unit testing
    public static void main(String args[])
    {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams())
        {
            if (division.isEliminated(team))
            {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team))
                {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else
            {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}