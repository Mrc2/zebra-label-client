To Build use goals as follow

<goals>
    <goal>clean</goal>
    <goal>compiler:compile</goal>
    <goal>compiler:testCompile</goal>
    <goal>jar:jar</goal>
    <!--                <goal>install:install</goal>-->
    <goal>deploy</goal>
</goals>
<properties>
    <skipTests>true</skipTests>
</properties>
<activatedProfiles>
    <activatedProfile>local</activatedProfile>
</activatedProfiles>

artivate will be shaded jar of about 3.2mb
zebralabel-1.0-SNAPSHOT.jar 
