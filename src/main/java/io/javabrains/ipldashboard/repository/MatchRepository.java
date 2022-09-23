package io.javabrains.ipldashboard.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import io.javabrains.ipldashboard.data.Match;
import io.javabrains.ipldashboard.model.Team;

public interface MatchRepository extends CrudRepository<Match,Long>{
	
	public List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2,Pageable pageWith4Records);
	public default List<Match> getRecentMatchDetails(String teamName, int pageNo ){
		Pageable pageWith4Records = PageRequest.of(0, pageNo);
		return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName,pageWith4Records);
	}
	
}
