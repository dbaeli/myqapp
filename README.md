# MyQApp Developer Install Guide

**Always commit on branch dev**

**Never commit dev.properties**

## Install Vagrant

- Install [VirtualBox and VirtualBox Extension Pack](https://www.virtualbox.org/wiki/Downloads)
- Install [Vagrant](http://www.vagrantup.com/)
- Open a terminal
- Clone the project ```git clone git@github.com:haklop/myqapp.git```
- ```cd myqapp```
- Start the VM ```vagrant up```
- Once the machine has booted up, you can shell into it by typing ```vagrant ssh```
- From the /vagrant directory: ```mvn clean jetty:run```
- Go to [http://localhost:8080](http://localhost:8080)

Vagrant will mount your local directory *myqapp* on */vagrant*

## Setup external web services

Ignore uncommitted changes on dev.properties: ```git update-index --assume-unchanged src/main/resources/dev.properties```

### Google

- Create a new project on the [Google API's Console](https://code.google.com/apis/console#access). The result of this registration process is a set of values that are known to both Google and your application. WHen requesting a client ID, the callback have to be **http://localhost:8080/api/google/callback**.
- On dev.properties, replace *google.oauth.key* and *google.oauth.secret* respectively by the by the *Client ID* and *Client secret* given by Google.

### Trello

- Go to [https://trello.com/1/appKey/generate](https://trello.com/1/appKey/generate)
- On dev.properties, replace *trello.oauth.key* and *trello.oauth.secret* respectively by the key and the secret generated by Trello.
- Create a private board on Trello with 8 lists.
- Replace *editingprocess.add.board.id* and *editingprocess.stats.board.id* by the board's id. To simply get the board's id, create a map in the first column and export it as JSON. Locate idBoard on the generated json.

### GitHub

- Go to [https://github.com/settings/applications/new](https://github.com/settings/applications/new) and register the application. The callback have to be **http://localhost:8080/api/github/callback**.
- On dev.properties, replace *github.oauth.client.id* and *github.oauth.client.secret* respectively by the Client ID and the Client Secret generated by GitHub.

## Initialize MongoDB with some default data

Create an admin user on Vagrant environment:

- ```mongo```
- ```use myqapp```
- Replace the attribute *_id* with your email and execute:

```
db.userProfile.insert({ 
  "_class" : "com.infoq.myqapp.domain.UserProfile", 
  "_id" : "myqapp@gmail.com", 
  "authorities" : [  "ROLE_EDITOR",  "ROLE_ADMIN" ], 
  "firstName" : "John", 
  "lastName" : "Smith", 
  "secretGithub" : "", 
  "secretTrello" : "", 
  "tokenGithub" : "", 
  "tokenTrello" : "" 
})
```


## Change default port

By default, MyQApp is starting on port 8080.

// TODO

## Release and deploy

### Config

Use [maven-jgitflow-plugin](https://bitbucket.org/atlassian/maven-jgitflow-plugin/wiki/Home)

You may also need to provide a plugin group in your /.m2/settings.xml to be able to use the short name of the plugin on the command line.

To do so, simply add the following to your settings.xml:
```xml
<pluginGroups>
  <pluginGroup>com.atlassian.maven.plugins</pluginGroup>
</pluginGroups>
```

### Workflow

Create the release branch and set the release version. Release-start creates a release branch and sets the pom version.

	mvn jgitflow:release-start

Finish the release and set the next snapshot version. Release-finish creates the release tag, merges the release branch into master, deletes the release branch and push everything to the scm.

	mvn jgitflow:release-finish
	
When something is pushed to master, the application is automatically deployed on the server.
