# Setup
In order for this to work, you need to install both maven and JDK on your slave.
Log into the slave, and type the following:
```
sudo apt install maven openjdk-8-jdk 
```

# Coding Assignment
While trying to know Jenkins, this will be your coding assignment. It is made just to give you some tangible code to work with:
Remember, this is not a programming exercise, but a Jenkins one; code is only there so you have something to build :)

In a language of your choice, e.g. Java, implement the following functionality.

## Gilded Rose Requirements Specification

Hi and welcome to team Gilded Rose. As you know, we are a small inn with a prime location in a
prominent city ran by a friendly innkeeper named Allison. We also buy and sell only the finest goods.
Unfortunately, our goods are constantly degrading in quality as they approach their sell by date. We
have a system in place that updates our inventory for us. It was developed by a no-nonsense type named
Leeroy, who has moved on to new adventures. Your task is to add the new feature to our system so that
we can begin selling a new category of items. First an introduction to our system:

   - All items have a SellIn value which denotes the number of days we have to sell the item
   - All items have a Quality value which denotes how valuable the item is
   - At the end of each day our system lowers both values for every item

Pretty simple, right? Well this is where it gets interesting:

       - Once the sell by date has passed, Quality degrades twice as fast
       - The Quality of an item is never negative
       - "Aged Brie" actually increases in Quality the older it gets
       - The Quality of an item is never more than 50
       - "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
       - "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
	   Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
   	   Quality drops to 0 after the concert

We have recently signed a supplier of conjured items. This requires an update to our system:

   - "Conjured" items degrade in Quality twice as fast as normal items

Feel free to make any changes to the UpdateQuality method and add any new code as long as everything
still works correctly. However, do not alter the Item class or Items property as those belong to the
goblin in the corner who will insta-rage and one-shot you as he doesn't believe in shared code
ownership (you can make the UpdateQuality method and Items property static if you like, we'll cover
for you).

Just for clarification, an item can never have its Quality increase above 50, however "Sulfuras" is a
legendary item and as such its Quality is 80 and it never alters.

# Exercises:



### 1. Create a job and clone git:
* Go into your Jenkins server at click on the `New Item` button on the left.
* Name your new job "roman numerals" and choose `Freestyle project` and click OK
* Under `Source Code Management` choose git, and paste in your git clone URL for this project (Remember to choose your fork of the project!).
* Choose the credentials that you have set up in Jenkins to auth it against GitHub 
* Click `save` and then the `Build Now` button.
* Observe that there is a new build in the build history, that hopefully is blue.
* Clik on it and click on `Console Output` to see something like this on your screen :
```
Started by user Sofus
Building in workspace /var/lib/jenkins/workspace/test
Cloning the remote Git repository
Cloning repository git@github.com:praqma-training/romannumerals.git
 > git init /var/lib/jenkins/workspace/test # timeout=10
Fetching upstream changes from git@github.com:praqma-training/romannumerals.git
 > git --version # timeout=10
using GIT_SSH to set credentials 
 > git fetch --tags --progress git@github.com:praqma-training/romannumerals.git +refs/heads/*:refs/remotes/origin/*
 > git config remote.origin.url git@github.com:praqma-training/romannumerals.git # timeout=10
 > git config --add remote.origin.fetch +refs/heads/*:refs/remotes/origin/* # timeout=10
 > git config remote.origin.url git@github.com:praqma-training/romannumerals.git # timeout=10
Fetching upstream changes from git@github.com:praqma-training/romannumerals.git
using GIT_SSH to set credentials 
 > git fetch --tags --progress git@github.com:praqma-training/romannumerals.git +refs/heads/*:refs/remotes/origin/*
 > git rev-parse refs/remotes/origin/master^{commit} # timeout=10
 > git rev-parse refs/remotes/origin/origin/master^{commit} # timeout=10
Checking out Revision 94ca2473f084c804aec50945e022e2d4102862d1 (refs/remotes/origin/master)
 > git config core.sparsecheckout # timeout=10
 > git checkout -f 94ca2473f084c804aec50945e022e2d4102862d1
First time build. Skipping changelog.
Finished: SUCCESS
```
**Congratulations, you have now made your first jenkins job!**

### 2. Running a maven test

* Click on the `Back to Project` button, and go in and Configure the job again.
* Under the `Build` section, add an `Invoke top-level Maven targets` step and write `test` in it. That will trigger the maven test goal on the project, compiling the java code and running the unit tests.
* Click save, and build now once more.
* Go into the console output like last time, and see that maven now actually runs your tests.

### 3. scheduling the build
As a team, you do not want to go in and manually build the project every time you have some new code commited. _it needs to be automated, right!?!_

* Go into `Configuration` again and select the `Poll SCM` checkbox
* Type in `* * * * */1` to tell Jenkins to check for new commits every minute.
* Make a new commit, uncommenting the test in src/test/java/net/praqma/codeacademy/romannumerals/AppTest.java
* Push that change to github, and monitor as Jenkins starts a build automatically.
* Note that the build fails (because the uncommented test is failing) _this is OK_.

### 4. Generating an artifact

Our Java project needs to be packaged into a Jar file, in order to be ready for release.


* change the maven goal from `test` to `install`.
* Under `Post-build Actions` add the `Archive the artifacts` action, and write `target/romannumerals-*.jar` in it. That will take the output from the maven goal and add it as an artifact.
* Choose the advanced options and select `Archive artifacts only if build is successful` as well to reduce the number of artifacts
* Click save
* Go back to the job dashboard.
* Fix the unit test by implementing a dumb way of solving the test.
* Push the change to GitHub, and monitor that Jenkins will grab that change and make a build, producing an artifact.


### 5. Making the pipeline script work

There is a file in this folder called Jenkinsfile
