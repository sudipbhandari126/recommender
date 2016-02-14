package recommender;

/**
 * Created by SudipBhandari on 12/6/2015.
 */
import java.util.*;
import java.sql.*;

 class DataSet {
     
     
       String showList()
       {
           String temp="";
           Iterator entries=critics.entrySet().iterator();
         while (entries.hasNext())
         {
             Map.Entry thisentry=(Map.Entry)entries.next();
             Object name=thisentry.getKey();
             Object ratings=thisentry.getValue();
             temp=temp+name+ratings+"\n\n";
         }
           return temp;
       }

        Map<String,Map> critics = new HashMap<>();
        Map<String, Float> temp;

        Map<String,Float> ratings=new HashMap<>();
        
        
        
        
        

        
      
        
        
        DataSet(int a) //to load data from database
        {
             try
    {
    Class.forName("com.mysql.jdbc.Driver");
    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/recommender","root","12345");
    Statement st=con.createStatement();
    ResultSet rs=st.executeQuery("select * from movieratings order by critic");
    
    String previousCritic=new String();
    
    
    while (rs.next())
{
String critic=rs.getString("critic");
String movie=rs.getString("movie");
Float rating=rs.getFloat("rating");


if ((!critic.equals(previousCritic) &&(!previousCritic.isEmpty()))||rs.isLast())
{
//map rating to movies, rating to critic and clear the map for next critic
   
    temp=new HashMap<>(ratings);
    critics.put(previousCritic,temp);
    
    ratings.clear();
    
     ratings.put(movie, rating); //start for another critic
}
else 
        {
    //keep on mapping to the movies
            ratings.put(movie, rating);
        }
previousCritic=critic;

}
    
    }
    
    catch (ClassNotFoundException | SQLException e)
    {
    System.out.print(e);
    }
        }
        
        
        DataSet() { //start of constructor for demo
            
            
            //critic1
            ratings.put("Lady in the Water", 2.5F);
            ratings.put("Snakes on a Plane", 3.5F);
            ratings.put("Just My Luck", 3.0F);
            ratings.put("Superman Returns", 3.5F);
            ratings.put("You,Me and Dupree", 2.5F);
            ratings.put("The Night Listener", 3.0F);
            temp = new HashMap<>(ratings);
            critics.put("Lisa Rose", temp);
            ratings.clear();

            //critic2
            ratings.put("Lady in the Water", 3.0F);
            ratings.put("Snakes on a Plane", 3.5F);
            ratings.put("Just My Luck", 1.5F);
            ratings.put("Superman Returns", 5.0F);
            ratings.put("You,Me and Dupree", 3.5F);
            ratings.put("The Night Listener", 3.0F);
            temp = new HashMap<>(ratings);
            critics.put("Gene Seymour", temp);
            ratings.clear();

            //critic3
            ratings.put("Lady in the Water", 2.5F);
            ratings.put("Snakes on a Plane", 3.0F);
            ratings.put("Superman Returns", 3.5F);
            ratings.put("The Night Listener", 4.0F);
            temp = new HashMap<>(ratings);
            critics.put("Michael Philips", temp);
            ratings.clear();


            //critic4
            ratings.put("Snakes on a Plane", 3.5F);
            ratings.put("Just My Luck", 3.0F);
            ratings.put("Superman Returns", 4.0F);
            ratings.put("You,Me and Dupree", 2.5F);
            ratings.put("The Night Listener", 4.5F);
            temp = new HashMap<>(ratings);
            critics.put("Claudia Puig", temp);
            ratings.clear();

            //critics5
            ratings.put("Lady in the Water", 3.0F);
            ratings.put("Snakes on a Plane", 4.0F);
            ratings.put("Just My Luck", 2.0F);
            ratings.put("Superman Returns", 3.0F);
            ratings.put("You,Me and Dupree", 2.0F);
            ratings.put("The Night Listener", 3.0F);
            temp = new HashMap<>(ratings);
            critics.put("Mick LaSalle", temp);
            ratings.clear();

            //critics6
            ratings.put("Lady in the Water", 3.0F);
            ratings.put("Snakes on a Plane", 4.0F);
            ratings.put("Superman Returns", 5.0F);
            ratings.put("You,Me and Dupree", 3.5F);
            ratings.put("The Night Listener", 3.0F);
            temp = new HashMap<>(ratings);
            critics.put("Jack Matthews", temp);
            ratings.clear();

            //critics7
            ratings.put("Snakes on a Plane", 4.5F);
            ratings.put("Superman Returns", 4.0F);
            ratings.put("You,Me and Dupree", 1.0F);
            temp = new HashMap<>(ratings);
            critics.put("Toby", temp);
            ratings.clear();


            //critics8
            ratings.put("Snakes on a Plane",4.5F);
            ratings.put("Forrest Gump",5.0F);
            ratings.put("Titanic",4.5F);
            ratings.put("Avatar",5.0F);
            ratings.put("Silver Linings Playbook",4.5F);

            temp=new HashMap<>(ratings);
            critics.put("Sudip",temp);
            ratings.clear();


        } //end of constructor DataSet

     void showDataSet()
     {
         System.out.println(critics);
     }

     Map listMovies(String name){
         return critics.get(name);
     }

     List listCritics() //function to return the list of critcs
     {
          List ls=new ArrayList<>();
         Iterator entries=critics.entrySet().iterator();
         while (entries.hasNext())
         {
             Map.Entry thisentry=(Map.Entry)entries.next();
             Object name=thisentry.getKey();
             ls.add(name);
         }
         return ls;
     }

 //create a function to calculate the similarity distance of two persons
     float distance(String person1, String person2)
     {
         List common=new ArrayList<>();
         Iterator entries= critics.get(person1).entrySet().iterator();
         while (entries.hasNext())
         {
             Map.Entry thisentry=(Map.Entry)entries.next();
             Object key=thisentry.getKey();
             if (critics.get(person2).containsKey(key))  common.add(key);
         }
         ListIterator i=common.listIterator();
         float dis=0F;
         while (i.hasNext())
         {
             Object movie=i.next();
             float r1=new Float ((float)critics.get(person1).get(movie));
             float r2=new Float ((float)critics.get(person2).get(movie));
             dis+=Math.pow((r1-r2),2);
         }
     return 1/(1+dis);
     }

}
