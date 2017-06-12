# Setup
In order for this to work, you need to install both maven and JDK on your slave.
Log into the slave, and type the following:
```
sudo apt install maven openjdk-8-jdk 
```

# Coding Assignment
While trying to know Jenkins, this will be your coding assignment. It is made just to give you some tangible code to work with:
Remember, this is not a programming exercise, but a Jenkins one; code is only there so you have something to build :)

Given a positive integer number (eg 42) determine
its Roman numeral representation as a String (eg "XLII").

You cannot write numerals like IM for 999.
Wikipedia states "Modern Roman numerals are written by
expressing each digit separately starting with the
leftmost digit and skipping any digit with a value of zero."

Examples:
```
1 ->    "I" | 10 ->    "X" | 100 ->    "C" | 1000 ->    "M"
2 ->   "II" | 20 ->   "XX" | 200 ->   "CC" | 2000 ->   "MM"
3 ->  "III" | 30 ->  "XXX" | 300 ->  "CCC" | 3000 ->  "MMM"
4 ->   "IV" | 40 ->   "XL" | 400 ->   "CD" | 4000 -> "MMMM"
5 ->    "V" | 50 ->    "L" | 500 ->    "D" |
6 ->   "VI" | 60 ->   "LX" | 600 ->   "DC" |
7 ->  "VII" | 70 ->  "LXX" | 700 ->  "DCC" |
8 -> "VIII" | 80 -> "LXXX" | 800 -> "DCCC" |
9 ->   "IX" | 90 ->   "XC" | 900 ->   "CM" |

1990 -> "MCMXC"  (1000 -> "M"  + 900 -> "CM" + 90 -> "XC")
2008 -> "MMVIII" (2000 -> "MM" + 8 -> "VIII")
  99 -> "XCIX"   (90 -> "XC" + 9 -> "IX")
  47 -> "XLVII"  (40 -> "XL" + 7 -> "VII")

```

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

### 4. Generating an artifact

Our Java project needs to be packaged into a Jar file, in order to be ready for release.



* change the maven goal from `test` to `install`.
* Under `Post-build Actions` add the `Archive the artifacts` action, and write `target/romannumerals-*.jar` in it. That will take the output from the maven goal and add it as an artifact.
* Choose the advanced options and select `Archive artifacts only if build is successful` as well to reduce the number of artifacts
* Click save
* Go back to the job dashboard.
* Fix the unit test by implementing a dumb way of solving the test.
* Push the change to GitHub, and monitor that Jenkins will grab that change and make a build, producing an artifact.
