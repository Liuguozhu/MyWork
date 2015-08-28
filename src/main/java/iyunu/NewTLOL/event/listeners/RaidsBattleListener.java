package iyunu.NewTLOL.event.listeners;

import iyunu.NewTLOL.event.RaidsbattleEvent;

import org.springframework.context.ApplicationListener;

public class RaidsBattleListener implements ApplicationListener<RaidsbattleEvent> {

	@Override
	public void onApplicationEvent(RaidsbattleEvent event) {

		while (true) {
			System.out.println(event.getRole().getNick());
		}

	}

}