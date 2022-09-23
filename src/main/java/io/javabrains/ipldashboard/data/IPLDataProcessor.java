package io.javabrains.ipldashboard.data;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class IPLDataProcessor  implements ItemProcessor<MatchInput, Match> {

	  private static final Logger log = LoggerFactory.getLogger(IPLDataProcessor.class);

	  @Override
	  public Match process(final MatchInput matchInput) throws Exception {
	   
		 Match match  = new Match();
		 
		 match.setId(Long.parseLong(matchInput.getMatch_id()));
		 match.setCity(matchInput.getCity());

		 String date = matchInput.getDate();
		 
		
		 
		 
		 Date newDate=new SimpleDateFormat("dd/MM/yyyy").parse(date);
		 
		 match.setDate(newDate);
		 match.setVenue(matchInput.getVenue());
		 String firstInningTeam, secondInningTeam;
		 
		 if(matchInput.getToss_decision().equals("Bat")){
			 firstInningTeam = matchInput.getToss_winner();
			 secondInningTeam = matchInput.getTeam1().equals(matchInput.getToss_winner()) ?
					 matchInput.getTeam2():matchInput.getTeam1();
			 
			 
		 }else {
			 secondInningTeam = matchInput.getToss_winner();
			 firstInningTeam = matchInput.getTeam1().equals(matchInput.getToss_winner()) ?
					 matchInput.getTeam2():matchInput.getTeam1();
		 }
		 		
		 match.setTeam1(firstInningTeam);
		 match.setTeam2(secondInningTeam);
		 match.setTossWinner(matchInput.getToss_winner());
		 match.setTossDecision(matchInput.getToss_decision());
		 match.setPlayerOfMatch(matchInput.getPlayer_of_match());
		 match.setMatchWinner(matchInput.getWinner());
		 match.setResults(matchInput.getResults());
		 match.setUmpire1(matchInput.getUmpire1());
		 match.setUmpire2(matchInput.getUmpire2());
		 
		 
		 
	    log.info("Converting (" + match + ") into (" + match + ")");

	    return match;
	  }

	

}
