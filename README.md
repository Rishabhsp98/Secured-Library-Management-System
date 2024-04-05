## Details about the project

1. This project uses spring-boot-latest version - 3.2.4 along with java-version >=17
2. All the APIs are secured using an authentication manager builder which exposes the UserDetail model and UserDetailsService which is implemented by UserModels.
3. For more info on the latest version of spring-security please check this - https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring
4. For temprory purpose csrf(cross-site-request-forgeiry) is disabled for the sign-up(adding student API),which is scalable as per requriements.
5. There are 2 major types of users "student" and "admin"(library-head) which come under a single entity - "secured user"
6. Authorities are defined in such a way as to make the flow simple and lucid to understand.
7. Redis functionality has been utilized in this project using redis-client= lettuce, using redis-template to get and set the "SERIALIZED" entity.
8. To install redis via wsl and use it on windows please follow this link- https://redis.io/docs/install/install-redis/install-redis-on-windows/#install-or-enable-wsl2   
