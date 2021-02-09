package main;

import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;

import java.util.Random;

public class StatsChecker {

	Main main;

	StatsChecker(Main main){
		this.main = main;
	}


	boolean gotRequiredLevel(Skill skill){
		switch (skill){
			case WOODCUTTING:
				return getSkillLevel(Skill.WOODCUTTING) >= 20;
			case FISHING:
				return getSkillLevel(Skill.FISHING) >= 10;
			case MINING:
				return getSkillLevel(Skill.MINING) >= 15;
		}

		return false;
	}

	Skill getRandomSkill(){
		if(statsGoalReached()) return null;

		int i = new Random().nextInt(3) + 1;
		if(i == 1 && !gotRequiredLevel(Skill.WOODCUTTING)) return Skill.WOODCUTTING;
		if(i == 2 && !gotRequiredLevel(Skill.FISHING)) return Skill.FISHING;
		if(i == 3 && !gotRequiredLevel(Skill.MINING)) return Skill.MINING;
		else return getRandomSkill();
	}

	private int getSkillLevel(Skill skill){
		return Skills.getRealLevel(skill);
	}

	boolean statsGoalReached() {
		return getSkillLevel(Skill.WOODCUTTING) >= 20 && getSkillLevel(Skill.FISHING) >= 10
				&& getSkillLevel(Skill.MINING) >= 15;
	}
}
