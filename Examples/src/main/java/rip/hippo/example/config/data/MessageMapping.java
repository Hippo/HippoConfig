package rip.hippo.example.config.data;

import rip.hippo.config.map.Mappable;

/**
 * @author Hippo
 * @version 1.0.0, 1/26/21
 * @since 1.0.0
 */
public final class MessageMapping implements Mappable {

  private String lastJoined = "You are the first player";

  public String getLastJoined() {
    return lastJoined;
  }

  public void setLastJoined(String lastJoined) {
    this.lastJoined = lastJoined;
  }
}
