package io.javabrains.ipldashboard;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import io.javabrains.ipldashboard.data.Match;
import io.javabrains.ipldashboard.model.Team;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  private final EntityManager entityManager;

  @Autowired
  public JobCompletionNotificationListener(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      
      
      Map<String,Team> teamDetails =new HashMap<String,Team>();
      
      List<Object[]> queryResult1 = entityManager.createQuery("select m.team1,count(*) from Match  m group by m.team1",Object[].class)
    		  .getResultList();
//     
      for(int i = 0 ; i<queryResult1.size();i++) {
    	  Object[] ob=  (Object[])queryResult1.get(i);
    	  Team t = new Team((String)ob[0],(Long)ob[1]);
    	  teamDetails.put(t.getTeamName(), t);
    	
      }
      
      List<Object[]> queryResult2 = entityManager.createQuery("select m.team2,count(*) from Match  m group by m.team2",Object[].class)
    		  .getResultList();
//     
      for(int i = 0 ; i<queryResult2.size();i++) {
    	  Object[] ob=  (Object[])queryResult2.get(i);    	  
    	  Team t =  teamDetails.get((String)ob[0]);    	 
    	  t.setTotalMatches(t.getTotalMatches()+(Long)ob[1]);
    	 
      }
      
      List<Object[]> queryResult3 = entityManager.createQuery("select m.matchWinner,count(*) from Match  m group by m.matchWinner",Object[].class)
    		  .getResultList();
//     
      for(int i = 0 ; i<queryResult3.size();i++) {
    	  Object[] ob=  (Object[])queryResult3.get(i);        	   	
    	  Team t =  teamDetails.get((String)ob[0]);  	  
    	  if(t!=null)  {
    	  t.setTotalWins((Long)ob[1]);
    	  }
         
    	 
      }
      
      
      teamDetails.values().forEach(t->entityManager.persist(t));
      
      teamDetails.values().forEach(t->System.out.println(t));
      
//       entityManager.createQuery("select m.team1,count(*) from Match  m group by m.team1",Object[].class)
//       .getResultList()
//       .stream()
//       .map(i->new Team((String)i[0],(Long)i[1]))
//       .forEach(team ->matchDetails.put(team.getTeamName(), team));
       
     
     
      

//      jdbcTemplate.query("SELECT team1, team2 FROM match",
//        (rs, row) -> "Team 1"+rs.getString(1)+"Team 2"+rs.getString(2)
//      ).forEach(match -> log.info("Found <" + match + "> in the database."));
    }
  }
}
