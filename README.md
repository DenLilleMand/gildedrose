# Setup

Before this task, we expect you to have been through the basic Jenkins material and have the infrastructure set up from that. 

In order for this to work, you need to install both maven and JDK on your agent.
Log into the agen, and type the following:
```
sudo apt install maven openjdk-8-jdk
```

# Coding Assignment

While the purpose is to learn Jenkins, this will be a coding assignment. It is made just to give you some tangible code to work with:
Remember, this is not a programming exercise, but a Jenkins one; code is only there so you have something to build :)

In a language of your choice, e.g. Java, implement the following functionality.

## Gilded Rose Requirements Specification

Hi and welcome to team Gilded Rose. As you know, we are a small inn with a prime location in a
prominent city run by a friendly innkeeper named Allison. We also buy and sell only the finest goods.
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

### 0. Authenticate Jenkins to GitHub
* Generate a new SSH key that will be used by Jenkins to prove itself to GitHub, by following the first part of [Generating a new SSH key](https://help.github.com/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent/)
* Add the public-key to your GitHub account by following [Adding a new SSH key to your GitHub account](https://help.github.com/articles/adding-a-new-ssh-key-to-your-github-account/)
* Add the private-key to Jenkins, by opening your Jenkins server, and clicking `Credentials`
  * Click `(global)` and `Add credentials`
      * Choose Kind "SSH Username with private key", write the details used to generate the keypair and paste the contents from the private-key you generated in the first step, (default ~/.ssh/id_rsa)
      write the passphrase you chose.
      * Save it.

### 0.5 Fork the repository
* Fork of the [Gilded Rose repository](https://github.com/praqma-training/gildedrose) to obtain your own version of the code.

### 1. Create a job and clone git:
* Go into your Jenkins server and click on the `New Item` button on the left.
* Name your new job "gilded rose" and choose `Freestyle project` and click OK
* Under `Source Code Management` choose git, and paste in your git clone URL for this project (Remember to use the _ssh_-url to _your repository_!).
* Choose the credentials that you have set up in Jenkins to auth it against GitHub.
* Click `Save` and then the `Build Now` button.
* Observe that there is a new build in the build history, that hopefully is blue.
* Clik on it and click on `Console Output` to see something like this on your screen :
```
Started by user admin
Building in workspace /var/jenkins_home/workspace/my first job
 > git rev-parse --is-inside-work-tree # timeout=10
Fetching changes from the remote Git repository
 > git config remote.origin.url git@github.com:figaw/gildedrose.git # timeout=10
Fetching upstream changes from git@github.com:figaw/gildedrose.git
 > git --version # timeout=10
using GIT_SSH to set credentials Test to ssh jenkins access github
 > git fetch --tags --progress git@github.com:figaw/gildedrose.git +refs/heads/*:refs/remotes/origin/*
 > git rev-parse refs/remotes/origin/master^{commit} # timeout=10
 > git rev-parse refs/remotes/origin/origin/master^{commit} # timeout=10
Checking out Revision 06344e7eb74250449756084692ce55c4e701ce7d (refs/remotes/origin/master)
Commit message: "Update README.md"
 > git config core.sparsecheckout # timeout=10
 > git checkout -f 06344e7eb74250449756084692ce55c4e701ce7d
First time build. Skipping changelog.
Finished: SUCCESS
```
**Congratulations, you have now made your first jenkins job!**

### 2. Running a maven test

* Click on the `Back to Project` button, and go in and `Configure` the job again.
* Under the `Build` section, add an `Invoke top-level Maven targets` step and write `test` in it. That will trigger the maven test goal on the project, compiling the java code and running the unit tests.
* Click save, and build now once more.
* Go into the console output like last time, and see that maven now actually runs your tests.

### 3. Scheduling the build

As a team, you do not want to go in and manually build the project every time you have some new code commited. _it needs to be automated, right!?!_

* Go into `Configuration` again and select the `Poll SCM` checkbox
* Type in `* * * * */1` to tell Jenkins to check for new commits every minute.
* Make a new commit, uncommenting the test in src/test/java/net/praqma/codeacademy/gildedrose/GildedRoseTest.java
* Push that change to GitHub, and monitor as Jenkins starts a build automatically.
* Note that the build fails (because the test is failing) _this is OK_.

### 4. Generating an artifact

Our Java project needs to be packaged into a Jar file, in order to be ready for release.

* Change the maven goal from `test` to `install`.
* Under `Post-build Actions` add the `Archive the artifacts` action, and write `target/gildedrose-*.jar` in it. That will take the output from the maven goal and add it as an artifact.
* Choose the advanced options and select `Archive artifacts only if build is successful` as well to reduce the number of artifacts
* Click save
* Go back to the job dashboard.
* Fix the unit test by implementing a dumb way of solving the test.
* Push the change to GitHub, and monitor that Jenkins will grab that change and make a build, producing an artifact.

### 4.5 Implementing the Gilded Rose
Look in src/test/java/net/praqma/codeacademy/gildedrose/TexttestFixture.java for examples of items to use for tests.
* Make a test and push it, observe it failing
* Make changes to pass the test and push them, observe as only working code are built to production

### 5. Making the pipeline script work
Now you have made a really nice pipeline in Jenkins just using the normal jobs.
Now we want it *as code*!
 
First off, we need a new `Pipeline` job.

* Click on `New Item`, choose `Pipeline` type, and give it a name.
* Head down to the `Pipeline` section of the job, and click on the "try sample pipeline" and choose `Hello world`
* Save and Build it.

The result should very well be that you have a blue (succesful) build, and in the main view a text saying the following will appear:

>This Pipeline has run successfully, but does not define any stages. Please use the stage step to define some stages in this Pipeline.

We have to look into that now, *don't we?*

### 6. Convert your pipeline

In pipeline, we like `stages` as they give us the ability to see where in the process things are going wrong.
So take a look at your old build script and transfer the things you did there to the jenkins script.

If you cant remember the syntax for creating stages, then here is the hello world example of it:

```
node {
    stage ('Hello'){
        echo 'Hello World'
    }
}
```

Make three stages that does the following:

* `Preparation`: Clone the repository from git.
* `Build` : Executes maven `clean package`
* `Results` :  Make jUnit display the results of `**/target/surefire-reports/TEST-*.xml`, and archive the generated jar file in the `target` folder

Run this to see that it's working. The archiving part can be verified by looking for a small blue arrow next to the build number in the overview. Make sure you get your Jar file with you there.

### 7. Parallel and stashing
We also need to get the javadoc generated for the project.

Fortunately that can be done with a small `mvn site` command.

* Create another step called `Javadoc` where you execute the above command, and archive the result in the `target/javadoc` folder.

Now we have two processes that actually can be run in parallel. The `build` and `javadoc` steps both take in the sourcecode and produces artifacts. So lets try to run them in parallel.

> This assignment is loosely formulated, so you need to [look things up yourself](https://jenkins.io/doc/pipeline/steps/) in order to complete this one

* Stash the source code cloned in `Preparation` and call it source
* `build` and `javadoc` steps needs to be included in a parallel step like the one below

```
def builders = [
	"build": {
		node {}
	},
	"javadoc": {
	node {}
	}
]
stage('parallel'){
	parallel builders
}
```

* Unstash the source code in both stages, and perform the normal build steps
* Stash the results instead of archiving. Call them `jar` and `javadoc`
* Unstash them in the `Results` step in the end where you archive them.


### Multibranch pipeline
There is a file in this repository called Jenkinsfile

Right now it only has a dumb `hello world`

* Take your pipeline script, and replace the files content with it.
* Replace the git command with `checkout scm`. Multibranch knows where it gets triggered from.
* Push that back to the repository
* Create a new job of the `multibranch pipeline` type, and configure that to take from your repository.
* Trigger it to see that it works.
* Make a new branch locally, and push it up to GitHub to see that it automatically makes a new pipeline for you as well.

**That's it!** You rock at this!
If you have more time, and want to make a real pipeline with pretested integration, then read our story about [pipeline vs old fashioned jobs](http://www.praqma.com/stories/jenkins-pipeline/) and try to incorporate the script into your own pipeline!
