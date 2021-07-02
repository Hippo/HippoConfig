# Hippo Config
A lightweight object oriented conifg system for spigot plugins.

# Add Hippo Config
```groovy
repositories {
    maven {
      url 'https://jitpack.io'
    }
}
```

```groovy
dependencies {
    implementation group: 'com.github.Hippo', name: 'HippoConfig', version: '1.0.4'
}
```

# Issues
If you find any issues feel free to open a new issue, or join our [Discord](https://discord.gg/YsZCrRkgmT)

# Limitations
Due to the fact that this is based around bukkits config system it is limited to only being able to (de)serilaize objects that are supported by it. But you are able to write your own custom (de)serializers if needed (hint: you probably wont need to).