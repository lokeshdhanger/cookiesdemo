# cookiesdemo

* This POC demonstrates the old way of creating the cookies using Cookie object available and the new way of creating cookies in ResponseEntity.
* It also demonstrates the use of Same-Site attribute in cookies.

## prerequisites:
* java 8 should be installed,
* maven should be installed

## generate self certified ssl certificate for application
- go to resources folder of application
- open terminal/command prompt there and paste this script
  > keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650
- answer the questions asked during this setup and remember the password

# open application.properties file and paste the following
_ application.properties file edit START _

### Define a custom port instead of the default 8080
server.port=8443

### Tell Spring Security (if used) to require requests over HTTPS
security.require-ssl=true

### The format used for the keystore 
server.ssl.key-store-type=PKCS12
### The path to the keystore containing the certificate
server.ssl.key-store=classpath:keystore.p12
### The password used to generate the certificate
server.ssl.key-store-password= {your password here}
### The alias mapped to the certificate
server.ssl.key-alias=tomcat

_ application.properties file edit END _


## go to application root folder, here /cookiesdemo
## run:   mvn clean install

* a jar file would be created in **/target** folder
* run this jar file as 
  >java -jar cookiedemo-0.0.1-SNAPSHOT.jar
