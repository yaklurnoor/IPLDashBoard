package io.javabrains.ipldashboard.controller;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.ipldashboard.model.Team;
import io.javabrains.ipldashboard.repository.MatchRepository;
import io.javabrains.ipldashboard.repository.TeamRepository;

@RestController
@RequestMapping("/team")
public class MatchDetailController extends JobExecutionListenerSupport {
	
	@Autowired
	TeamRepository teamRepository ;
	@Autowired
	MatchRepository matchRepository ;
	
	@GetMapping("/getDetails/{teamname}")
	public Team getTeamDetails(@PathVariable("teamname") String teamName) {
		
		Team team =teamRepository.getTeamByTeamName(teamName);		
		team.setMatchList(matchRepository.getRecentMatchDetails(teamName,4));
		
		return team;
		
	}
	
}
