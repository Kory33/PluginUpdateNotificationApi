# UpdatePluginNotification
[![](https://jitpack.io/v/Kory33/UpdateNotificationPlugin.svg)](https://jitpack.io/#Kory33/UpdateNotificationPlugin)  
This repository is a library that wraps Bukkit API along with automatic update notification.
## How To Use It?
### Bukkit plugins
If you are using Github for version management, simply extend  ``com.github.kory33.updatenotificationplugin.bukkit.github:GithubUpdateNotifyPlugin``  class.


### BungeeCord plugins
BungeeCord plugins can utilize the class that is present under
``jp.llv.updatenotificationplugin.bungeecord``
which should replace ``com.github.kory33.updatenotificationplugin.bukkit`` part of above-stated classes.  


## Dependencies
You can add dependency to this library into your project with the following settings:
### Maven
(Replace `Other ...` comments with set of elements appropriate for your project)
```XML
<project>
    <repositories>
        <!-- Other repositories -->
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    <dependencies>
        <!-- Other dependencies -->
        <dependency>
            <groupId>com.github.Kory33</groupId>
            <artifactId>UpdateNotificationPlugin</artifactId>
            <version>0.1.3</version>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <!-- Other plugins -->
            <!-- To include this library's classes -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <artifactSet>
                                <includes>
                                    <include>com.github.Kory33:UpdateNotificationPlugin</include>
                                </includes>
                            </artifactSet>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
