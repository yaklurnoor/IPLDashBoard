package io.javabrains.ipldashboard.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import io.javabrains.ipldashboard.data.Match;



@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long  id;
	String teamName;	
	Long totalMatches;
	Long totalWins;
	
	@Transient
	List<Match> matchList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Long getTotalMatches() {
		return totalMatches;
	}
	public void setTotalMatches(Long totalMatches) {
		this.totalMatches = totalMatches;
	}
	public Long getTotalWins() {
		return totalWins;
	}
	public void setTotalWins(Long totalWins) {
		this.totalWins = totalWins;
	}
	@Override
	public String toString() {
		return "Team [id=" + id + ", teamName=" + teamName + ", totalMatches=" + totalMatches + ", totalWins="
				+ totalWins + "]";
	}
	public Team(String teamName, Long totalMatches) {
		super();
		this.teamName = teamName;
		this.totalMatches = totalMatches;
	}
	
	
	public Team() {
		
	}
	public List<Match> getMatchList() {
		return matchList;
	}
	public void setMatchList(List<Match> matchList) {
		this.matchList = matchList;
	}
	

}

