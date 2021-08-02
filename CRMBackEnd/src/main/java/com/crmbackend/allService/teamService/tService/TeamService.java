package com.crmbackend.allService.teamService.tService;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crmbackend.allService.teamService.repo.TeamRepository;
import com.crmbackend.allService.teamService.repo.TeamUserRepository;
import com.crmbackend.allService.userService.repo.RoleRepository;
import com.crmbackend.allService.userService.repo.UserRepository;
import com.crmbackend.dtos.IavaliableUsers;
import com.crmbackend.entity.Team;
import com.crmbackend.entity.TeamUsers;

@Service
@Transactional
public class TeamService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private TeamUserRepository teamUserRepo;

	@Autowired
	private TeamRepository teamRepo;

	public List<Team> listAll() {

		List<Object> alldata = teamRepo.getAllTeamAndDetails();

		List<Team> finalResult = alldata.stream().map(t -> new Team(
				((Team) t).getId(), ((Team) t).getTeamname(), ((Team) t).getDelete_date(), ((Team) t).getDescription(),
				((Team) t).getActive(), ((Team) t).getTeam_users().stream()
						.filter(u -> u.getActive().equals(TeamUsers.Status.ACTIVE)).collect(Collectors.toSet())))
				.collect(Collectors.toList());

		return finalResult;

	}

	public List<IavaliableUsers> getAllUsers() {
		List<IavaliableUsers> allUser = userRepo.getAllUsersAndTeamInfo();
		return allUser;
	}
}
