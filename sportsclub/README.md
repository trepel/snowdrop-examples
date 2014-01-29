Sportsclub Spring JBoss Integration example
===========================================

Prerequisites:
----------------------------------------

A. Maven Repository setup

  - The example is pre-configured with JBoss repositories.

  - The pom.xml can be modified to include references to other repositories, or 
equivalent artifact versions (if the build environment uses an enterprise-wide 
repository in which the artifacts have different version numbers).

  - Further instructions on how to set up the community repositories can be 
found [here](http://community.jboss.org/wiki/MavenGettingStarted-Users).

B. Spring Deployer installed in JBoss AS

  - For running the application, the Spring Deployer must be installed in JBoss AS.
Please refer to the Snowdrop documentation for details.


For building & running the application:
--------------------------------------  

**1) Execute a Maven build from the root folder of the example**

        mvn clean package

JMS integration can be included in the build or not through the messaging profile. Running the application on platforms which do not contain the messaging component (such as the web profile) requires 
disabling the messaging profile as follows: 

        mvn clean package "-P!messaging" 


**2) Configure JBoss AS**

   a. Add the JMS destination definition in the messaging subsystem

            <subsystem xmlns="urn:jboss:domain:messaging:1.1">
                ...
                <hornetq-server>
                    ...
                    <jms-destinations>
                        ...
                        <!-- Include the following definition -->
                        <jms-queue name="sportsclub">
                            <entry name="queue/sportsclub"/>
                        </jms-queue>
                    </jms-destinations>
                </hornetq-server>
            </subsystem>

   b. Add a security domain in the security subsystem

            <subsystem xmlns="urn:jboss:domain:security:1.1">
                <security-domains>
                    ...
                    <!-- Include the following definition -->
                    <security-domain name="employees">
                        <authentication>
                            <login-module code="Database" flag="required">
                                <module-option name="dsJndiName" value="java:jboss/datasources/ExampleDS"/>
                                <module-option name="principalsQuery" value="select passwd from SPORTSCLUB_USERS where username=?"/>
                                <module-option name="rolesQuery" value="select userRoles,'Roles' from SPORTSCLUB_ROLES where username=?"/>
                            </login-module>
                        </authentication>
                    </security-domain>
                </security-domains>
            </subsystem>


**3) Deploy the application**

  a. Copy one of the two ears produced by the build to the deploy folder of JBoss AS.

  b. The deployment folder is: `JBOSS_HOME/standalone/deployments`

  c. The two (alternative) build files are:

     - `sportsclub-ear/target/sportsclub.ear` (Hibernate-based implementation)

     - `sportsclub-jpa-ear/target/sportsclub.ear` (JPA-based implementation, using Hibernate as the underlying provider)


**4) Start JBoss AS**

**5) Point the browser at one of:**

   - [http://localhost:8080/sportsclub/invoicing](http://localhost:8080/sportsclub/invoicing) [1]
   - [http://localhost:8080/sportsclub/reservations](http://localhost:8080/sportsclub/reservations)
   - [http://localhost:8080/sportsclub/reservations-webflow](http://localhost:8080/sportsclub/reservations-webflow)
   - [http://localhost:8080/sportsclub/subscriptions](http://localhost:8080/sportsclub/subscriptions)

*NOTES:*

[1] The invoicing application uses pre-authentication, therefore it is necessary to use one of the following user/password 
combinations:

- 'admin/adminPass' - for an administrator account, which can generate invoices

- 'employee/employeePass' - for an employee account which can browse the invoices but cannot generate one
                        (attempt will result in an application failure)

