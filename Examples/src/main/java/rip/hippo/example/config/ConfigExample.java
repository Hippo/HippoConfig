package rip.hippo.example.config;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import rip.hippo.config.adapter.pool.ConfigAdapterPool;
import rip.hippo.config.adapter.pool.impl.StandardConfigAdapterPool;
import rip.hippo.example.config.data.MessageMapping;


/**
 * @author Hippo
 * @version 1.0.0, 1/26/21
 * @since 1.0.0
 */
public final class ConfigExample extends JavaPlugin implements Listener {

  private final ConfigAdapterPool configAdapterPool;
  private final MessageMapping messageMapping;

  public ConfigExample() {
    this.configAdapterPool = new StandardConfigAdapterPool(getDataFolder()); // Create our config adapter pool, set the directory to the plugins data folder.
    this.messageMapping = new MessageMapping(); // Example mappable object, this can be constructed anywhere obviously.
    configAdapterPool.registerMappable(MessageMapping.class);
  }

  @Override
  public void onEnable() {
    configAdapterPool.getAdapter("MyConfig") // Get the adapter for the config file `MyConfig` (datafolder/MyConfig.yml).
        .map(messageMapping) // Set the mappable object for this adapter.
        .update(); // update the object, note that if the config is empty the object will not be affected.

    Bukkit.getPluginManager().registerEvents(this, this);

    super.onEnable();
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent playerJoinEvent) {
    Player player = playerJoinEvent.getPlayer();
    player.sendMessage(messageMapping.getLastJoined());
    messageMapping.setLastJoined(String.format("%s was the last player to join.", player.getName()));

    Bukkit.getScheduler().runTaskAsynchronously(this, configAdapterPool
        .getAdapter("MyConfig")
        .map(messageMapping) // We don't necessarily have to map the object before saving, but I'm doing it just in case its not set for whatever reason.
        ::save);
  }
}
