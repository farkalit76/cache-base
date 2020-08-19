# Pre-Requisites:
	Windows or Macbook machine with at least 16 GB RAM
	Install Eclipse IDE 2019‑12
	Install Eclipse plugins (Freemarker, Spring Boot, EditorConfig)
	Java  Runtime Environment 1.8
	Maven 3.5+
	Git Command Line
	SourceTree (optional)
	
	After Maven installation go to config folder and change settings.xml file to point to SDG artifactory.
	Make sure your have credentials created for SDG artifactory.

# Checkout the Source Code:
	git clone https://smartscm.dsg.gov.ae/scm/pps/paperless-common-services.git
	Switch branch to {branchName} with below commands
	git checkout {branchName}
	git pull
# To create your branch
	git checkout feature/{branchtag}.  E.g. feature/gdrfareports
# To commit the code
	git commit –m “commit message”
# To push the code
	git push origin feature/{branchtag}
    To merge the code, create pull request with destination as {destbranch}
    
# Configuration changes:
1. Configure redis using redisconfig-{profile}.yml based on the profile to be used.
	Ex: configure below:
		spring.redis.conf.host as 10.100.134.121
		spring.redis.password as password
		spring.redis.database as 0 (Redis Database id to be used)
		spring.redis.conf.port as 6379
2. Otp length and sms url and client credentials are configured in application-{profile}.yml
3. In logback-spring.xml make change LOG_PATH value to point to valid local folder path based on springProfile name i.e, native,dev,qa,prod. (In windows no need to change it will create folder in path provided already).
4. Logs will be mounted to the path configured.
5. Configure cache authentication credentials based on profile in application-{profile}.yml. Password configured is encrypted. This are the credentials which are used while consuming the API's using Basic Authorization. 
	Ex: For dev credentials are sdguser/password
6. For logging the method parameters using AOP configure logLevelVerbose in application-{profile}.yml to true.
	Ex: application-qa.yml set logLevelVerbose to true
7. There are two different variations provided for storing the data in redis cache with different implementations of redis.
	i.  JSON
	ii. PlainText (String)
	

# Running from Eclipse:
	1. Start the server by configuring VM arguments with required profile to run by replacing {profile} in below syntax. (Boot.java --> Running on port 9090). Change the port by making changes in profile specific application-{profile}.yml.
		Syntax:  -Dspring.profiles.active={profile}
		Ex: -Dspring.profiles.active=dev
	


# Build the jar/image:
	1. Run the below maven command to build the image
		mvn clean package

# Running from Commandline:
	1. Start the server by configuring VM arguments with required profile to run by replacing {profile} in below syntax.
		Syntax: java -jar -Dspring.profiles.active={profile} {jarPath}
		Ex: java -jar -Dspring.profiles.active=dev target/paperless-common-services-0.0.1-SNAPSHOT.jar

# Pushing the image:
	1. Run the below command to tag push the image to repository
		Syntax: docker push {imageName}:{imageid}
		Ex: docker push dtr.dubai.ae/sdg/paperless-common-services:bhaf8q1
	
# Deploy the image:
	1. Make changes in docker-compose.yml with new tags, required profile, port etc.. for the images
	2. Run the below command to remove the running stack:
		Syntax: docker stack rm {stackName}
		Ex: docker stack rm commonsrvc
	3. Run the below command to deploy
		Syntax: docker stack deploy --compose-file docker-compose.yml {stackName}
		Ex: docker stack deploy --compose-file docker-compose.yml commonsrvc
	4. Check if stack is running using command. Check if the given {stackName} is available in the list.
		Syntax: docker stack ls | grep {stackName}
		Ex: docker stack ls | grep commonsrvc
	5. Check if service is running using
		Syntax: docker service ls | grep {stackName}
		Ex: docker service ls | grep commonsrvc
	6. Check if containers are running using
		Syntax: docker container ls | grep {stackName}
		Ex: docker container ls | grep commonsrvc
	
# Monitoring images using UI:
	1. Download the portainer image
		docker pull portainer/portainer
	2. Run the below portainer image for managing images using UI.
		docker run -d -p 8500:8000 -p 9005:9000 -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data  	portainer/portainer
