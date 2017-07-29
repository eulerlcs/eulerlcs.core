package chapter02.xmlconfig.soundsystem.properties;
import org.springframework.beans.factory.annotation.Autowired;

import chapter02.xmlconfig.soundsystem.CompactDisc;
import chapter02.xmlconfig.soundsystem.MediaPlayer;

public class CDPlayer implements MediaPlayer {
  private CompactDisc compactDisc;

  @Autowired
  public void setCompactDisc(CompactDisc compactDisc) {
    this.compactDisc = compactDisc;
  }

  public void play() {
    compactDisc.play();
  }

}
