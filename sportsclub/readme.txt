Sportsclub Spring JBoss Integration example
===========================================

Prerequisites:

A. Maven Repository setup

The example is pre-configured with JBoss repositories.

The pom.xml can be modified to include references to other repositories, or
equivalent artifact versions (if the build environment uses an enterprise-wide
repository in which the artifacts have different version numbers).

Further instructions on how to set up the community repositories can be
found at: http://community.jboss.org/wiki/MavenGettingStarted-Users.

B. Spring Deployer installed in JBoss AS

For running the application, the Spring Deployer must be installed in JBoss AS.
Please refer to the Snowdrop documentation for details.

For building & running the application:

1. Execute a Maven build from the root folder of the example

mvn clean package

Note:

A) The application uses different profiles for Spring 2.5 and Spring 3.
The default profile is Spring 2.5.

B) JMS integration can be included in the build or not through the messaging profile
(active by default). Running the application on platforms which do not contain the
messaging component (such as the 'web' profile) requires disabling the messaging profile.
Specifying a set of active profiles

As such, the following profile combinations are possible:

mvn clean package : Spring 2.5, messaging enabled
mvn clean package -Pspring-2.5 : Spring 2.5, messaging disabled
mvn clean package -Pspring-3 : Spring 3, messaging disabled
mvn clean package -Pspring-2.5,messaging : Spring 2.5, messaging enabled
mvn clean package -Pspring-3,messaging : Spring 2.5, messaging enabled




2. Setup the data source and JMS destination:

  a) enter the jbossconf directory
     cd jbossconf

  b) modify the jbossconf/jbossas.properties file to indicate the correct location
  of the JBoss AS installation

  c) execute the maven build with one of the two applicable profiles:

    - for installing both the datasource and the JMS queue (e.g. for the default profile)

       mvn -Pinstall

    - for installing only the datasource (e.g. for the web profile)

       mvn -Pinstall-only-ds

    The installed files can be removed with the command:

       mvn -Pcleanup

3. Initialize the database

   a) enter the database directory

      cd database

   b) execute the maven build

      mvn -Pdb-setup

4. Start the database

   a) execute the database startup script from the database directory (instructions):

      on Linux and other *nix-like systems: ./startdb.sh
      on Windows: ./startdb.bat

5. Deploy the application

   Copy one of the two ears produced by the build to the deploy folder of JBoss AS.

   The deployment folder is: JBOSS_HOME/server/default/deploy

   The two (alternative) build files are:

   sportsclub-ear/target/sportsclub.ear (Hibernate-based implementation)

   sportsclub-jpa-ear/target/sportsclub.ear (JPA-based implementation, using Hibernate as the underlying provider)

6. Start JBoss AS

7. Point the browser at one of:

   http://localhost:8080/sportsclub/invoicing [1]
   http://localhost:8080/sportsclub/reservations
   http://localhost:8080/sportsclub/reservations-webflow
   http://localhost:8080/sportsclub/subscriptions


Steps 1,2,3 need to be executed only once for setting up the runtime environment.

Step 3 can be repeated any time in order to reset the database (with the application stopped).

Steps 5,6 do not need to be executed in a particular order, but the database must be started
before the application is deployed.

Notes:

[1] The invoicing application uses pre-authentication, therefore it is necessary to use one of the following user/password
combinations:

admin/adminPass - for an administrator account, which can generate invoices
employee/employeePass - for an employee account which can browse the invoices but cannot generate one
                        (attempt will result in an application failure)



