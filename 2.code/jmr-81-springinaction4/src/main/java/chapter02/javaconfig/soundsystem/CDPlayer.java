package chapter02.javaconfig.soundsystem;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;

public class CDPlayer implements MediaPlayer {
	@Getter
	private CompactDisc cd;

	@Autowired
	public CDPlayer(CompactDisc cd) {
		this.cd = cd;
	}

	@Override
	public void play() {
		cd.play();
	}
}
